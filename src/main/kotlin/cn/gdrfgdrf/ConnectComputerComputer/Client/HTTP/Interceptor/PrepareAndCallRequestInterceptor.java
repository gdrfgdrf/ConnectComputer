package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Interceptor;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import cn.gdrfgdrf.ConnectComputerComputer.Common.User.User;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Request.HttpNetworkRequestBuilderKt;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.URL.URLEnum;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.BeanManager;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.RSAUtils;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import org.apache.tomcat.util.codec.binary.Base64;
import org.jetbrains.annotations.NotNull;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class PrepareAndCallRequestInterceptor implements Interceptor {
    private final DataStore dataStore;

    {
        BeanManager beanManager = BeanManager.getInstance();
        dataStore = beanManager.getBean("DataStore");
    }

    @Override
    public @NotNull Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        URLEnum urlEnum = URLEnum.getFromFullURL(request.url().encodedPath());

        if (urlEnum == null) {
            throw new RuntimeException("URLEnum cannot be null");
        }

        switch (urlEnum.getMethod()) {
            case GET -> {
                Request.Builder builder = new Request.Builder(request)
                        .get();

                request = builder.build();
            }

            case POST, PUT -> {
                if (request.body() != null && urlEnum.getEncrypted()) {
                    RequestBody requestBody = request.body();
                    if (requestBody instanceof MultipartBody) {
                        break;
                    }

                    String string = requestBodyToString(requestBody);

                    if (GlobalConfiguration.SERVER_PUBLIC_KEY != null) {
                        try {
                            string = RSAUtils.publicEncrypt(
                                    string, GlobalConfiguration.SERVER_PUBLIC_KEY
                            ).toString();
                        } catch (Exception e) {
                            throw new RuntimeException("Cannot encrypt request body", e);
                        }

                        RequestBody newRequestBody = RequestBody.create(
                                string,
                                HttpNetworkRequestBuilderKt.getMEDIA_TYPE_JSON()
                        );
                        Request.Builder builder = new Request.Builder(request)
                                .method(urlEnum.getMethod().getMethod(), newRequestBody);

                        request = builder.build();
                    } else {
                        throw new RuntimeException("Server public key cannot be null");
                    }
                }
            }
        }

        if (urlEnum.getNeedPublicKey()) {
            Request.Builder builder = new Request.Builder(request)
                    .addHeader(
                            "publicKey", Base64.encodeBase64String(
                                    dataStore.getRsa().getPublicKey().getEncoded()
                            )
                    );

            request = builder.build();
        }

        if (urlEnum.getNeedToken()) {
            if (User.INSTANCE.getToken() == null) {
                throw new RuntimeException("Token cannot be null");
            }

            Request.Builder builder;
            try {
                builder = new Request.Builder(request)
                        .addHeader(
                                "token", RSAUtils.publicEncrypt(
                                        User.INSTANCE.getToken(),
                                        GlobalConfiguration.SERVER_PUBLIC_KEY
                                ).toString()
                        );
            } catch (Exception e) {
                throw new RuntimeException("Cannot encrypt token", e);
            }

            request = builder.build();
        }
        if (urlEnum.getNeedClientVersion()) {
            if (StringUtils.isBlank(Constants.VERSION_ENCRYPTED)) {
                throw new RuntimeException("VERSION ENCRYPTED is blank");
            }

            request = new Request.Builder(request)
                    .addHeader("clientVersion", Constants.VERSION_ENCRYPTED)
                    .build();
        }

        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();

        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.getBuffer();

        String body = buffer.clone().readString(StandardCharsets.UTF_8);

        if (!StringUtils.isBlank(body) && urlEnum.getDecrypted()) {
            try {
                body = new String(
                        RSAUtils.privateDecrypt(
                                body,
                                dataStore.getRsa().getPrivateKey()
                        ).toString().getBytes(Charset.defaultCharset())
                );
            } catch (Exception e) {
                throw new RuntimeException("Cannot decrypt response body", e);
            }
        } else {
            body = new String(
                    body.getBytes(Charset.defaultCharset())
            );
        }
        ResponseBody newResponseBody = ResponseBody.create(
                body,
                HttpNetworkRequestBuilderKt.getMEDIA_TYPE_JSON()
        );
        response = response.newBuilder().body(newResponseBody).build();

        return response;
    }

    public String requestBodyToString(RequestBody requestBody) {
        Buffer buffer = new Buffer();

        try {
            requestBody.writeTo(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Charset charset = StandardCharsets.UTF_8;
        MediaType contentType = requestBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(StandardCharsets.UTF_8);
        }

        if (charset == null) {
            return null;
        }

        return buffer.readString(charset);
    }

}

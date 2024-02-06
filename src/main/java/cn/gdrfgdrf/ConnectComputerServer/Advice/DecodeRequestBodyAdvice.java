package cn.gdrfgdrf.ConnectComputerServer.Advice;

import cn.gdrfgdrf.ConnectComputerServer.Annotation.SecurityParameter;
import cn.gdrfgdrf.ConnectComputerServer.Exception.IllegalParameterException;
import cn.gdrfgdrf.ConnectComputerServer.Enum.RSAKeyEnum;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.ConnectComputerServer.Utils.RSAUtils;
import lombok.NonNull;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @author gdrfgdrf
 */
@ControllerAdvice
public class DecodeRequestBodyAdvice implements RequestBodyAdvice {

    @Override
    public boolean supports(
            @NonNull MethodParameter methodParameter,
            @NonNull Type targetType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return true;
    }

    @Override
    public @NonNull HttpInputMessage beforeBodyRead(
            @NonNull HttpInputMessage inputMessage,
            MethodParameter methodParameter,
            @NonNull Type type,
            @NonNull Class<? extends HttpMessageConverter<?>> clazz
    ) {
        boolean decode = false;

        if (methodParameter.getMethod() != null) {
            if (methodParameter.getMethod().isAnnotationPresent(SecurityParameter.class)) {
                SecurityParameter securityParameter = methodParameter.getMethodAnnotation(SecurityParameter.class);

                if (securityParameter != null) {
                    decode = securityParameter.parameterDecode();
                }
            }
        }

        if (decode) {
            if (inputMessage.getHeaders().containsKey("publicKey")) {
                try {
                    return new MyHttpInputMessage(inputMessage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new IllegalParameterException(
                        ResultEnum.ERROR_NOT_FOUND_PUBLIC_KEY_IN_HEADER
                );
            }
        } else {
            return inputMessage;
        }
    }

    @Override
    public @NonNull Object afterBodyRead(
            @NonNull Object body,
            @NonNull HttpInputMessage inputMessage,
            @NonNull MethodParameter parameter,
            @NonNull Type targetType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return body;
    }

    @Override
    public Object handleEmptyBody(
            Object body,
            @NonNull HttpInputMessage inputMessage,
            @NonNull MethodParameter parameter,
            @NonNull Type targetType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return body;
    }

    public static class MyHttpInputMessage implements HttpInputMessage {
        private final HttpHeaders httpHeaders;
        private final InputStream inputStream;

        public MyHttpInputMessage(HttpInputMessage httpInputMessage) throws Exception {
            this.httpHeaders = httpInputMessage.getHeaders();

            String body = IOUtils.toString(httpInputMessage.getBody(), StandardCharsets.UTF_8);
            body = body.replace(".", "//");

            this.inputStream = IOUtils.toInputStream(
                    RSAUtils.privateDecrypt(body, RSAKeyEnum.HTTP_KEY.getPrivateKey()),
                    StandardCharsets.UTF_8
            );
        }

        @Override
        public @NonNull InputStream getBody() {
            return this.inputStream;
        }

        @Override
        public @NonNull HttpHeaders getHeaders() {
            return this.httpHeaders;
        }
    }
}

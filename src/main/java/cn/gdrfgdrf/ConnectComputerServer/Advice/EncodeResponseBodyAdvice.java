package cn.gdrfgdrf.ConnectComputerServer.Advice;

import cn.gdrfgdrf.ConnectComputerServer.Annotation.SecurityParameter;
import cn.gdrfgdrf.ConnectComputerServer.Exception.IllegalParameterException;
import cn.gdrfgdrf.ConnectComputerServer.Result.Result;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.ConnectComputerServer.Utils.CharsetUtils;
import cn.gdrfgdrf.ConnectComputerServer.Utils.JacksonUtils;
import cn.gdrfgdrf.ConnectComputerServer.Utils.RSAUtils;
import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Objects;

/**
 * @author gdrfgdrf
 */
@ControllerAdvice
public class EncodeResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter methodParameter,
            @NonNull MediaType mediaType,
            @NonNull Class clazz,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response
    ) {
        boolean encode = false;

        if (methodParameter.getMethod() != null) {
            if (methodParameter.getMethod().isAnnotationPresent(SecurityParameter.class)) {
                SecurityParameter securityParameter = methodParameter.getMethodAnnotation(SecurityParameter.class);
                assert securityParameter != null;

                encode = securityParameter.responseEncode();
            }
        }

        Charset charset = CharsetUtils.getRequestCharsetOrDefault(request, StandardCharsets.UTF_8);
        MediaType responseMediaType = MediaType.parseMediaType("application/json; charset=" + charset.name());
        response.getHeaders().setContentType(responseMediaType);

        if (encode) {
            try {
                if (request.getHeaders().containsKey("publicKey")) {
                    PublicKey publicKey = RSAUtils.getPublicKey(Objects.requireNonNull(request.getHeaders().get("publicKey")).get(0));

                    String resultString = JacksonUtils.writeJsonString(body);
                    resultString = RSAUtils.publicEncrypt(resultString.getBytes(charset), publicKey).toString();

                    return resultString;
                } else {
                    throw new IllegalParameterException(
                            ResultEnum.ERROR_NOT_FOUND_PUBLIC_KEY_IN_HEADER
                    );
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return body;
    }
}

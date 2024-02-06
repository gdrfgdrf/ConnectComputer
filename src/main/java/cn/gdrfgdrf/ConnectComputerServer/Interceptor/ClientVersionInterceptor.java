package cn.gdrfgdrf.ConnectComputerServer.Interceptor;

import cn.gdrfgdrf.ConnectComputerServer.Annotation.ClientVersion;
import cn.gdrfgdrf.ConnectComputerServer.Enum.RSAKeyEnum;
import cn.gdrfgdrf.ConnectComputerServer.Enum.VersionEnum;
import cn.gdrfgdrf.ConnectComputerServer.Exception.IllegalParameterException;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.ConnectComputerServer.Utils.RSAUtils;
import cn.gdrfgdrf.ConnectComputerServer.Utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

/**
 * @author gdrfgdrf
 */
public class ClientVersionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        Method method = handlerMethod.getMethod();
        Class<?> clazz = method.getDeclaringClass();

        ClientVersion clientVersion = clazz.getAnnotation(ClientVersion.class);
        if (clientVersion != null) {
            ClientVersion.Exclude exclude = method.getAnnotation(ClientVersion.Exclude.class);
            if (exclude != null) {
                return true;
            }

            return processClientVersion(request, clientVersion);
        }

        clientVersion = method.getAnnotation(ClientVersion.class);
        if (clientVersion != null) {
            return processClientVersion(request, clientVersion);
        }

        return true;
    }

    private boolean processClientVersion(
            HttpServletRequest request,
            ClientVersion clientVersion
    ) throws Exception {
        String headerClientVersion = request.getHeader("clientVersion");
        if (StringUtils.isBlank(headerClientVersion)) {
            throw new IllegalParameterException(
                    ResultEnum.ERROR_NOT_FOUND_CLIENT_VERSION_IN_HEADER
            );
        }
        headerClientVersion = RSAUtils.privateDecrypt(
                        headerClientVersion,
                        RSAKeyEnum.HTTP_KEY.getPrivateKey()
                )
                .toString();

        VersionEnum clientVersionEnum = clientVersion.version();
        VersionEnum headerClientVersionEnum;

        try {
            headerClientVersionEnum = VersionEnum.valueOf(headerClientVersion);
        } catch (Exception ignored) {
            throw new IllegalParameterException(
                    ResultEnum.ERROR_CLIENT_VERSION_ILLEGAL
            );
        }
        if (!VersionEnum.compare(clientVersionEnum, headerClientVersionEnum)) {
            throw new IllegalParameterException(
                    ResultEnum.VERSION_TOO_LOW
            );
        }

        return true;
    }
}

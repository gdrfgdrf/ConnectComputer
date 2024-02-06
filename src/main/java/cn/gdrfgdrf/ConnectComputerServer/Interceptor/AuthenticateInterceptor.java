package cn.gdrfgdrf.ConnectComputerServer.Interceptor;

import cn.gdrfgdrf.ConnectComputerServer.Annotation.NeedToken;
import cn.gdrfgdrf.ConnectComputerServer.Annotation.PassToken;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import cn.gdrfgdrf.ConnectComputerServer.Exception.IllegalParameterException;
import cn.gdrfgdrf.ConnectComputerServer.Enum.RSAKeyEnum;
import cn.gdrfgdrf.ConnectComputerServer.Holder.SpringContextHolder;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.ConnectComputerServer.Service.Database.IUserEntityService;
import cn.gdrfgdrf.ConnectComputerServer.Utils.RSAUtils;
import cn.gdrfgdrf.ConnectComputerServer.Utils.TokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author gdrfgdrf
 */
public class AuthenticateInterceptor implements HandlerInterceptor {
    private final IUserEntityService userEntityService = SpringContextHolder.getBean(IUserEntityService.class);

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        Method method = ((HandlerMethod) handler).getMethod();
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);

            if (passToken.required()) {
                return true;
            }
        }

        if (method.isAnnotationPresent(NeedToken.class)) {
            NeedToken needToken = method.getAnnotation(NeedToken.class);
            return processNeedToken(needToken, request);
        }
        return true;
    }

    @SuppressWarnings("all")
    private boolean processNeedToken(NeedToken needToken, HttpServletRequest request) throws Exception {
        if (!needToken.required()) {
            return true;
        }

        String token = request.getHeader("token");
        if (token == null) {
            throw new IllegalParameterException(
                    ResultEnum.ERROR_NOT_FOUND_TOKEN_IN_HEADER
            );
        }
        token = RSAUtils.privateDecrypt(token, RSAKeyEnum.HTTP_KEY.getPrivateKey())
                .toString();

        try {
            TokenUtils.verifyToken(token);
        } catch (ExpiredJwtException e) {
            throw new IllegalParameterException(
                    ResultEnum.ERROR_TOKEN_EXPIRED
            );
        } catch (UnsupportedJwtException |
                 MalformedJwtException |
                 SignatureException |
                 IllegalArgumentException e) {
            throw new IllegalParameterException(
                    ResultEnum.ERROR_TOKEN_INVALID
            );
        }

        Map<String, Object> map = TokenUtils.parseToken(token);
        if (map == null) {
            throw new IllegalParameterException(
                    ResultEnum.ERROR_TOKEN_INVALID
            );
        }

        Object idObj = map.get("id");
        if (idObj == null) {
            throw new IllegalParameterException(
                    ResultEnum.ERROR_TOKEN_INVALID
            );
        }

        Integer id = Integer.parseInt(idObj.toString());
        UserEntity user = userEntityService.getById(id);
        if (user != null && !user.getTokenEntities().isEmpty()) {
            if (user.containsToken(token)) {
                return true;
            } else {
                throw new IllegalParameterException(
                        ResultEnum.ERROR_TOKEN_INVALID
                );
            }
        } else {
            throw new IllegalParameterException(
                    ResultEnum.ERROR_TOKEN_INVALID
            );
        }
    }
}

package cn.gdrfgdrf.ConnectComputerServer.Resolver;

import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserWithChangePasswordEntity;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserWithMultipartFileEntity;
import cn.gdrfgdrf.ConnectComputerServer.Exception.IllegalParameterException;
import cn.gdrfgdrf.ConnectComputerServer.Enum.RSAKeyEnum;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.ConnectComputerServer.Service.Database.IUserEntityService;
import cn.gdrfgdrf.ConnectComputerServer.Utils.Jackson.SuperJsonNode;
import cn.gdrfgdrf.ConnectComputerServer.Utils.JacksonUtils;
import cn.gdrfgdrf.ConnectComputerServer.Utils.RSAUtils;
import cn.gdrfgdrf.ConnectComputerServer.Utils.StringUtils;
import cn.gdrfgdrf.ConnectComputerServer.Utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author gdrfgdrf
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    private final IUserEntityService userEntityService;

    @Autowired
    public UserArgumentResolver(IUserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == UserEntity.class ||
                clazz == UserWithChangePasswordEntity.class ||
                clazz == UserWithMultipartFileEntity.class;
    }

    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        if (request == null) {
            return null;
        }

        if (parameter.getParameterType() == UserEntity.class ||
                parameter.getParameterType() == UserWithChangePasswordEntity.class ||
                parameter.getParameterType() == UserWithMultipartFileEntity.class) {

            String headerToken = request.getHeader("token");
            headerToken = RSAUtils.privateDecrypt(headerToken, RSAKeyEnum.HTTP_KEY.getPrivateKey()).toString();

            UserEntity tokenUser = token2User(headerToken);
            UserEntity bodyUser;

            String body = getRequestBody(request);
            if (parameter.getParameterType() == UserEntity.class) {
                bodyUser = body2User(body);
            } else {
                if (parameter.getParameterType() == UserWithChangePasswordEntity.class) {
                    bodyUser = body2UserWithChangePassword(body);
                } else {
                    bodyUser = request2UserWithMultipartFile(request);
                }
            }

            if (tokenUser == null) {
                throw new IllegalParameterException(
                        ResultEnum.ERROR_TOKEN_INVALID
                );
            }

            return mergeUserWithSubUser(tokenUser, bodyUser);
        }

        throw new IllegalParameterException(
                ResultEnum.ERROR
        );
    }

    private UserEntity mergeUserWithSubUser(UserEntity tokenUser, UserEntity bodyUser) {
        if (bodyUser instanceof UserWithChangePasswordEntity userWithChangePassword) {
            userWithChangePassword.setId(tokenUser.getId());
            return userWithChangePassword;
        } else {
            if (bodyUser instanceof UserWithMultipartFileEntity userWithMultipartFileEntity) {
                userWithMultipartFileEntity.setId(tokenUser.getId());
                return userWithMultipartFileEntity;
            } else {
                tokenUser.setDisplayName(bodyUser.getDisplayName());
            }
        }
        return tokenUser;
    }

    private UserEntity token2User(String token) {
        Map<String, Object> map = TokenUtils.parseToken(token);
        return userEntityService.getById(Integer.valueOf(map.get("id").toString()));
    }

    private UserEntity body2User(String body)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeyException {
        SuperJsonNode jsonNode = JacksonUtils.readStringTree(body);

        String id = jsonNode.getStringOrNull("id");
        if (id != null) {
            if (!StringUtils.verifyInteger(id)) {
                throw new IllegalParameterException(
                        ResultEnum.ERROR_ID_ILLEGAL
                );
            }
        }

        String password = jsonNode.getStringOrNull("password");
        if (password != null) {
            password = RSAUtils.privateDecrypt(
                    password,
                    RSAKeyEnum.HTTP_KEY.getPrivateKey()
            ).toString();
        }

        String displayName = jsonNode.getStringOrNull("displayName");

        UserEntity user = new UserEntity();
        user.setId(id != null ? Integer.parseInt(id) : null);
        user.setPassword(password);
        user.setDisplayName(displayName);

        return user;
    }

    private UserWithChangePasswordEntity body2UserWithChangePassword(String body)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeyException {
        SuperJsonNode jsonNode = JacksonUtils.readStringTree(body);

        String originalPassword = jsonNode.getStringOrNull("originalPassword");
        if (originalPassword != null) {
            originalPassword = RSAUtils.privateDecrypt(
                    originalPassword,
                    RSAKeyEnum.HTTP_KEY.getPrivateKey()
            ).toString();
        }

        String id = jsonNode.getStringOrNull("id");
        if (id != null) {
            if (!StringUtils.verifyInteger(id)) {
                throw new IllegalParameterException(
                        ResultEnum.ERROR_ID_ILLEGAL
                );
            }
        }

        String password = jsonNode.getStringOrNull("password");
        if (password != null) {
            password = RSAUtils.privateDecrypt(
                    password,
                    RSAKeyEnum.HTTP_KEY.getPrivateKey()
            ).toString();
        }

        UserWithChangePasswordEntity userWithChangePassword = new UserWithChangePasswordEntity();
        userWithChangePassword.setId(id != null ? Integer.parseInt(id) : null);
        userWithChangePassword.setOriginalPassword(originalPassword);
        userWithChangePassword.setPassword(password);

        return userWithChangePassword;
    }

    private UserWithMultipartFileEntity request2UserWithMultipartFile(HttpServletRequest request) throws Exception {
        String jsonStr = request.getParameterValues("json")[0];
        jsonStr = RSAUtils.privateDecrypt(jsonStr, RSAKeyEnum.HTTP_KEY.getPrivateKey()).toString();
        SuperJsonNode jsonNode = JacksonUtils.readStringTree(jsonStr);

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");

        String id = jsonNode.getStringOrNull("id");
        if (id != null) {
            if (!StringUtils.verifyInteger(id)) {
                throw new IllegalParameterException(
                        ResultEnum.ERROR_ID_ILLEGAL
                );
            }
        }

        UserWithMultipartFileEntity userWithMultipartFileEntity = new UserWithMultipartFileEntity();
        userWithMultipartFileEntity.setId(id != null ? Integer.parseInt(id) : null);
        userWithMultipartFileEntity.setMultipartFile(multipartFile);

        return userWithMultipartFileEntity;
    }

    private String getRequestBody(HttpServletRequest request) throws Exception {
        String body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        body = RSAUtils.privateDecrypt(body, RSAKeyEnum.HTTP_KEY.getPrivateKey()).toString();

        return body;
    }
}

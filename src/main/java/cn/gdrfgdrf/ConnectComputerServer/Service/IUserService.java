package cn.gdrfgdrf.ConnectComputerServer.Service;

import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserWithChangePasswordEntity;
import cn.gdrfgdrf.ConnectComputerServer.Result.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author gdrfgdrf
 */
public interface IUserService {
    Result login(UserEntity tempUser) throws Exception;
    Result updateLogin(UserEntity tempUser) throws Exception;
    Result logout(UserEntity tempUser) throws Exception;
    Result changePassword(UserWithChangePasswordEntity tempUser) throws Exception;

    Result userInfo(String id) throws Exception;

    Result changeAvatar(UserEntity tempUser, MultipartFile file) throws Exception;
    void getAvatar(String id, HttpServletResponse response) throws Exception;
    Result getAvatarSha256(String id) throws Exception;

    Result changeDisplayName(UserEntity tempUser) throws Exception;
}

package cn.gdrfgdrf.ConnectComputerServer.Service.Impl;

import cn.gdrfgdrf.ConnectComputerServer.Bean.Information.User.AvatarInformation;
import cn.gdrfgdrf.ConnectComputerServer.Bean.Information.User.UserInformation;
import cn.gdrfgdrf.ConnectComputerServer.Bean.Information.User.UserSecretInformation;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.TokenEntity;
import cn.gdrfgdrf.ConnectComputerServer.Enum.RSAKeyEnum;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UploadFileEntity;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserWithChangePasswordEntity;
import cn.gdrfgdrf.ConnectComputerServer.Bean.Enum.UploadFileForWhatEnum;
import cn.gdrfgdrf.ConnectComputerServer.Bean.Enum.UserGroupEnum;
import cn.gdrfgdrf.ConnectComputerServer.Exception.IllegalParameterException;
import cn.gdrfgdrf.ConnectComputerServer.Global.Global;
import cn.gdrfgdrf.ConnectComputerServer.Result.Result;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.ConnectComputerServer.Service.Database.ITokenEntityService;
import cn.gdrfgdrf.ConnectComputerServer.Service.Database.IUserEntityService;
import cn.gdrfgdrf.ConnectComputerServer.Service.IUserService;
import cn.gdrfgdrf.ConnectComputerServer.Service.Database.IUploadFileService;
import cn.gdrfgdrf.ConnectComputerServer.Utils.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static cn.gdrfgdrf.ConnectComputerServer.Global.Global.PASSWORD_REGEX;

/**
 * @author gdrfgdrf
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    private final IUserEntityService userEntityService;
    private final ITokenEntityService tokenEntityService;
    private final IUploadFileService uploadFileService;

    @Autowired
    public UserServiceImpl(
            IUserEntityService userEntityService,
            ITokenEntityService tokenEntityService,
            IUploadFileService uploadFileService
    ) {
        this.userEntityService = userEntityService;
        this.tokenEntityService = tokenEntityService;
        this.uploadFileService = uploadFileService;
    }

    @Transactional
    @Override
    public Result login(UserEntity tempUser) throws Exception {
        tempUser.setPassword(
                RSAUtils.privateDecrypt(
                        tempUser.getPassword(),
                        RSAKeyEnum.HTTP_KEY.getPrivateKey()
                ).toString()
        );
        if (!StringUtils.verifyByPattern(tempUser.getPassword(), PASSWORD_REGEX)) {
            throw new IllegalParameterException(ResultEnum.ERROR_PASSWORD_ILLEGAL);
        }

        UserEntity user = userEntityService.findByUsername(tempUser.getUsername());
        if (user == null) {
            String salt = PBKDF2Utils.generateSalt();

            user = new UserEntity();
            user.setId(null);
            user.setUsername(tempUser.getUsername());
            user.setDisplayName(tempUser.getUsername());
            user.setPassword(
                    PBKDF2Utils.getEncryptedPassword(
                            tempUser.getPassword(),
                            salt
                    )
            );
            user.setSalt(salt);
            user.setUserGroup(UserGroupEnum.USER);
            userEntityService.save(user);

            TokenEntity token = TokenUtils.create(user);
            tokenEntityService.save(token);

            Result result = new Result().setFromResultEnum(ResultEnum.SUCCESS_LOGIN);
            UserSecretInformation userSecretInformation = UserSecretInformation.createByUser(user, token.getContent());
            result.addData(userSecretInformation);

            log.info("New user registered: {}", user.getUsername());

            return result;
        } else {
            Result result;

            if (PBKDF2Utils.authenticate(
                    tempUser.getPassword(),
                    user.getPassword(),
                    user.getSalt())
            ) {
                TokenEntity token = TokenUtils.create(user);
                tokenEntityService.save(token);

                UserSecretInformation userSecretInformation = UserSecretInformation.createByUser(user, token.getContent());
                result = new Result().setFromResultEnum(ResultEnum.SUCCESS_LOGIN);
                result.addData(userSecretInformation);

                log.info("User login: {}", user.getUsername());
            } else {
                result = new Result().setFromResultEnum(
                        ResultEnum.ERROR_PASSWORD_INCORRECT_OR_NOT_FOUND_USER
                );
            }

            return result;
        }
    }

    @Override
    public Result updateLogin(UserEntity tempUser) {
        UserEntity user = userEntityService.getById(tempUser.getId());

        if (user != null) {
            TokenEntity token = TokenUtils.create(user);
            tokenEntityService.save(token);

            Result result = new Result().setFromResultEnum(ResultEnum.SUCCESS_LOGIN);
            UserSecretInformation userSecretInformation = UserSecretInformation.createByUser(user, token.getContent());
            result.addData(userSecretInformation);

            log.info("User update login: {}", user.getUsername());

            return result;
        } else {
            throw new IllegalParameterException(
                    ResultEnum.ERROR_NOT_FOUND_USER
            );
        }
    }

    @Override
    public Result logout(UserEntity tempUser) {
        UserEntity user = userEntityService.getById(tempUser.getId());

        if (user != null) {
            QueryWrapper<TokenEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("userId", user.getId());
            tokenEntityService.remove(queryWrapper);

            Result result = new Result();
            result.setFromResultEnum(ResultEnum.SUCCESS_LOGOUT);

            log.info("User logout: {}", user.getUsername());
            return result;
        } else {
            throw new IllegalParameterException(
                    ResultEnum.ERROR_NOT_FOUND_USER
            );
        }
    }

    @Override
    public Result changePassword(UserWithChangePasswordEntity tempUser) throws Exception {
        tempUser.setOriginalPassword(
                RSAUtils.privateDecrypt(
                        tempUser.getOriginalPassword(),
                        RSAKeyEnum.HTTP_KEY.getPrivateKey()
                ).toString()
        );
        tempUser.setPassword(
                RSAUtils.privateDecrypt(
                        tempUser.getPassword(),
                        RSAKeyEnum.HTTP_KEY.getPrivateKey()
                ).toString()
        );

        if (tempUser.getPassword().length() > 255) {
            throw new IllegalParameterException(
                    ResultEnum.ERROR_PASSWORD_OUT_OF_LIMITED
            );
        }
        if (!StringUtils.verifyByPattern(tempUser.getPassword(), PASSWORD_REGEX)) {
            throw new IllegalParameterException(
                    ResultEnum.ERROR_PASSWORD_ILLEGAL
            );
        }

        UserEntity user = userEntityService.getById(tempUser.getId());
        if (user != null) {
            String originalSalt = user.getSalt();
            tempUser.setOriginalPassword(
                    PBKDF2Utils.getEncryptedPassword(
                            tempUser.getOriginalPassword(),
                            originalSalt)
            );

            if (user.getPassword().equals(tempUser.getOriginalPassword())) {
                String salt = PBKDF2Utils.generateSalt();
                user.setPassword(
                        PBKDF2Utils.getEncryptedPassword(
                                tempUser.getPassword(),
                                salt
                        )
                );
                user.setSalt(salt);
                user.getTokenEntities().clear();

                userEntityService.updateById(user);

                Result result = new Result();
                result.setFromResultEnum(ResultEnum.SUCCESS_CHANGE_PASSWORD);

                log.info("User {} changed password", user.getUsername());

                return result;
            } else {
                throw new IllegalParameterException(
                        ResultEnum.ERROR_ORIGINAL_PASSWORD_INCORRECT
                );
            }
        } else {
            throw new IllegalParameterException(
                    ResultEnum.ERROR_NOT_FOUND_USER
            );
        }
    }

    @Cacheable(cacheNames = "user_info", key = "#p0")
    @Override
    public Result userInfo(String id) {
        UserEntity user = userEntityService.getById(Integer.parseInt(id));
        if (user != null) {
            UserInformation userInformation = UserInformation.createByUser(user);

            Result result = new Result();
            result.setFromResultEnum(ResultEnum.SUCCESS_GET);
            result.addData(userInformation);

            return result;
        } else {
            throw new IllegalParameterException(ResultEnum.ERROR_NOT_FOUND_USER);
        }
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "user_info", key = "#tempUser.id"),
                    @CacheEvict(cacheNames = "user_avatar_sha256", key = "#tempUser.id")
            }
    )
    @Override
    public Result changeAvatar(UserEntity tempUser, MultipartFile multipartFile) {
        UserEntity user = userEntityService.getById(tempUser.getId());

        if (user != null) {
            UploadFileEntity uploadFile = uploadFileService.upload(
                    user, multipartFile.getOriginalFilename(),
                    multipartFile, UploadFileForWhatEnum.AVATAR
            );
            user.setAvatarUrl(Global.AVATAR_FILE_PATH + "/" + uploadFile.getFileName());

            userEntityService.updateById(user);
        } else {
            throw new IllegalParameterException(
                    ResultEnum.ERROR_NOT_FOUND_USER
            );
        }

        Result result = new Result();
        result.setFromResultEnum(ResultEnum.SUCCESS_UPLOAD_FILE);

        return result;
    }

    @Override
    public void getAvatar(String id, HttpServletResponse response) throws Exception {
        UserEntity user = userEntityService.getById(Integer.parseInt(id));

        if (user != null) {
            File avatarFile = FileUtils.getAvatarFile(user);

            String filename = avatarFile.getName();
            InputStream inputStream = new FileInputStream(avatarFile);
            response.reset();
            response.setContentType("application/octet-stream");
            response.addHeader(
                    "Content-Disposition",
                    "attachment; filename=" +
                            URLEncoder.encode(filename, StandardCharsets.UTF_8)
            );

            ServletOutputStream outputStream = response.getOutputStream();

            byte[] bytes = new byte[1024];
            int length;
            while ((length = inputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, length);
            }
            inputStream.close();
        } else {
            throw new IllegalParameterException(ResultEnum.ERROR_NOT_FOUND_USER);
        }
    }

    @Cacheable(cacheNames = "user_avatar_sha256", key = "#p0")
    @Override
    public Result getAvatarSha256(String id) {
        UserEntity user = userEntityService.getById(Integer.parseInt(id));

        if (user != null) {
            File avatarFile = FileUtils.getAvatarFile(user);

            String sha256;
            if (avatarFile == Global.DEFAULT_AVATAR) {
                sha256 = Global.DEFAULT_AVATAR_SHA256;
            } else {
                QueryWrapper<UploadFileEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("path", avatarFile.getAbsolutePath());
                UploadFileEntity uploadFile = uploadFileService.getOne(queryWrapper);

                if (uploadFile != null) {
                    sha256 = uploadFile.getSha256();
                } else {
                    sha256 = Global.DEFAULT_AVATAR_SHA256;
                }
            }

            AvatarInformation avatarInformation = new AvatarInformation();
            avatarInformation.setSha256(sha256);

            Result result = new Result().setFromResultEnum(ResultEnum.SUCCESS_GET);
            result.addData(avatarInformation);

            return result;
        } else {
            throw new IllegalParameterException(
                    ResultEnum.ERROR_NOT_FOUND_USER
            );
        }
    }

    @CacheEvict(cacheNames = "user_info", key = "#tempUser.id")
    @Override
    public Result changeDisplayName(UserEntity tempUser) {
        UserEntity user = userEntityService.getById(tempUser.getId());

        if (user != null) {
            user.setDisplayName(tempUser.getDisplayName());
            userEntityService.updateById(user);

            Result result = new Result();
            result.setFromResultEnum(ResultEnum.SUCCESS_CHANGE);

            return result;
        } else {
            throw new IllegalParameterException(
                    ResultEnum.ERROR_NOT_FOUND_USER
            );
        }
    }
}

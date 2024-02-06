package cn.gdrfgdrf.ConnectComputerServer.Service.Database.Impl;

import cn.gdrfgdrf.ConnectComputerServer.Bean.Enum.FileTypeEnum;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UploadFileEntity;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import cn.gdrfgdrf.ConnectComputerServer.Bean.Enum.UploadFileForWhatEnum;
import cn.gdrfgdrf.ConnectComputerServer.Exception.IllegalParameterException;
import cn.gdrfgdrf.ConnectComputerServer.Global.Global;
import cn.gdrfgdrf.ConnectComputerServer.Mapper.UploadFileMapper;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.ConnectComputerServer.Service.Database.IUploadFileService;
import cn.gdrfgdrf.ConnectComputerServer.Utils.FileUtils;
import cn.gdrfgdrf.ConnectComputerServer.Utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.dreamyoung.mprelation.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author gdrfgdrf
 */
@Service
public class UploadFileServiceImpl extends ServiceImpl<UploadFileMapper, UploadFileEntity> implements IUploadFileService {
    @Transactional
    @Override
    public UploadFileEntity upload(
            UserEntity uploader,
            String realName,
            MultipartFile multipartFile,
            UploadFileForWhatEnum forWhatEnum
    ) {
        if (Objects.requireNonNull(forWhatEnum) == UploadFileForWhatEnum.AVATAR) {
            if (multipartFile.getSize() > Global.AVATAR_FILE_MAX_SIZE_IN_KB * 1024) {
                throw new IllegalParameterException(
                        ResultEnum.ERROR_FILE_TOO_BIG
                );
            }

            String originalFilename = multipartFile.getOriginalFilename();
            if (originalFilename == null) {
                throw new IllegalParameterException(
                        ResultEnum.ERROR_UPLOAD_FILE
                );
            }

            String primaryName = FileUtils.getBaseName(originalFilename);
            String extension = FileUtils.getExtension(originalFilename);

            boolean correctExtension = FileTypeEnum.isFileType(
                    FileTypeEnum.IMAGE,
                    extension
            );
            if (!correctExtension) {
                throw new IllegalParameterException(
                        ResultEnum.ERROR_INCORRECT_FILE_TYPE
                );
            }

            String fileName = StringUtils.getRandomName(originalFilename);
            String filePath = Global.AVATAR_FILE_PATH + "/" + fileName;

            while (new File(filePath).exists()) {
                fileName = StringUtils.getRandomName(originalFilename);
                filePath = Global.AVATAR_FILE_PATH + "/" + fileName;
            }

            try {
                File dest = new File(filePath).getCanonicalFile();
                if (!dest.getParentFile().exists()) {
                    if (!dest.getParentFile().mkdirs()) {
                        throw new IllegalParameterException(
                                ResultEnum.ERROR_UPLOAD_FILE
                        );
                    }
                }

                multipartFile.transferTo(dest);

                Integer integer = null;
                if (uploader.getAvatarUrl() != null) {
                    File avatarFile = new File(uploader.getAvatarUrl());
                    UploadFileEntity discardAvatar = this.findByPath(avatarFile.getAbsolutePath());

                    if (discardAvatar != null) {
                        integer = discardAvatar.getId();

                        File file = new File(discardAvatar.getPath());

                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }

                UploadFileEntity uploadFile = new UploadFileEntity();
                uploadFile.setId(integer);
                uploadFile.setRealName(realName);
                uploadFile.setFileName(fileName);
                uploadFile.setPrimaryName(primaryName);
                uploadFile.setExtension(extension);
                uploadFile.setPath(dest.getPath());
                uploadFile.setFileType(FileTypeEnum.IMAGE);
                uploadFile.setSize(multipartFile.getSize());
                uploadFile.setSha256(FileUtils.getSha256(dest));

                uploadFile.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
                uploadFile.setUploader(uploader.getId());

                boolean result;
                if (integer == null) {
                    result = this.save(uploadFile);
                } else {
                    result = this.updateById(uploadFile);
                }

                if (result) {
                    return uploadFile;
                }

                throw new IllegalParameterException(
                        ResultEnum.ERROR_UPLOAD_FILE
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Transactional
    @Override
    public UploadFileEntity findByPath(String path) {
        QueryWrapper<UploadFileEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("path", path);

        return getOne(queryWrapper);
    }
}

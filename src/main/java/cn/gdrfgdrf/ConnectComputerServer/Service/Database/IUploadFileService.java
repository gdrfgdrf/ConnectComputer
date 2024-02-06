package cn.gdrfgdrf.ConnectComputerServer.Service.Database;

import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UploadFileEntity;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import cn.gdrfgdrf.ConnectComputerServer.Bean.Enum.UploadFileForWhatEnum;
import com.github.dreamyoung.mprelation.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author gdrfgdrf
 */
public interface IUploadFileService extends IService<UploadFileEntity> {
    UploadFileEntity upload(UserEntity uploader, String realName, MultipartFile multipartFile, UploadFileForWhatEnum forWhatEnum);
    UploadFileEntity findByPath(String path);
}

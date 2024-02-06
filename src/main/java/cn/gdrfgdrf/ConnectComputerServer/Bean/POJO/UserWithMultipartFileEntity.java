package cn.gdrfgdrf.ConnectComputerServer.Bean.POJO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author gdrfgdrf
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserWithMultipartFileEntity extends UserEntity implements Serializable {
    private MultipartFile multipartFile;
}

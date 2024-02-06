package cn.gdrfgdrf.ConnectComputerServer.Bean.POJO;

import cn.gdrfgdrf.ConnectComputerServer.Bean.Enum.FileTypeEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.EnumTypeHandler;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author gdrfgdrf
 */
@Data
@TableName("uploadfile")
public class UploadFileEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("realName")
    private String realName;
    @TableField("fileName")
    private String fileName;
    @TableField("primaryName")
    private String primaryName;
    @TableField("extension")
    private String extension;
    @TableField("path")
    private String path;
    @TableField(value = "fileType", typeHandler = EnumTypeHandler.class)
    private FileTypeEnum fileType;
    @TableField("size")
    private Long size;
    @TableField("SHA256")
    private String sha256;

    @TableField("createTime")
    private Timestamp createTime;
    @TableField("uploader")
    private Integer uploader;
}

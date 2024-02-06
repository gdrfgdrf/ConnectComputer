package cn.gdrfgdrf.ConnectComputerServer.Bean.POJO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author gdrfgdrf
 */
@Data
@TableName("computer")
public class ComputerEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("ownerId")
    private Integer ownerId;

    @TableField("name")
    private String name;
}

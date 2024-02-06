package cn.gdrfgdrf.ConnectComputerServer.Bean.POJO;

import com.baomidou.mybatisplus.annotation.*;
import com.github.dreamyoung.mprelation.JoinColumn;
import com.github.dreamyoung.mprelation.ManyToOne;
import lombok.Data;

import java.io.Serializable;

/**
 * @author gdrfgdrf
 */
@Data
@TableName("token")
public class TokenEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("userId")
    private Integer userId;
    @TableField(value = "content", insertStrategy = FieldStrategy.ALWAYS, updateStrategy = FieldStrategy.ALWAYS)
    private String content;

    @TableField(exist = false)
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserEntity user;
}

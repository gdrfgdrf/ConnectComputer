package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Update;

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Information;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * @author gdrfgdrf
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class UpdateInformation extends Information {
    private Integer id;
    private String version;
    private String description;
    private Timestamp timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

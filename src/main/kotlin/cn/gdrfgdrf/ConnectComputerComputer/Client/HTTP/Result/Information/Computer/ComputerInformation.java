package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Computer;

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Information;
import cn.gdrfgdrf.ConnectComputerComputer.TableConvertor.Annotation.TableConvertible;
import cn.gdrfgdrf.ConnectComputerComputer.TableConvertor.Translator.Impl.BooleanTranslator;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author gdrfgdrf
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class ComputerInformation extends Information {
    private Integer id;
    private Integer ownerId;
    @TableConvertible
    private String name;
    @TableConvertible(translator = BooleanTranslator.class)
    private Boolean online;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }
}

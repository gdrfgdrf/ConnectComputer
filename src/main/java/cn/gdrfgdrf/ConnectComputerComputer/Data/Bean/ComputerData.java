package cn.gdrfgdrf.ConnectComputerComputer.Data.Bean;

import cn.gdrfgdrf.ConnectComputerComputer.Data.DataBean;
import lombok.ToString;

/**
 * @author gdrfgdrf
 */
@ToString
public class ComputerData implements DataBean {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

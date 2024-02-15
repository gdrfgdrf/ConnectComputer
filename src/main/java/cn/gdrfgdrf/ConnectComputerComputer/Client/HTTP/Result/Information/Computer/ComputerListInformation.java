package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Computer;

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Information;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

/**
 * @author gdrfgdrf
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class ComputerListInformation extends Information {
    private List<ComputerInformation> list = new LinkedList<>();

    public List<ComputerInformation> getList() {
        return list;
    }

    public void setList(List<ComputerInformation> list) {
        this.list = list;
    }
}

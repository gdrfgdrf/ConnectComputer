package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Update;

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Information;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

/**
 * @author gdrfgdrf
 */
@ToString
public class UpdateListInformation extends Information {
    @Setter
    @Getter
    private List<UpdateInformation> list = new LinkedList<>();
}

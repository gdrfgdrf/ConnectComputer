package cn.gdrfgdrf.ConnectComputerServer.Bean.Information.Common;

import cn.gdrfgdrf.ConnectComputerServer.Bean.Information.Information;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author gdrfgdrf
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorInformation extends Information {
    private List<String> errorMessage = new LinkedList<>();
}

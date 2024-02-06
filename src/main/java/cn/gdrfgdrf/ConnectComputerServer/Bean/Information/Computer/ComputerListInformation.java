package cn.gdrfgdrf.ConnectComputerServer.Bean.Information.Computer;

import cn.gdrfgdrf.ConnectComputerServer.Bean.Information.Information;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.ComputerEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author gdrfgdrf
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ComputerListInformation extends Information {
    @Setter
    @Getter
    private List<ComputerInformation> list = new LinkedList<>();

    public static ComputerListInformation createByComputerList(List<ComputerEntity> computerEntities) {
        ComputerListInformation computerListInformation = new ComputerListInformation();
        computerEntities.forEach(computerEntity -> {
            ComputerInformation computerInformation = ComputerInformation.createByComputer(computerEntity);
            computerListInformation.list.add(computerInformation);
        });

        return computerListInformation;
    }
}

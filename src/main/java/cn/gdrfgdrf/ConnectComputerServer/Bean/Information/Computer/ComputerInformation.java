package cn.gdrfgdrf.ConnectComputerServer.Bean.Information.Computer;

import cn.gdrfgdrf.ConnectComputerServer.Bean.Information.Information;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.ComputerEntity;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gdrfgdrf
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ComputerInformation extends Information {
    private Integer id;
    private Integer ownerId;
    private String name;
    private Boolean online;

    public static ComputerInformation createByComputer(ComputerEntity computer) {
        ComputerInformation computerInformation = new ComputerInformation();
        computerInformation.setId(computer.getId());
        computerInformation.setOwnerId(computer.getOwnerId());
        computerInformation.setName(computer.getName());
        computerInformation.setOnline(NettyServer.getInstance().computerOnline(computer.getOwnerId(), computer.getId()));

        return computerInformation;
    }
}

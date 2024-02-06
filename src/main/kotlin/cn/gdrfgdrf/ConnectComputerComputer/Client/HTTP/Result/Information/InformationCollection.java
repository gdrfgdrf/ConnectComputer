package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information;

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Annotation.InformationClass;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Common.ErrorInformation;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Common.KeyInformation;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Computer.ComputerInformation;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Computer.ComputerListInformation;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Update.UpdateInformation;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Update.UpdateListInformation;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.User.UserInformation;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.User.UserSecretInformation;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gdrfgdrf
 */
@Slf4j
@InformationClass(
        classes = {
                ErrorInformation.class,
                KeyInformation.class,

                ComputerInformation.class,
                ComputerListInformation.class,

                UpdateInformation.class,
                UpdateListInformation.class,

                UserInformation.class,
                UserSecretInformation.class
        }
)
public class InformationCollection implements Bean {
    public static final Map<String, Class<? extends Information>> MAP = new HashMap<>();

    @Override
    public void run() throws Exception {
        InformationClass informationClass = getClass().getAnnotation(InformationClass.class);
        if (informationClass == null) {
            return;
        }

        Class<? extends Information>[] classes = informationClass.classes();
        for (Class<? extends Information> clazz : classes) {
            MAP.put(clazz.getSimpleName(), clazz);

            log.info("Register Information {}", clazz.getSimpleName());
        }
    }
}

/*
 * Copyright (C) 2024 Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
    public void init() throws Exception {
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

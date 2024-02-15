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

package cn.gdrfgdrf.ConnectComputerComputer.Global;

import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.FileUtils;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.JacksonUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.Writer;

/**
 * @author gdrfgdrf
 */
@Data
@Slf4j
public class Config implements Bean {
    private String locale = "ChineseSimplified.json";

    @Override
    public void init() throws Exception {
        File file = new File("config.json");

        if (!file.exists()) {
            log.info("No config.json file found, start writing");

            String json = JacksonUtils.writeJsonString(this);

            Writer writer = FileUtils.getWriter(file);
            writer.write(json);
            writer.close();

            log.info("The config.json file is written");
        } else {
            log.info("Start loading the config.json file");

            Config newInstance = JacksonUtils.readFile(file, Config.class);

            this.locale = newInstance.getLocale();

            log.info("The config.json file is loaded");
        }
    }
}

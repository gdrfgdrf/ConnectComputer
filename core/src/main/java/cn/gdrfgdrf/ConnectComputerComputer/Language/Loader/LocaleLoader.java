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

package cn.gdrfgdrf.ConnectComputerComputer.Language.Loader;

import cn.gdrfgdrf.ConnectComputerComputer.Global.Config;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Language.Base.Language;
import cn.gdrfgdrf.ConnectComputerComputer.Language.Impl.ChineseSimplified;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.BeanManager;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.FileUtils;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.Jackson.SuperJsonNode;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.JacksonUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class LocaleLoader implements Bean {
    private final Class<? extends Language> DEFAULT_LANGUAGE = ChineseSimplified.class;

    @Override
    public void init() throws Exception {
        Config config = BeanManager.getInstance().getBean("Config");
        loadLocale(config);
    }

    public void loadLocale(Config config) throws Exception {
        File defaultLanguageFile = new File("ChineseSimplified.json");

        if (GlobalConfiguration.DEBUG) {
            if (defaultLanguageFile.exists()) {
                defaultLanguageFile.delete();
            }
        }

        if (!defaultLanguageFile.exists()) {
            log.info("The default language file ChineseSimplified.json does not exist, start writing to the file");

            Method method = DEFAULT_LANGUAGE.getMethod("writeTo", Class.class, File.class);
            method.invoke(null, ChineseSimplified.class, defaultLanguageFile);

            log.info("The default language file ChineseSimplified.json has been written");
        }

        Field[] fields = AppLocale.class.getFields();
        File file = new File(config.getLocale());
        if (!file.exists()) {
            log.info("The language file {} does not exist. Load the default language file", file.getName());
            file = defaultLanguageFile;
        }

        SuperJsonNode jsonNode = JacksonUtils.readFileTree(file);

        for (Field field : fields) {
            String value = jsonNode.getStringOrNull(field.getName());
            field.set(null, value);
        }

        log.info("The {} file is loaded", file.getName());
    }
}

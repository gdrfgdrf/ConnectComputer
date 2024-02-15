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

package cn.gdrfgdrf.ConnectComputerComputer.Language.Base;

import cn.gdrfgdrf.ConnectComputerComputer.Annotation.Keep;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.FileUtils;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.JacksonUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;

/**
 * @author gdrfgdrf
 */
public abstract class Language {
    @Keep
    public static void writeTo(Class<? extends Language> clazz, File file) throws IOException {
        ObjectNode objectNode = JacksonUtils.newTree();
        Field[] fields = clazz.getFields();

        for (Field field : fields) {
            if (field.getType() == String.class) {
                try {
                    String name = field.getName();
                    String content = (String) field.get(null);

                    objectNode.put(name, content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Writer writer = FileUtils.getWriter(file);
        writer.write(objectNode.toString());
        writer.close();
    }
}

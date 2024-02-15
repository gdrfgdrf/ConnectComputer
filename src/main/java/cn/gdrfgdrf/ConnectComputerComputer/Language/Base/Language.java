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

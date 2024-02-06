package cn.gdrfgdrf.ConnectComputerComputer.Language.Base;

import cn.gdrfgdrf.ConnectComputerComputer.Utils.FileUtils;
import com.alibaba.fastjson2.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;

/**
 * @author gdrfgdrf
 */
public abstract class Language {
    public static void writeTo(Class<? extends Language> clazz, File file) throws IOException {
        JSONObject jsonObject = new JSONObject();
        Field[] fields = clazz.getFields();

        for (Field field : fields) {
            if (field.getType() == String.class) {
                try {
                    String name = field.getName();
                    String content = (String) field.get(null);

                    jsonObject.put(name, content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Writer writer = FileUtils.getWriter(file);
        writer.write(jsonObject.toString());
        writer.close();
    }
}

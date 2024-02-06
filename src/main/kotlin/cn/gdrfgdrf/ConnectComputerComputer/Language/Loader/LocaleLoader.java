package cn.gdrfgdrf.ConnectComputerComputer.Language.Loader;

import cn.gdrfgdrf.ConnectComputerComputer.Global.Config;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Language.Base.Language;
import cn.gdrfgdrf.ConnectComputerComputer.Language.Impl.ChineseSimplified;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.BeanManager;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.FileUtils;
import com.alibaba.fastjson2.JSONObject;
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
    public void run() throws Exception {
        Config config = BeanManager.getInstance().getBean("Config");
        loadLocale(config);
    }

    public void loadLocale(Config config) throws Exception {
        File defaultLanguageFile = new File("ChineseSimplified.json");

        if (Constants.DEBUG) {
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

        String text = FileUtils.getFileContent(file);
        JSONObject jsonObject = JSONObject.parseObject(text);

        for (Field field : fields) {
            String value = jsonObject.getString(field.getName());
            field.set(null, value);
        }

        log.info("The {} file is loaded", file.getName());
    }
}

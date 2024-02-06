package cn.gdrfgdrf.ConnectComputerComputer.Global;

import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.FileUtils;
import com.alibaba.fastjson2.JSON;
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
    public void run() throws Exception {
        File file = new File("config.json");

        if (!file.exists()) {
            log.info("No config.json file found, start writing");

            String json = JSON.toJSONString(this);

            Writer writer = FileUtils.getWriter(file);
            writer.write(json);
            writer.close();

            log.info("The config.json file is written");
        } else {
            log.info("Start loading the config.json file");

            String content = FileUtils.getFileContent(file);
            Config newInstance = JSON.parseObject(content, Config.class);

            this.locale = newInstance.getLocale();

            log.info("The config.json file is loaded");
        }
    }
}
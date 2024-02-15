package cn.gdrfgdrf.ConnectComputerComputer.Utils;

import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class AppUtils {
    private AppUtils() {}

    public static Map<String, String> processArgs(String[] args) {
        Map<String, String> result = new HashMap<>();
        List<String> argumentList = new LinkedList<>();

        for (String arg : args) {
            if (arg.contains("=")) {
                argumentList.add(arg);
            }
        }

        for (String s : argumentList) {
            String[] argument = s.split("=", 2);

            String header = argument[0];
            String content = argument[1];

            result.put(header, content);
        }

        return result;
    }

    public static void exitProgram() {
        log.info(AppLocale.PROGRAM_EXIT);

        System.out.println("\nBye");
        System.exit(0);
    }
}

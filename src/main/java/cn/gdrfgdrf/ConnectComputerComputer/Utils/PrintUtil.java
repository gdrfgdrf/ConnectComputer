package cn.gdrfgdrf.ConnectComputerComputer.Utils;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author gdrfgdrf
 */
public class PrintUtil {
    private PrintUtil() {}

    public static void print(String str, Object... arguments) {
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(str, arguments);
        System.out.println(formattingTuple.getMessage());
    }

}

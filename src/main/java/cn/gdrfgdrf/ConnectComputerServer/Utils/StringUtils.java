package cn.gdrfgdrf.ConnectComputerServer.Utils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gdrfgdrf
 */
public class StringUtils {
    private StringUtils() {}

    public static String getRandomName(String fileName) {
        int index = fileName.lastIndexOf(".");
        String extension = fileName.substring(index);
        return UUID.randomUUID().toString().replace("-", "") + extension;
    }

    public static boolean isBlank(CharSequence str) {
        return org.apache.commons.lang3.StringUtils.isBlank(str);
    }

    public static boolean isEmpty(CharSequence str) {
        return org.apache.commons.lang3.StringUtils.isEmpty(str);
    }

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte aB : b) {
            sb.append(Integer.toHexString(aB & 0xFF));
        }
        return sb.toString();
    }

    public static boolean verifyInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean verifyByPattern(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

}

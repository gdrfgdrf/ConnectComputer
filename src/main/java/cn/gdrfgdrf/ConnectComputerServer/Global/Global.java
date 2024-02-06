package cn.gdrfgdrf.ConnectComputerServer.Global;

import java.io.File;

/**
 * @author gdrfgdrf
 */
public class Global {
    public static File DEFAULT_AVATAR;
    public static String DEFAULT_AVATAR_SHA256;
    public static File AVATAR_FILE_PATH;
    public static Long AVATAR_FILE_MAX_SIZE_IN_KB;

    public final static String USERNAME_REGEX = "^[a-zA-Z0-9_-]{6,18}$";
    public final static String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[A-z])[\\da-zA-Z]{6,18}$";
}

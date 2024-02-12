package cn.gdrfgdrf.ConnectComputerComputer.Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author gdrfgdrf
 */
public class FileUtils {
    private FileUtils() {}

    public static Reader getReader(File file) throws IOException {
        return new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file), StandardCharsets.UTF_8
                )
        );
    }

    public static Writer getWriter(File file) throws IOException {
        return new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(file), StandardCharsets.UTF_8)
        );
    }

}

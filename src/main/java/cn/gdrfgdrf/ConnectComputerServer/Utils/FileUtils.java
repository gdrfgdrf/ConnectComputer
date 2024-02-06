package cn.gdrfgdrf.ConnectComputerServer.Utils;

import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import cn.gdrfgdrf.ConnectComputerServer.Global.Global;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;

/**
 * @author gdrfgdrf
 */
public class FileUtils {
    private FileUtils() {}

    public static void saveResource(String path, File to) throws IOException {
        if (!to.exists()) {
            ClassPathResource classPathResource = new ClassPathResource(path);
            InputStream inputStream = classPathResource.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(to);

            int length;
            while ((length = inputStream.read()) != -1) {
                fileOutputStream.write(length);
            }

            fileOutputStream.close();
            inputStream.close();
        }
    }

    public static String getSha256(File file) throws Exception {
        InputStream fis = new FileInputStream(file);
        MessageDigest md5 = MessageDigest.getInstance("SHA256");
        byte[] buffer = new byte[1024];

        for (int numRead; (numRead = fis.read(buffer)) > 0; ) {
            md5.update(buffer, 0, numRead);
        }

        fis.close();
        return StringUtils.toHexString(md5.digest());
    }

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

    public static String getFileContent(File file) throws IOException {
        BufferedReader reader = (BufferedReader) getReader(file);

        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        return stringBuilder.toString();
    }

    public static File getAvatarFile(UserEntity user) {
        File avatarFile;

        if (user.getAvatarUrl() == null) {
            avatarFile = Global.DEFAULT_AVATAR;
        } else {
            File userAvatarFile = new File(user.getAvatarUrl());
            if (userAvatarFile.exists()) {
                avatarFile = userAvatarFile;
            } else {
                avatarFile = Global.DEFAULT_AVATAR;
            }
        }

        return avatarFile;
    }

    //Copy from org.apache.commons.compress.utils.FileNameUtils#fileNameToBaseName of Apache Common Compress
    private static String fileNameToBaseName(String name) {
        int extensionIndex = name.lastIndexOf(46);
        return extensionIndex < 0 ? name : name.substring(0, extensionIndex);
    }

    //Copy from org.apache.commons.compress.utils.FileNameUtils#fileNameToExtension of Apache Common Compress
    private static String fileNameToExtension(String name) {
        int extensionIndex = name.lastIndexOf(46);
        return extensionIndex < 0 ? "" : name.substring(extensionIndex + 1);
    }

    //Copy from org.apache.commons.compress.utils.FileNameUtils#getBaseName(java.lang.String) of Apache Common Compress
    public static String getBaseName(String filename) {
        return filename == null ? null : fileNameToBaseName((new File(filename)).getName());
    }

    //Copy from org.apache.commons.compress.utils.FileNameUtils#getExtension(java.lang.String) of Apache Common Compress
    public static String getExtension(String filename) {
        return filename == null ? null : fileNameToExtension((new File(filename)).getName());
    }
}

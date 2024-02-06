package cn.gdrfgdrf.ConnectComputerServer.Bean.Enum;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author gdrfgdrf
 */

public enum FileTypeEnum implements Serializable {
    IMAGE;

    private static final String IMAGE_EXTENSION = "bmp jpg png jpeg";

    public static boolean isFileType(FileTypeEnum fileType, String extension) {
        if (Objects.requireNonNull(fileType) == FileTypeEnum.IMAGE) {
            return IMAGE_EXTENSION.contains(extension);
        }
        return false;
    }
}

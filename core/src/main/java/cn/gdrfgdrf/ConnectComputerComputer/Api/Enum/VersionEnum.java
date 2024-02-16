package cn.gdrfgdrf.ConnectComputerComputer.Api.Enum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gdrfgdrf
 */
public enum VersionEnum {
    v1_0_0,
    v1_2_0_20240217
    ;

    public static final VersionEnum CURRENT = VersionEnum.v1_2_0_20240217;
    private static List<VersionEnum> versions;

    VersionEnum() {
        init();
    }

    private void init() {
        if (versions == null) {
            versions = new ArrayList<>();
        }

        versions.add(this);
    }

    public static boolean compare(VersionEnum versionEnum, VersionEnum beCompared) {
        int index = versions.indexOf(versionEnum);
        int beComparedIndex = versions.indexOf(beCompared);

        return index >= beComparedIndex;
    }
}

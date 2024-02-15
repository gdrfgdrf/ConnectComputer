/*
 * Copyright (C) 2024 Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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

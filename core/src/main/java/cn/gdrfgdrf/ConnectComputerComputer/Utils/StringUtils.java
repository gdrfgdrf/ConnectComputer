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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gdrfgdrf
 */
public class StringUtils {
    private StringUtils() {}

    public static boolean verifyByPattern(CharSequence str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    public static boolean checkStringForInteger(CharSequence charSequence) {
        try {
            Integer.parseInt(charSequence.toString());
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static boolean isBlank(CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    private static class ReplaceBlock {
        @Setter
        @Getter
        private int start = -1;
        @Setter
        @Getter
        private int end = -1;
    }

    public static String replaceToTemplate(String template, String full) {
        ReplaceBlock currentBlock = null;
        ReplaceBlock currentFullBlock = null;

        for (int i = 0; i < template.length(); i++) {
            char c = template.charAt(i);

            if (c == '{') {
                currentBlock = new ReplaceBlock();
                currentFullBlock = new ReplaceBlock();
                currentBlock.start = i;
                currentFullBlock.start = i;
            }

            if (c == '}') {
                assert currentBlock != null;
                currentBlock.end = i;

                for (int x = currentFullBlock.start; x < full.length() + 1; x++) {
                    if (x < full.length() + 1) {
                        char cFull = full.charAt(x - 1);
                        if (cFull == '/') {
                            currentFullBlock.end = x + 1;

                            String templatePlaceholder = template.substring(
                                    currentBlock.start,
                                    currentBlock.end + 1
                            );
                            full = new StringBuilder(full).replace(
                                    currentFullBlock.start,
                                    currentFullBlock.end,
                                    templatePlaceholder
                            ).toString();

                            break;
                        }
                    } else {
                        currentFullBlock.end = x - 1;

                        String templatePlaceholder = template.substring(
                                currentBlock.start,
                                currentBlock.end
                        );
                        full = new StringBuilder(full).replace(
                                currentFullBlock.start,
                                currentFullBlock.end,
                                templatePlaceholder
                        ).toString();

                        break;
                    }
                }
            }
        }

        return full;
    }

}

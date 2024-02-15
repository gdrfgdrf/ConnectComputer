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

package cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Assignor;

import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.ArgumentAssignor;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.StringUtils;

import java.lang.reflect.Field;

/**
 * @author gdrfgdrf
 */
public class IntegerArgumentAssignor implements ArgumentAssignor<Integer> {
    @Override
    public boolean assign(Object obj, Field field, Object argument) throws IllegalAccessException {
        String str = argument.toString();
        if (!StringUtils.checkStringForInteger(str)) {
            return false;
        }

        Integer result = Integer.parseInt(str);
        field.set(obj, result);

        return true;
    }

    @Override
    public String getShownName() {
        return AppLocale.ARGUMENT_INTEGER_ASSIGNOR;
    }
}

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

package cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Exception;

import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.CustomException;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;

/**
 * @author gdrfgdrf
 */
public class ArgumentAssignorExecuteException extends CustomException {
    public ArgumentAssignorExecuteException() {
    }

    public ArgumentAssignorExecuteException(String message) {
        super(message);
    }

    public ArgumentAssignorExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentAssignorExecuteException(Throwable cause) {
        super(cause);
    }

    public ArgumentAssignorExecuteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getErrorMessage() {
        return AppLocale.CUSTOM_EXCEPTION_ARGUMENT_ASSIGNOR_EXECUTE;
    }
}

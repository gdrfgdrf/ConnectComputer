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

package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Exception;

import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.CustomException;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import com.google.protobuf.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author gdrfgdrf
 */
@AllArgsConstructor
public class NettyNotFoundPacketHandlerException extends CustomException {
    @NonNull
    @Getter
    private final Message msg;

    @Override
    public String getErrorMessage() {
        return AppLocale.CUSTOM_EXCEPTION_NETTY_NOT_FOUND_PACKET_HANDLER_ERROR;
    }
}

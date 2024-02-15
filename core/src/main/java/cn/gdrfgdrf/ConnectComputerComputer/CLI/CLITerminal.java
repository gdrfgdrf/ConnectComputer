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

package cn.gdrfgdrf.ConnectComputerComputer.CLI;

import cn.gdrfgdrf.ConnectComputerComputer.CLI.Exception.ApplicationClosedException;

import java.io.IOException;

/**
 * @author gdrfgdrf
 */
public interface CLITerminal {
    void setPrompt(String prompt);
    String readLine() throws Exception;
    String readLine(String prompt) throws Exception;
    void interruptReadLine() throws Exception;

    void write(byte[] bytes) throws Exception;
    void write(String s) throws Exception;
    void newLine() throws Exception;


}

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

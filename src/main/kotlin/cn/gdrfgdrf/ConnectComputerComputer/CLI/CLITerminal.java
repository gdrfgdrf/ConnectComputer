package cn.gdrfgdrf.ConnectComputerComputer.CLI;

import cn.gdrfgdrf.ConnectComputerComputer.CLI.Exception.ApplicationClosedException;

import java.io.IOException;

/**
 * @author gdrfgdrf
 */
public interface CLITerminal {
    void setPrompt(String prompt);
    String readLine() throws IOException, ApplicationClosedException;
    String readLine(String prompt) throws IOException, ApplicationClosedException;

    void write(byte[] bytes) throws IOException;
    void write(String s) throws IOException, ApplicationClosedException;
    void newLine() throws IOException, ApplicationClosedException;


}

package cn.gdrfgdrf.ConnectComputerComputer.CLI;

import cn.gdrfgdrf.ConnectComputerComputer.CLI.Exception.ApplicationClosedException;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.Platform;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * @author gdrfgdrf
 */
public class DefaultTerminal implements Terminal {
    private final Scanner scanner = new Scanner(System.in);
    private final OutputStream outputStream = System.out;
    private String prompt;

    private boolean closed = false;

    public DefaultTerminal() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            closed = true;
            try {
                newLine();
            } catch (Exception ignored) {
            }
        }));
    }

    @Override
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    private String readLineInternal(String prompt) throws IOException, ApplicationClosedException {
        if (closed) {
            throw new ApplicationClosedException();
        }
        if (!scanner.hasNext()) {
            throw new ApplicationClosedException();
        }
        if (prompt != null) {
            write(prompt);
        }
        return scanner.nextLine();
    }

    @Override
    public String readLine() throws IOException, ApplicationClosedException {
        return readLineInternal(prompt);
    }

    @Override
    public String readLine(String prompt) throws IOException, ApplicationClosedException {
        return readLineInternal(prompt);
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        outputStream.write(bytes);
    }

    @Override
    public void write(String s) throws IOException, ApplicationClosedException {
        if (closed) {
            throw new ApplicationClosedException();
        }
        write(s.getBytes());
    }

    @Override
    public void newLine() throws IOException, ApplicationClosedException {
        if (Platform.isWindows()) {
            write("\r\n");
            return;
        }
        write("\n");
    }
}

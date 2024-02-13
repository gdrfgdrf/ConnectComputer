package cn.gdrfgdrf.ConnectComputerComputer.CLI;

import cn.gdrfgdrf.ConnectComputerComputer.CLI.Exception.ApplicationClosedException;
import cn.gdrfgdrf.ConnectComputerComputer.Thread.ThreadPoolService;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.Platform;

import java.io.IOException;
import java.io.OutputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author gdrfgdrf
 */
public class DefaultCLITerminal implements CLITerminal {
    private final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

    private final Scanner scanner = new Scanner(System.in);
    private final OutputStream outputStream = System.out;
    private String prompt;

    private boolean closed = false;

    public DefaultCLITerminal() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            closed = true;
            try {
                newLine();
            } catch (Exception ignored) {
            }
        }));

        ThreadPoolService.newTask(new TerminalReader());
    }

    @Override
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public String readLine() throws Exception {
        return readLineInternal(prompt);
    }

    @Override
    public String readLine(String prompt) throws Exception {
        return readLineInternal(prompt);
    }

    @Override
    public void interruptReadLine() throws Exception {
        if (Platform.isWindows()) {
            queue.put("\r\n");
            return;
        }
        queue.put("\n");
    }

    private String readLineInternal(String prompt) throws Exception {
        if (closed) {
            throw new ApplicationClosedException();
        }
        if (prompt != null) {
            write(prompt);
        }
        return queue.take();
    }

    @Override
    public void write(byte[] bytes) throws Exception {
        outputStream.write(bytes);
    }

    @Override
    public void write(String s) throws Exception {
        if (closed) {
            throw new ApplicationClosedException();
        }
        write(s.getBytes());
    }

    @Override
    public void newLine() throws Exception {
        if (Platform.isWindows()) {
            write("\r\n");
            return;
        }
        write("\n");
    }

    private class TerminalReader implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    if (closed) {
                        interruptReadLine();
                        break;
                    }
                    String line = scanner.nextLine();

                    queue.put(line);
                } catch (NoSuchElementException | InterruptedException ignored) {
                    closed = true;
                    try {
                        interruptReadLine();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

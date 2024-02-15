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

package cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base;

import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import com.jediterm.terminal.TerminalStarter;
import com.jediterm.terminal.TtyConnector;
import com.jediterm.terminal.ui.JediTermWidget;
import com.jediterm.terminal.ui.TerminalWidgetListener;
import com.jediterm.terminal.ui.settings.DefaultSettingsProvider;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * @author gdrfgdrf
 */
@Slf4j
public abstract class Terminal {
    private final boolean windowVisible;
    private final List<TerminalCloseListener> terminalCloseListeners = new LinkedList<>();

    @Getter
    private JFrame frame;
    private JediTermWidget jediTermWidget;
    private TerminalStarter terminalStarter;
    protected boolean closeByOpposite = false;

    private final Object jediTermWidgetLock = new Object();

    protected Terminal(boolean windowVisible) {
        this.windowVisible = windowVisible;
    }

    public void addCloseListener(TerminalCloseListener terminalCloseListener) {
        terminalCloseListeners.add(terminalCloseListener);
    }

    public void removeCloseListener(TerminalCloseListener terminalCloseListener) {
        terminalCloseListeners.remove(terminalCloseListener);
    }

    public void start() {
        SwingUtilities.invokeLater(this::create);
    }

    public void close() {
        GlobalConfiguration.tempAesKey = null;
        GlobalConfiguration.tempOwnPrivateKey = null;
        GlobalConfiguration.tempOwnPublicKey = null;
        GlobalConfiguration.tempPublicKey = null;

        if (terminalStarter == null) {
            return;
        }
        listener.allSessionsClosed(jediTermWidget);
        terminalStarter.close();
        terminalStarter = null;

        log.info("Terminal is closed");

        if (!closeByOpposite) {
            terminalCloseListeners.forEach(TerminalCloseListener::onClose);
        } else {
            terminalCloseListeners.forEach(TerminalCloseListener::onCloseOpposite);
        }
    }

    public abstract void closeByOpposite();

    public void userInput(String str) {
        if (terminalStarter != null) {
            terminalStarter.sendString(str, true);
        }
    }

    protected abstract TtyConnector createTtyConnector();

    //Copy from com.jediterm.example.BasicTerminalShellExample#createTerminalWidget of JediTerm (change).
    private @NotNull JediTermWidget createTerminalWidget() {
        JediTermWidget widget = new JediTermWidget(140, 32, settingsProvider);
        widget.setTtyConnector(createTtyConnector());
        widget.start();
        jediTermWidget = widget;
        synchronized (jediTermWidgetLock) {
            jediTermWidgetLock.notifyAll();
        }
        terminalStarter = widget.getTerminalStarter();

        return widget;
    }

    //Copy from com.jediterm.example.BasicTerminalShellExample#createAndShowGUI of JediTerm (change).
    protected void create() {
        frame = new JFrame(getClass().getSimpleName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JediTermWidget widget = createTerminalWidget();
        widget.addListener(listener);
        frame.addWindowListener(windowAdapter);
        frame.setContentPane(widget);
        frame.pack();
        frame.setVisible(windowVisible);

        log.info("Terminal is started");
    }

    public JediTermWidget getJediTermWidget() {
        synchronized (jediTermWidgetLock) {
            if (jediTermWidget == null) {
                try {
                    jediTermWidgetLock.wait();
                } catch (InterruptedException ignored) {
                }
            }
            return jediTermWidget;
        }
    }

    private final TerminalWidgetListener listener = terminalWidget -> {
        if (jediTermWidget != null) {
            try {
                jediTermWidget.close();
            } catch (Exception ignored) {
            }
        }
        if (frame != null) {
            SwingUtilities.invokeLater(() -> {
                if (frame.isVisible()) {
                    frame.dispose();
                }
            });
        }
        GlobalConfiguration.terminal = null;
    };
    private final WindowAdapter windowAdapter = new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent e) {
            if (getFrame() != null) {
                getFrame().setVisible(false);
            }
            if (getJediTermWidget() != null) {
                if (!closeByOpposite) {
                    close();
                }
            }
        }
    };
    protected final DefaultSettingsProvider settingsProvider = new DefaultSettingsProvider() {
        @Override
        public Font getTerminalFont() {
            Locale locale = Locale.getDefault();
            if (locale == Locale.CHINA || locale == Locale.TRADITIONAL_CHINESE) {
                return new Font("新宋体", Font.PLAIN, (int) getTerminalFontSize());
            }

            return new Font("YaHeiConsola", Font.PLAIN, (int) getTerminalFontSize());
        }
    };

    public interface TerminalCloseListener {
        void onClose();
        void onCloseOpposite();
    }
}

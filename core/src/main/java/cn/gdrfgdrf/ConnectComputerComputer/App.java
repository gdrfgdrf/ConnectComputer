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

package cn.gdrfgdrf.ConnectComputerComputer;

import cn.gdrfgdrf.ConnectComputerComputer.Annotation.Keep;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Annotation.Argument;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.ArgumentAssignorExecutor;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Annotation.ArgumentValidated;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.ArgumentValidatorExecutor;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Validator.PortArgumentValidator;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Validator.UsernameArgumentValidator;
import cn.gdrfgdrf.ConnectComputerComputer.CLI.CLITerminal;
import cn.gdrfgdrf.ConnectComputerComputer.CLI.DefaultCLITerminal;
import cn.gdrfgdrf.ConnectComputerComputer.CLI.Exception.ApplicationClosedException;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.Account;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.ServerInfo;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Route.MenuRoute;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Menu;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.MenuNavigator;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.BeanManager;
import cn.gdrfgdrf.ConnectComputerComputer.Thread.ThreadPoolService;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.AppUtils;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.JdkLoggerFactory;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class App {
    public static final App INSTANCE = new App();

    @Keep
    @Argument
    private Boolean ssl;
    @Keep
    @Argument
    private String serverIp;
    @Keep
    @Argument
    @ArgumentValidated(validator = PortArgumentValidator.class)
    private Integer port;

    @Keep
    @Argument
    @ArgumentValidated(validator = UsernameArgumentValidator.class)
    private String username;
    @Keep
    @Argument
    private String password;
    @Keep
    @Argument
    private Boolean autoLogin;
    @Keep
    @Argument
    private Boolean controller;
    @Keep
    @Argument
    private Boolean silent = false;
    @Keep
    @Argument
    private Boolean debug = false;

    @Setter
    private Boolean unencryptedPassword = false;

    @Keep
    public static void main(String[] args) throws Exception {
        INSTANCE.run(args);
    }

    private void run(String[] args) throws Exception {
        if (debug) {
            GlobalConfiguration.DEBUG = true;
            log.info(AppLocale.DEBUG_MODE_ENABLED);
        } else {
            InternalLoggerFactory.setDefaultFactory(JdkLoggerFactory.INSTANCE);
        }

        BeanManager beanManager = BeanManager.getInstance();
        beanManager.init();

        Map<String, String> map = AppUtils.processArgs(args);
        ArgumentAssignorExecutor.INSTANCE.assign(this, map);
        ArgumentValidatorExecutor.INSTANCE.validate(this);

        if (silent) {
            log.info(AppLocale.SILENT_MODE_ENABLED);
        }

        DataStore dataStore = beanManager.getBean("DataStore");
        assignArgumentToServerInfo(dataStore.getServerInfo());
        assignArgumentToAccount(dataStore.getAccount());

        ServerInfo serverInfo = dataStore.getServerInfo();
        Account account = dataStore.getAccount();

        log.info("Loading terminal...");

        CLITerminal cliTerminal = new DefaultCLITerminal();
        cliTerminal.setPrompt(Constants.PROMPT);

        StepExecutor stepExecutor = new StepExecutor();
        stepExecutor.setDataStore(dataStore);
        stepExecutor.setCliTerminal(cliTerminal);
        stepExecutor.start();

        final Object lock = stepExecutor.getLock();
        synchronized (lock) {
            lock.wait();
        }

        ThreadPoolService.newTask(() -> {
            try {
                NettyClient.INSTANCE.setServerInfo(serverInfo);
                NettyClient.INSTANCE.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        if (account.isController()) {
            MenuNavigator.INSTANCE.popup(MenuRoute.MENU_ROUTE_COMPUTER_LIST);

            while (true) {
                try {
                    String line = cliTerminal.readLine(Constants.PROMPT);

                    Menu currentMenu = MenuNavigator.INSTANCE.getCurrentMenu();
                    currentMenu.userInput(line);
                } catch (ApplicationClosedException e) {
                    AppUtils.exitProgram();
                    break;
                } catch (Exception e) {

                }
            }
        }
    }

    private void assignArgumentToServerInfo(ServerInfo serverInfo) {
        if (ssl != null ||
                serverIp != null ||
                port != null) {
            serverInfo.setSslEnabled(null);
            serverInfo.setIp(null);
            serverInfo.setPort(null);

            log.info(AppLocale.INPUT_SERVER_INFO_ARGUMENT_PASS_THE_DATASTORE_SERVER_INFO_ARGUMENT);
        }

        if (ssl != null) {
            serverInfo.setSslEnabled(ssl);
        }
        if (serverIp != null) {
            serverInfo.setIp(serverIp);
        }
        if (port != null) {
            serverInfo.setPort(port);
        }
    }

    private void assignArgumentToAccount(Account account) {
        if (username != null ||
                password != null ||
                autoLogin != null ||
                controller != null) {
            account.setUsername(null);
            account.setPassword(null);
            account.setAutoLogin(null);
            account.setController(null);

            log.info(AppLocale.INPUT_ACCOUNT_ARGUMENT_PASS_THE_DATASTORE_ACCOUNT_ARGUMENT);
        }

        if (username != null) {
            account.setUsername(username);
        }
        if (password != null) {
            account.setPassword(password);
            unencryptedPassword = true;
        }
        if (autoLogin != null) {
            account.setAutoLogin(autoLogin);
        }
        if (controller != null) {
            account.setController(controller);
        }
    }

    public Boolean isSilent() {
        return silent;
    }

    public Boolean isUnencryptedPassword() {
        return unencryptedPassword;
    }
}

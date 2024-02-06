package cn.gdrfgdrf.ConnectComputerComputer;

import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Annotation.Argument;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.ArgumentAssignorExecutor;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Annotation.ArgumentValidated;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.ArgumentValidatorExecutor;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Validator.PortArgumentValidator;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Validator.UsernameArgumentValidator;
import cn.gdrfgdrf.ConnectComputerComputer.CLI.CLITerminal;
import cn.gdrfgdrf.ConnectComputerComputer.CLI.DefaultCLITerminal;
import cn.gdrfgdrf.ConnectComputerComputer.CLI.Exception.ApplicationClosedException;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Deserializer.InformationDeserializer;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Information;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.Account;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.RSA;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.ServerInfo;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Deserializer.RSADeserializer;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Deserializer.RSASerializer;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import cn.gdrfgdrf.ConnectComputerComputer.Global.MenuRoute;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Menu;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.MenuNavigator;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.BeanManager;
import cn.gdrfgdrf.ConnectComputerComputer.Thread.ThreadPoolService;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.AppUtils;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.StringUtils;
import com.alibaba.fastjson2.JSON;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.JdkLoggerFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class App {
    public static final App INSTANCE = new App();

    @Argument
    private Boolean ssl;
    @Argument
    private String serverIp;
    @Argument
    @ArgumentValidated(validator = PortArgumentValidator.class)
    private Integer port;

    @Argument
    @ArgumentValidated(validator = UsernameArgumentValidator.class)
    private String username;
    @Argument
    private String password;
    @Argument
    private Boolean autoLogin;
    @Argument
    private Boolean controller;

    @Argument
    private Boolean silent = false;

    public static void main(String[] args) throws Exception {
        INSTANCE.run(args);
    }

    private void run(String[] args) throws Exception {
        InternalLoggerFactory.setDefaultFactory(JdkLoggerFactory.INSTANCE);
        JSON.register(RSA.class, RSASerializer.INSTANCE);
        JSON.register(RSA.class, RSADeserializer.INSTANCE);
        JSON.register(Information.class, InformationDeserializer.INSTANCE);

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

        CLITerminal CLITerminal = new DefaultCLITerminal();
        CLITerminal.setPrompt(Constants.PROMPT);

        AppKotlin appKotlin = new AppKotlin(log, dataStore, CLITerminal);
        appKotlin.enterServerInfo();
        appKotlin.enterAccount(!StringUtils.isBlank(password));

        ThreadPoolService.newTask(() -> {
            try {
                NettyClient.INSTANCE.setServerInfo(serverInfo);
                NettyClient.INSTANCE.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        if (account.getController()) {
            MenuNavigator.INSTANCE.setMenuHttpNetworkRequest(appKotlin.getMenuHttpNetworkRequest());
            MenuNavigator.INSTANCE.popup(MenuRoute.MENU_ROUTE_COMPUTER_LIST);

            while (true) {
                try {
                    String line = CLITerminal.readLine(Constants.PROMPT);

                    Menu currentMenu = MenuNavigator.INSTANCE.getCurrentMenu();
                    currentMenu.userInput(line);
                } catch (ApplicationClosedException e) {
                    AppUtils.exitProgram();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
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
        }
        if (autoLogin != null) {
            account.setAutoLogin(autoLogin);
        }
        if (controller != null) {
            account.setController(controller);
        }
    }

    public Boolean getSilent() {
        return silent;
    }
}

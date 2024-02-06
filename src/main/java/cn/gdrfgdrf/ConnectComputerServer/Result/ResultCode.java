package cn.gdrfgdrf.ConnectComputerServer.Result;

/**
 * @author gdrfgdrf
 */
public interface ResultCode {
    int SUCCESS = 20000;
    int NOT_FOUND = 20001;
    int SYNTAX_ERROR = 20002;
    int NEED_LOGIN = 20003;
    int NEED_UPDATE = 20004;

    int ERROR = 20005;
    int ERROR_PARAMETER = 20006;
    int CONDITION_NOT_SATISFIED = 20007;

    int TIMEOUT = 20008;
    int GOODBYE = 20009;
}
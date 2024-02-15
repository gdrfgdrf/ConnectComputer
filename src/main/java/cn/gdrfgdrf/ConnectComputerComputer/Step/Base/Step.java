package cn.gdrfgdrf.ConnectComputerComputer.Step.Base;

import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;

/**
 * @author gdrfgdrf
 */
public interface Step {
    void start(DataStore dataStore) throws Exception;
    void userInput(String input) throws Exception;
}

package cn.gdrfgdrf.ConnectComputerComputer.TableConvertor.Translator.Impl;

import cn.gdrfgdrf.ConnectComputerComputer.TableConvertor.Translator.Translator;

/**
 * @author gdrfgdrf
 */
public class DefaultTranslator implements Translator<Object> {
    @Override
    public String translate(Object obj) {
        return obj.toString();
    }
}

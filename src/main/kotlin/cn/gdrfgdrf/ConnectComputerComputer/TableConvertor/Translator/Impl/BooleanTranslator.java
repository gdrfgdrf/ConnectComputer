package cn.gdrfgdrf.ConnectComputerComputer.TableConvertor.Translator.Impl;

import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.TableConvertor.Translator.Translator;

/**
 * @author gdrfgdrf
 */
public class BooleanTranslator implements Translator<Boolean> {
    @Override
    public String translate(Boolean obj) {
        return obj ? AppLocale.TRUE : AppLocale.FALSE;
    }
}

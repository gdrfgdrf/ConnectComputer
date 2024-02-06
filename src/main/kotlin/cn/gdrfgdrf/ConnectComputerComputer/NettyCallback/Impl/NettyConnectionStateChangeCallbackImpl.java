package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base.Terminal;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.RSA;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.NettyConnectionStateChangeCallback;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.BeanManager;
import cn.gdrfgdrf.Protobuf.Security.SecurityProto;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class NettyConnectionStateChangeCallbackImpl implements NettyConnectionStateChangeCallback {
    @Override
    public void onStartCountdown(long milliseconds) throws Exception {
        log.error(
                AppLocale.CANNOT_CONNECT_SERVER_WITH_COUNTDOWN,
                milliseconds / 1000
        );
    }

    @Override
    public void onConnecting() throws Exception {
        log.info(AppLocale.TRY_CONNECTING_SERVER);
    }

    @Override
    public void onConnectSuccess() throws Exception {
        log.info(AppLocale.CONNECTING_SERVER_SUCCESS);

        DataStore dataStore = BeanManager.getInstance().getBean("DataStore");
        RSA rsa = dataStore.getRsa();

        SecurityProto.ExchangeRsaPublicKeyPacket exchangeRsaPublicKeyPacket = SecurityProto.ExchangeRsaPublicKeyPacket.newBuilder()
                .setPublicKey(
                        Base64.encodeBase64String(
                                rsa.getNettyPublicKey().getEncoded()
                        )
                )
                .build();

        NettyClient.INSTANCE.getSender().send(exchangeRsaPublicKeyPacket);
    }

    @Override
    public void onConnectFailed() throws Exception {
        log.error(AppLocale.CONNECTING_SERVER_FAILED);

        if (GlobalConfiguration.terminal != null) {
            Terminal terminal = GlobalConfiguration.terminal;
            terminal.closeByOpposite();
        }
    }
}

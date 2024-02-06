package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Annotation.CallbackInfo;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Base.NettyCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.NettyConnectionStateChangeCallbackImpl;

/**
 * @author gdrfgdrf
 */
@CallbackInfo(
        targetClass = NettyClient.class,
        implementedClass = NettyConnectionStateChangeCallbackImpl.class
)
public interface NettyConnectionStateChangeCallback extends NettyCallback {
    void onStartCountdown(long milliseconds) throws Exception;
    void onConnecting() throws Exception;
    void onConnectSuccess() throws Exception;
    void onConnectFailed() throws Exception;
}

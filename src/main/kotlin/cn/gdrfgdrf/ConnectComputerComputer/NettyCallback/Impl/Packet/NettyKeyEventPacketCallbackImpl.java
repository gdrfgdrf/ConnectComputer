package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyKeyEventPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.Protobuf.Action.Controller.ControllerProto;
import com.jediterm.terminal.ui.JediTermWidget;
import org.apache.commons.lang3.CharUtils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

/**
 * @author gdrfgdrf
 */
public class NettyKeyEventPacketCallbackImpl implements NettyKeyEventPacketCallback {
    @Override
    public void onKeyEventPacket(PacketMessage packetMessage) throws Exception {
        if (GlobalConfiguration.terminal == null) {
            return;
        }

        ControllerProto.KeyEventPacket message = packetMessage.getMessage();
        JediTermWidget widget = GlobalConfiguration.terminal.getJediTermWidget();
        KeyEvent keyEvent = new KeyEvent(
                widget.getComponent(),
                message.getId(),
                message.getWhen(),
                message.getModifiers(),
                message.getKeyCode(),
                CharUtils.toChar(message.getKeyChar()),
                message.getKeyLocation()
        );

        widget.getTerminalPanel().processKeyEvent(keyEvent);
    }
}

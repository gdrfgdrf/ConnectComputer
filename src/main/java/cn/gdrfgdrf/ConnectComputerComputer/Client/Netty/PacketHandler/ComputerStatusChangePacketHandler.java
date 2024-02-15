package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Base.BasePacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Annotation.PacketHandler;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerProto;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Route.MenuRoute;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Menu;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.MenuNavigator;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyDefaultPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.NettyCallbackCollection;
import com.google.protobuf.Message;
import io.netty.channel.Channel;

/**
 * @author gdrfgdrf
 */
@PacketHandler(
        support = {
                ComputerProto.ComputerOnlinePacket.class,
                ComputerProto.ComputerOfflinePacket.class
        }
)
public class ComputerStatusChangePacketHandler implements BasePacketHandler {
    @Override
    public void handle(
            Channel channel,
            BaseProto.Packet packet,
            Message message
    ) throws Exception {
        NettyDefaultPacketCallback callback = NettyCallbackCollection.INSTANCE.getNettyCallback(Object.class);
        callback.onPacket(PacketMessage.createByMessage(packet, message));

        Menu menu = MenuNavigator.INSTANCE.getCurrentMenu();
        if (menu.getRoute().equals(MenuRoute.MENU_ROUTE_COMPUTER_LIST)) {
            MenuNavigator.INSTANCE.popupAgain();
        }
    }
}

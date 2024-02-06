package cn.gdrfgdrf.ConnectComputerServer.Netty.PacketHandler;

import cn.gdrfgdrf.ConnectComputerServer.Annotation.ClientVersion;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import cn.gdrfgdrf.ConnectComputerServer.Enum.VersionEnum;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.Condition;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.Handler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.PassNettyHandler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Base.PacketHandler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ComputerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ControllerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.NettyUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Enum.ConditionEnum;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Exception.IllegalParameterNettyException;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Handler.AuthenticateHandler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.Writer;
import cn.gdrfgdrf.Protobuf.Action.Account.AccountErrorProto;
import cn.gdrfgdrf.Protobuf.Action.Account.AccountProto;
import cn.gdrfgdrf.Protobuf.Action.Account.AccountSuccessProto;
import cn.gdrfgdrf.Protobuf.Action.Account.Enum.AccountEnumProto;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.ConnectComputerServer.Service.Database.IUserEntityService;
import cn.gdrfgdrf.ConnectComputerServer.Utils.TokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author gdrfgdrf
 */
@Slf4j
@Component
@Condition(
        condition = {
                ConditionEnum.AES_KEY_HAS_BEEN_GENERATED,
                ConditionEnum.LOGGED_IN
        },
        notSatisfiedResult = {
                ResultEnum.NETTY_AES_KEY_HAS_NOT_BEEN_GENERATED,
                ResultEnum.NETTY_ALREADY_LOGIN
        },
        need = {
                true,
                false
        }
)
@Handler(support = AccountProto.UserLoginPacket.class)
@PassNettyHandler(pass = {AuthenticateHandler.class})
@ClientVersion(version = VersionEnum.v1_0_0)
public class UserLoginPacketHandler implements PacketHandler<AccountProto.UserLoginPacket> {
    private final IUserEntityService userEntityService;

    @Autowired
    public UserLoginPacketHandler(IUserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @Override
    public void handle(Channel channel, BaseProto.Packet packet, AccountProto.UserLoginPacket message) throws Exception {
        String token = message.getToken();

        try {
            TokenUtils.verifyToken(token);
        } catch (ExpiredJwtException e) {
            log.warn(
                    "{} try to login, but user provides token was expired",
                    channel.remoteAddress()
            );
            throw new IllegalParameterNettyException(
                    ResultEnum.ERROR_TOKEN_EXPIRED,
                    AccountErrorProto.LoginFailedPacket.class,
                    packet.getRequestId(),
                    true
            );
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException e) {
            log.warn(
                    "{} try to login, but failed to verify the provided token",
                    channel.remoteAddress()
            );
            throw new IllegalParameterNettyException(
                    ResultEnum.ERROR_TOKEN_INVALID,
                    AccountErrorProto.LoginFailedPacket.class,
                    packet.getRequestId(),
                    true
            );
        } catch (Exception e) {
            log.error(
                    "An error occurred verifying {}'s token",
                    channel.remoteAddress()
            );
            throw new IllegalParameterNettyException(
                    ResultEnum.ERROR_TOKEN_INVALID,
                    AccountErrorProto.LoginFailedPacket.class,
                    packet.getRequestId(),
                    true
            );
        }

        Map<String, Object> map = TokenUtils.parseToken(token);
        if (map.get("id") == null) {
            log.warn(
                    "{} try to login, but not found id in token",
                    channel.remoteAddress()
            );
            throw new IllegalParameterNettyException(
                    ResultEnum.ERROR_TOKEN_INVALID,
                    AccountErrorProto.LoginFailedPacket.class,
                    packet.getRequestId(),
                    true
            );
        }

        Integer id = Integer.parseInt(map.get("id").toString());
        UserEntity user = userEntityService.getById(id);

        if (user == null) {
            log.warn(
                    "{} try to login, but user not found",
                    channel.remoteAddress()
            );
            throw new IllegalParameterNettyException(
                    ResultEnum.ERROR_NOT_FOUND_USER,
                    AccountErrorProto.LoginFailedPacket.class,
                    packet.getRequestId(),
                    true
            );
        }
        if (user.getTokenEntities().isEmpty()) {
            log.warn(
                    "{} try to login, but user in the database has no token",
                    channel.remoteAddress()
            );
            throw new IllegalParameterNettyException(
                    ResultEnum.ERROR_TOKEN_INVALID,
                    AccountErrorProto.LoginFailedPacket.class,
                    packet.getRequestId(),
                    true
            );
        }
        if (!user.containsToken(token)) {
            log.warn(
                    "{} try to login, but user token in the database does not match the provided token",
                    channel.remoteAddress()
            );
            throw new IllegalParameterNettyException(
                    ResultEnum.ERROR_TOKEN_INVALID,
                    AccountErrorProto.LoginFailedPacket.class,
                    packet.getRequestId(),
                    true
            );
        }

        try {
            NettyUser nettyUser = null;

            if (message.getLoginMode() == AccountEnumProto.LoginModeEnum.CONTROLLER) {
                nettyUser = new ControllerUser(
                        channel,
                        user.getId(),
                        token
                );
                NettyServer.getInstance().addControllerUser((ControllerUser) nettyUser);
            }
            if (message.getLoginMode() == AccountEnumProto.LoginModeEnum.COMPUTER) {
                nettyUser = new ComputerUser(
                        channel,
                        user.getId(),
                        token
                );
                NettyServer.getInstance().addComputerUser((ComputerUser) nettyUser);
            }
            assert nettyUser != null;

            NettyServer.getInstance().userLogin(channel, nettyUser);

            Writer.write(
                    channel,
                    AccountSuccessProto.LoginSuccessPacket.class,
                    packet.getRequestId(),
                    ResultEnum.NETTY_LOGIN_SUCCESS
            );

            log.info(
                    "User {} login in to the netty server",
                    user.getUsername()
            );
        } catch (Exception e) {
            log.warn(
                    "{} try to login, but user provides an incorrect RSA public key",
                    channel.remoteAddress()
            );
            throw new IllegalParameterNettyException(
                    ResultEnum.ERROR_RSA_KEY,
                    AccountErrorProto.LoginFailedPacket.class,
                    packet.getRequestId(),
                    true
            );
        }
    }
}
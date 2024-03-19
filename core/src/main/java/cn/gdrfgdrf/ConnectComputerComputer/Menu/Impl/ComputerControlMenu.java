/*
 * Copyright (C) 2024 Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cn.gdrfgdrf.ConnectComputerComputer.Menu.Impl;

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Computer.ComputerInformation;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.ConnectionStateChangePoster.ConnectionStateChangePoster;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.ConnectionStateChangePoster.ConnectionStateChangeReceiver;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.PacketPoster.PacketPoster;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.PacketPoster.PacketReceiver;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Utils.AnyPacketPacker;
import cn.gdrfgdrf.ConnectComputerComputer.Common.Key.AesKey;
import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base.Terminal;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.RemoteTerminal;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.AESUtils;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.RSAUtils;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerProto;
import cn.gdrfgdrf.Protobuf.Action.Computer.Security.ComputerSecurityProto;
import cn.gdrfgdrf.Protobuf.Action.Controller.ControllerProto;
import cn.gdrfgdrf.Protobuf.Action.Controller.ControllerSuccessProto;
import cn.gdrfgdrf.Protobuf.Action.Controller.Security.ControllerSecurityProto;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Menu;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.MenuNavigator;
import cn.gdrfgdrf.Protobuf.Common.AnyPacketProto;
import cn.gdrfgdrf.Protobuf.Common.EnumProto;
import cn.gdrfgdrf.Protobuf.Security.SecurityEnumProto;
import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author gdrfgdrf
 */
public class ComputerControlMenu extends Menu {
    private ComputerInformation computerInformation;

    @Override
    public String getTitle() {
        return AppLocale.MENU_TITLE_COMPUTER_CONTROL;
    }

    @Override
    public void popup(Object args) throws Exception {
        computerInformation = (ComputerInformation) args;
        if (!connectComputer()) {
            MenuNavigator.INSTANCE.dismiss();
            return;
        }
        KeyPair keyPair = RSAUtils.generateRSAKeyPair();
        if (!exchangeRsaKey(keyPair.getPrivate(), keyPair.getPublic())) {
            MenuNavigator.INSTANCE.dismiss();
            return;
        }
        if (!initAesKey(GlobalConfiguration.tempPublicKey)) {
            MenuNavigator.INSTANCE.dismiss();
            return;
        }

        registerReceiver();

        if (GlobalConfiguration.terminal != null) {
            GlobalConfiguration.terminal.closeByOpposite();
        }

        Terminal terminal = new RemoteTerminal();
        terminal.start();
        GlobalConfiguration.terminal = terminal;
        terminal.getJediTermWidget().getTerminalPanel().addCustomKeyListener(keyAdapter);
        terminal.addCloseListener(terminalCloseListener);

        terminal.getJediTermWidget().getTtyConnector().waitFor();
        GlobalConfiguration.tempAesKey = null;
        GlobalConfiguration.tempOwnPrivateKey = null;
        GlobalConfiguration.tempOwnPublicKey = null;
        GlobalConfiguration.tempPublicKey = null;
    }

    @Override
    public void dismiss() throws Exception {

    }

    @Override
    public void userInput(String input) throws Exception {

    }

    private void registerReceiver() {
        PacketPoster.INSTANCE.registerPacketReceiver(packetReceiver);
        ConnectionStateChangePoster.INSTANCE.registerConnectionStateChangeReceiver(connectionStateChangeReceiver);
    }

    private void unregisterReceiver() {
        PacketPoster.INSTANCE.unregisterPacketReceiver(packetReceiver);
        ConnectionStateChangePoster.INSTANCE.unregisterConnectionStateChangeReceiver(connectionStateChangeReceiver);
    }

    private boolean connectComputer() throws ExecutionException, InterruptedException, TimeoutException {
        ControllerProto.ConnectComputerPacket connectComputerPacket = ControllerProto.ConnectComputerPacket.newBuilder()
                .setId(computerInformation.getId())
                .build();

        Pair<BaseProto.Packet, Message> sendResult = NettyClient.INSTANCE.getSender()
                .sendSynchronously(connectComputerPacket);
        if (sendResult == null) {
            return false;
        }

        Message message = sendResult.getRight();
        return message instanceof ControllerSuccessProto.ConnectComputerSuccessPacket;
    }

    private boolean exchangeRsaKey(PrivateKey privateKey, PublicKey publicKey)
            throws ExecutionException,
            InterruptedException,
            TimeoutException,
            NoSuchAlgorithmException,
            InvalidKeySpecException,
            NoSuchPaddingException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException,
            InvalidKeyException {
        ControllerSecurityProto.ExchangeRsaPublicKeyPacket exchangeRsaPublicKeyPacket =
                ControllerSecurityProto.ExchangeRsaPublicKeyPacket.newBuilder()
                        .setPublicKey(Base64.encodeBase64String(publicKey.getEncoded()))
                        .build();

        Pair<BaseProto.Packet, Message> sendResult = NettyClient.INSTANCE.getSender()
                .sendSynchronously(exchangeRsaPublicKeyPacket);
        if (sendResult == null) {
            return false;
        }

        Message message = sendResult.getRight();
        if (!(message instanceof ComputerSecurityProto.ExchangeRsaPublicKeyPacket computerExchangeRsaPublicKeyPacket)) {
            return false;
        }
        String encryptedComputerPublicKey = computerExchangeRsaPublicKeyPacket.getPublicKey();
        encryptedComputerPublicKey = RSAUtils.privateDecrypt(encryptedComputerPublicKey, privateKey).toString();

        PublicKey computerPublicKey = RSAUtils.getPublicKey(encryptedComputerPublicKey);
        GlobalConfiguration.tempOwnPrivateKey = privateKey;
        GlobalConfiguration.tempOwnPublicKey = publicKey;
        GlobalConfiguration.tempPublicKey = computerPublicKey;

        return true;
    }

    private boolean initAesKey(PublicKey publicKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException,
            InvalidKeyException,
            ExecutionException,
            InterruptedException,
            TimeoutException {
        IvParameterSpec iv = AESUtils.generateIv();
        SecretKey key = AESUtils.generateKey();

        ControllerSecurityProto.InitAesKeyPacket initAesKeyPacket = ControllerSecurityProto.InitAesKeyPacket.newBuilder()
                .setIv(Base64.encodeBase64String(iv.getIV()))
                .setKey(Base64.encodeBase64String(key.getEncoded()))
                .build();

        AnyPacketProto.AnyPacket anyPacket = AnyPacketPacker.pack(initAesKeyPacket);
        BaseProto.Packet result = BaseProto.Packet.newBuilder()
                .setData(anyPacket)
                .build();

        byte[] bytes = result.toByteArray();
        bytes = RSAUtils.publicEncryptToByte(bytes, publicKey);
        ControllerSecurityProto.EncryptBlockPacket encryptBlockPacket = ControllerSecurityProto.EncryptBlockPacket.newBuilder()
                .setEncryptAlgorithm(SecurityEnumProto.EncryptAlgorithmEnum.RSA)
                .setEncrypted(ByteString.copyFrom(bytes))
                .build();

        Pair<BaseProto.Packet, Message> sendResult = NettyClient.INSTANCE.getSender()
                .sendSynchronously(encryptBlockPacket, future -> {
                    GlobalConfiguration.tempAesKey = new AesKey(iv, key);
                });
        if (sendResult == null) {
            return false;
        }

        Message message = sendResult.getRight();
        return message instanceof ComputerSecurityProto.AesKeyReceivedPacket;
    }

    private final Terminal.TerminalCloseListener terminalCloseListener = new Terminal.TerminalCloseListener() {
        @Override
        public void onClose() {
            unregisterReceiver();
            MenuNavigator.INSTANCE.dismiss();
        }

        @Override
        public void onCloseOpposite() {

        }
    };
    private final PacketReceiver packetReceiver = new PacketReceiver() {
        @Override
        public Class<? extends GeneratedMessage>[] type() {
            return new Class[]{
                    ComputerProto.ComputerDisconnectedPacket.class,
                    ComputerProto.TerminalClosedPacket.class
            };
        }

        @Override
        public void onReceived(BaseProto.Packet packet, Message message) {
            unregisterReceiver();
            MenuNavigator.INSTANCE.dismiss();
        }
    };
    private final ConnectionStateChangeReceiver connectionStateChangeReceiver = new ConnectionStateChangeReceiver() {
        @Override
        public void onConnected() {

        }

        @Override
        public void onDisconnected() {
            unregisterReceiver();
            MenuNavigator.INSTANCE.dismiss();
        }
    };
    private final KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            ControllerProto.KeyEventPacket keyEventPacket = ControllerProto.KeyEventPacket.newBuilder()
                    .setKeyEventType(EnumProto.KeyEventType.PRESSED)
                    .setId(e.getID())
                    .setWhen(e.getWhen())
                    .setModifiers(e.getModifiersEx())
                    .setKeyCode(e.getKeyCode())
                    .setKeyChar(String.valueOf(e.getKeyChar()))
                    .setKeyLocation(e.getKeyLocation())
                    .build();

            try {
                NettyClient.INSTANCE.getSender().send(keyEventPacket);
            } catch (Exception ignored) {

            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            ControllerProto.KeyEventPacket keyEventPacket = ControllerProto.KeyEventPacket.newBuilder()
                    .setKeyEventType(EnumProto.KeyEventType.TYPED)
                    .setId(e.getID())
                    .setWhen(e.getWhen())
                    .setModifiers(e.getModifiersEx())
                    .setKeyCode(e.getKeyCode())
                    .setKeyChar(String.valueOf(e.getKeyChar()))
                    .setKeyLocation(e.getKeyLocation())
                    .build();

            try {
                NettyClient.INSTANCE.getSender().send(keyEventPacket);
            } catch (Exception ignored) {

            }
        }
    };
}

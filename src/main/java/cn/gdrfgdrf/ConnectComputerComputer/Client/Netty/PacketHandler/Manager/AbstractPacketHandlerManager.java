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

package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Manager;

import java.util.HashMap;
import java.util.Map;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Base.BasePacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Annotation.PacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Annotation.PacketHandlerClass;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import com.google.protobuf.GeneratedMessageV3;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public abstract class AbstractPacketHandlerManager {
    private static final Map<Class<? extends BasePacketHandler>, BasePacketHandler> INSTANCE_MAP = new HashMap<>();

    protected void initHandler() {
        initAnnotationHandler();
    }

    private void initAnnotationHandler() {
        PacketHandlerClass packetHandlerClass = getClass().getAnnotation(PacketHandlerClass.class);
        if (packetHandlerClass == null) {
            return;
        }

        Class<?>[] classes = packetHandlerClass.clazz();
        for (Class<?> clazz : classes) {
            initHandlerClazz(clazz);
        }
    }

    private void initHandlerClazz(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(PacketHandler.class)) {
            return;
        }
        PacketHandler packetHandler = clazz.getAnnotation(PacketHandler.class);
        if (packetHandler == null) {
            return;
        }

        Class<? extends GeneratedMessageV3>[] supports = packetHandler.support();
        try {
            Class<? extends BasePacketHandler> handlerClazz = clazz.asSubclass(BasePacketHandler.class);
            BasePacketHandler instance;

            if (INSTANCE_MAP.containsKey(handlerClazz)) {
                instance = INSTANCE_MAP.get(handlerClazz);
            } else {
                instance = handlerClazz.getDeclaredConstructor().newInstance();
                INSTANCE_MAP.put(handlerClazz, instance);
            }

            for (Class<? extends GeneratedMessageV3> support : supports) {
                NettyClient.INSTANCE.getHandlerMap().put(support, instance);

                log.info(
                        "Register packet handler {} for type {}",
                        instance.getClass().getSimpleName(),
                        support.getSimpleName()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

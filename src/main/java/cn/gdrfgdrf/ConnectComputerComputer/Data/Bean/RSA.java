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

package cn.gdrfgdrf.ConnectComputerComputer.Data.Bean;

import cn.gdrfgdrf.ConnectComputerComputer.Data.DataBean;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.RSAUtils;
import lombok.*;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author gdrfgdrf
 */
@Getter
@ToString
public class RSA implements DataBean {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    private PrivateKey nettyPrivateKey;
    private PublicKey nettyPublicKey;

    @Override
    public void init(DataStore dataStore) throws Exception {
        if (privateKey == null ||
                publicKey == null ||
                nettyPrivateKey == null ||
                nettyPublicKey == null) {
            //Generate http key
            KeyPair httpKeyPair = RSAUtils.generateRSAKeyPair();
            PrivateKey httpPrivateKey = httpKeyPair.getPrivate();
            PublicKey httpPublicKey = httpKeyPair.getPublic();

            setPrivateKey(httpPrivateKey);
            setPublicKey(httpPublicKey);

            //Generate netty key
            KeyPair nettyKeyPair = RSAUtils.generateRSAKeyPair();
            PrivateKey nettyPrivateKey = nettyKeyPair.getPrivate();
            PublicKey nettyPublicKey = nettyKeyPair.getPublic();

            setNettyPrivateKey(nettyPrivateKey);
            setNettyPublicKey(nettyPublicKey);

            dataStore.saveDataBean(this);
        }
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void setNettyPrivateKey(PrivateKey nettyPrivateKey) {
        this.nettyPrivateKey = nettyPrivateKey;
    }

    public void setNettyPublicKey(PublicKey nettyPublicKey) {
        this.nettyPublicKey = nettyPublicKey;
    }
}

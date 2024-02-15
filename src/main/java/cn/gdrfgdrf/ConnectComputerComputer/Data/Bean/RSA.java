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

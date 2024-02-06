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
//        jsonPrivateKey = Base64.encodeBase64String(privateKey.getEncoded());
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
//        jsonPublicKey = Base64.encodeBase64String(publicKey.getEncoded());
    }

    public void setNettyPrivateKey(PrivateKey nettyPrivateKey) {
        this.nettyPrivateKey = nettyPrivateKey;
//        jsonNettyPrivateKey = Base64.encodeBase64String(nettyPrivateKey.getEncoded());
    }

    public void setNettyPublicKey(PublicKey nettyPublicKey) {
        this.nettyPublicKey = nettyPublicKey;
//        jsonNettyPublicKey = Base64.encodeBase64String(nettyPublicKey.getEncoded());
    }

//    public void setJsonPrivateKey(String jsonPrivateKey) throws Exception {
//        if (jsonPrivateKey == null) {
//            return;
//        }
//        this.jsonPrivateKey = jsonPrivateKey;
//        privateKey = RSAUtils.getPrivateKey(jsonPrivateKey);
//    }
//
//    public void setJsonPublicKey(String jsonPublicKey) throws Exception {
//        if (jsonPublicKey == null) {
//            return;
//        }
//        this.jsonPublicKey = jsonPublicKey;
//        publicKey = RSAUtils.getPublicKey(jsonPublicKey);
//    }
//
//    public void setJsonNettyPrivateKey(String jsonNettyPrivateKey) throws Exception {
//        if (jsonNettyPrivateKey == null) {
//            return;
//        }
//        this.jsonNettyPrivateKey = jsonNettyPrivateKey;
//        nettyPrivateKey = RSAUtils.getPrivateKey(jsonNettyPrivateKey);
//    }
//
//    public void setJsonNettyPublicKey(String jsonNettyPublicKey) throws Exception {
//        if (jsonNettyPublicKey == null) {
//            return;
//        }
//        this.jsonNettyPublicKey = jsonNettyPublicKey;
//        nettyPublicKey = RSAUtils.getPublicKey(jsonNettyPublicKey);
//    }
}

package cn.gdrfgdrf.ConnectComputerComputer.Data.Deserializer;

import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.RSA;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.RSAUtils;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.reader.ObjectReader;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author gdrfgdrf
 */
public class RSADeserializer implements ObjectReader<RSA> {
    public static final RSADeserializer INSTANCE = new RSADeserializer();

    @Override
    public RSA readObject(JSONReader jsonReader, long features) {
        if (jsonReader.nextIfNull()) {
            return null;
        }

        String str = jsonReader.readString();
        RSA rsa = new RSA();

        try {
            JSONObject jsonObject = JSONObject.parseObject(str);

            String privateKey = jsonObject.getString("privateKey");
            rsa.setPrivateKey(convertStringToPrivateKey(privateKey));

            String publicKey = jsonObject.getString("publicKey");
            rsa.setPublicKey(convertStringToPublicKey(publicKey));

            String nettyPrivateKey = jsonObject.getString("nettyPrivateKey");
            rsa.setNettyPrivateKey(convertStringToPrivateKey(nettyPrivateKey));

            String nettyPublicKey = jsonObject.getString("nettyPublicKey");
            rsa.setNettyPublicKey(convertStringToPublicKey(nettyPublicKey));

            return rsa;
        } catch (Exception ignored) {
            return null;
        }
    }

    private PrivateKey convertStringToPrivateKey(String str) {
        try {
            return RSAUtils.getPrivateKey(str);
        } catch (Exception e) {
            return null;
        }
    }

    private PublicKey convertStringToPublicKey(String str) {
        try {
            return RSAUtils.getPublicKey(str);
        } catch (Exception e) {
            return null;
        }
    }
}

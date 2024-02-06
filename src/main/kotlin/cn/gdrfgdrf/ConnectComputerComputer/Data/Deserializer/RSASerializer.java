package cn.gdrfgdrf.ConnectComputerComputer.Data.Deserializer;

import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.RSA;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.writer.ObjectWriter;
import org.apache.tomcat.util.codec.binary.Base64;

import java.lang.reflect.Type;
import java.security.Key;

/**
 * @author gdrfgdrf
 */
public class RSASerializer implements ObjectWriter<RSA> {
    public static final RSASerializer INSTANCE = new RSASerializer();

    @Override
    public void write(JSONWriter jsonWriter, Object object, Object fieldName, Type fieldType, long features) {
        if (object == null) {
            jsonWriter.writeNull();
            return;
        }

        RSA rsa = (RSA) object;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("privateKey", convertKeyToString(rsa.getPrivateKey()));
        jsonObject.put("publicKey", convertKeyToString(rsa.getPublicKey()));
        jsonObject.put("nettyPrivateKey", convertKeyToString(rsa.getNettyPrivateKey()));
        jsonObject.put("nettyPublicKey", convertKeyToString(rsa.getNettyPublicKey()));

        jsonWriter.writeAny(jsonObject);
    }

    private String convertKeyToString(Key key) {
        if (key == null) {
            return null;
        }
        return Base64.encodeBase64String(key.getEncoded());
    }
}

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

package cn.gdrfgdrf.ConnectComputerComputer.Data.Deserializer;

import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.RSA;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.Jackson.SuperJsonNode;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.JacksonUtils;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.RSAUtils;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.StringUtils;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author gdrfgdrf
 */
public class RSADeserializer extends JsonDeserializer<RSA> {
    public static final RSADeserializer INSTANCE = new RSADeserializer();

    @Override
    public RSA deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectCodec codec = jsonParser.getCodec();
        TreeNode treeNode = codec.readTree(jsonParser);
        String json = treeNode.toString();
        if (StringUtils.isBlank(json)) {
            return null;
        }

        RSA rsa = new RSA();
        try {
            SuperJsonNode jsonNode = JacksonUtils.readStringTree(json);

            String privateKey = jsonNode.getString("privateKey");
            rsa.setPrivateKey(convertStringToPrivateKey(privateKey));

            String publicKey = jsonNode.getString("publicKey");
            rsa.setPublicKey(convertStringToPublicKey(publicKey));

            String nettyPrivateKey = jsonNode.getString("nettyPrivateKey");
            rsa.setNettyPrivateKey(convertStringToPrivateKey(nettyPrivateKey));

            String nettyPublicKey = jsonNode.getString("nettyPublicKey");
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

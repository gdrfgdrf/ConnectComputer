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

package cn.gdrfgdrf.ConnectComputerComputer.Data.Serializer;

import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.RSA;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.JacksonUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.IOException;
import java.security.Key;

/**
 * @author gdrfgdrf
 */
public class RSASerializer extends JsonSerializer<RSA> {
    public static final RSASerializer INSTANCE = new RSASerializer();

    @Override
    public void serialize(RSA rsa, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (rsa == null) {
            jsonGenerator.writeNull();
            return;
        }

        ObjectNode objectNode = JacksonUtils.newTree();
        objectNode.put("privateKey", convertKeyToString(rsa.getPrivateKey()));
        objectNode.put("publicKey", convertKeyToString(rsa.getPublicKey()));
        objectNode.put("nettyPrivateKey", convertKeyToString(rsa.getNettyPrivateKey()));
        objectNode.put("nettyPublicKey", convertKeyToString(rsa.getNettyPublicKey()));

        jsonGenerator.writeObject(objectNode);
    }

    private String convertKeyToString(Key key) {
        if (key == null) {
            return null;
        }
        return Base64.encodeBase64String(key.getEncoded());
    }
}

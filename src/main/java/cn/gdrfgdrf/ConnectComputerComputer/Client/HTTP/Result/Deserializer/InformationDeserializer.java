package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Deserializer;

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Information;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.InformationCollection;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.JacksonUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @author gdrfgdrf
 */
public class InformationDeserializer extends JsonDeserializer<Information> {
    public static final InformationDeserializer INSTANCE = new InformationDeserializer();

    @Override
    public Information deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = jsonParser.getCodec();
        TreeNode treeNode = codec.readTree(jsonParser);
        String json = treeNode.toString();

        String className = codec.treeToValue(treeNode.get("className"), String.class);
        if (className == null) {
            return null;
        }
        Class<? extends Information> clazz = InformationCollection.MAP.get(className);

        return JacksonUtils.readString(json, clazz);
    }
}

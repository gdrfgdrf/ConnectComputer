package cn.gdrfgdrf.ConnectComputerComputer.Utils;

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Deserializer.InformationDeserializer;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Information;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.RSA;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Deserializer.RSADeserializer;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Serializer.RSASerializer;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.Jackson.SuperJsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gdrfgdrf
 */
@SuppressWarnings("unchecked")
public class JacksonUtils {
    private static final ObjectMapper MAPPER_INSTANCE = new ObjectMapper();

    static {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(RSA.class, RSASerializer.INSTANCE);
        simpleModule.addDeserializer(RSA.class, RSADeserializer.INSTANCE);
        simpleModule.addDeserializer(Information.class, InformationDeserializer.INSTANCE);

        MAPPER_INSTANCE.registerModule(simpleModule);
    }

    private JacksonUtils() {}

    public static <T> T readString(String jsonString, Class<?> type) throws JsonProcessingException {
        return (T) MAPPER_INSTANCE.readValue(jsonString, type);
    }

    public static <T> T readFile(File file, Class<?> type) throws IOException {
        return (T) MAPPER_INSTANCE.readValue(file, type);
    }

    public static <T> T readInputStream(InputStream inputStream, Class<?> type) throws IOException {
        return (T) MAPPER_INSTANCE.readValue(inputStream, type);
    }

    public static <T> T readBytes(byte[] bytes, Class<?> type) throws IOException {
        return (T) MAPPER_INSTANCE.readValue(bytes, type);
    }

    public static ObjectNode newTree() throws JsonProcessingException {
        return (ObjectNode) MAPPER_INSTANCE.readTree("{}");
    }

    public static SuperJsonNode readStringTree(String jsonString) throws JsonProcessingException {
        return new SuperJsonNode(MAPPER_INSTANCE.readTree(jsonString));
    }

    public static SuperJsonNode readFileTree(File file) throws IOException {
        return new SuperJsonNode(MAPPER_INSTANCE.readTree(file));
    }

    public static SuperJsonNode readInputStreamTree(InputStream inputStream) throws IOException {
        return new SuperJsonNode(MAPPER_INSTANCE.readTree(inputStream));
    }

    public static SuperJsonNode readBytes(byte[] bytes) throws IOException {
        return new SuperJsonNode(MAPPER_INSTANCE.readTree(bytes));
    }

    public static <E> List<E> toList(String jsonString, Class<E> E) throws JsonProcessingException {
        List<E> result = new ArrayList<>();
        SuperJsonNode jsonNode = JacksonUtils.readStringTree(jsonString);
        if (jsonNode.getJsonNode().isArray()) {
            for (int i = 0; i < jsonNode.size(); i++) {
                E e = JacksonUtils.readString(jsonNode.getString(i), E);
                result.add(e);
            }
        }

        return result;
    }

    public static String writeJsonString(Object obj) throws JsonProcessingException {
        return MAPPER_INSTANCE.writeValueAsString(obj);
    }


}

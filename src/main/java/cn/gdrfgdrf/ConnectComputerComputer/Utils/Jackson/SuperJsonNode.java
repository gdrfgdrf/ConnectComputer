package cn.gdrfgdrf.ConnectComputerComputer.Utils.Jackson;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Iterator;

/**
 * @author gdrfgdrf
 */
public class SuperJsonNode {
    @Getter
    private final JsonNode jsonNode;

    public SuperJsonNode(JsonNode jsonNode) {
        this.jsonNode = jsonNode;
    }

    public String getString(int i) {
        return jsonNode.get(i).asText();
    }

    public String getString(String key) {
        return jsonNode.get(key).asText();
    }

    public String getStringOrNull(int i) {
        if (jsonNode.has(i)) {
            return jsonNode.get(i).asText();
        }
        return null;
    }

    public String getStringOrNull(String key) {
        if (jsonNode.has(key)) {
            return jsonNode.get(key).asText();
        }
        return null;
    }

    public int size() {
        return jsonNode.size();
    }

    public Iterator<String> keySet() {
        return jsonNode.fieldNames();
    }
}

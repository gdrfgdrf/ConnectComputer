package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Deserializer;


import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Information;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.InformationCollection;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.reader.ObjectReader;

/**
 * @author gdrfgdrf
 */
public class InformationDeserializer implements ObjectReader<Information> {
    public static final InformationDeserializer INSTANCE = new InformationDeserializer();

    @Override
    public Information readObject(JSONReader jsonReader, long features) {
        if (jsonReader.nextIfNull()) {
            return null;
        }

        String str = jsonReader.readString();

        try {
            JSONObject jsonObject = JSONObject.parseObject(str);
            String className = jsonObject.get("className").toString();

            Class<? extends Information> clazz = InformationCollection.MAP.get(className);
            return JSON.parseObject(str, clazz);
        } catch (Exception e) {
            return null;
        }
    }
}

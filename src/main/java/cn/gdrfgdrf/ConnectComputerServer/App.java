package cn.gdrfgdrf.ConnectComputerServer;

import cn.gdrfgdrf.ConnectComputerServer.Bean.Information.Information;
import cn.gdrfgdrf.ConnectComputerServer.Bean.Information.InformationCollection;
import cn.gdrfgdrf.ConnectComputerServer.Global.Global;
import cn.gdrfgdrf.ConnectComputerServer.Enum.RSAKeyEnum;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.Handler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Base.PacketHandler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import cn.gdrfgdrf.ConnectComputerServer.Result.Deserializer.InformationDeserializer;
import cn.gdrfgdrf.ConnectComputerServer.Result.Deserializer.MessageEnumDeserializer;
import cn.gdrfgdrf.ConnectComputerServer.Result.LanguageLoader;
import cn.gdrfgdrf.ConnectComputerServer.Result.MessageEnum;
import cn.gdrfgdrf.ConnectComputerServer.Result.Serializer.MessageEnumSerializer;
import cn.gdrfgdrf.ConnectComputerServer.Utils.FileUtils;
import cn.gdrfgdrf.ConnectComputerServer.Utils.RSAUtils;
import cn.gdrfgdrf.ConnectComputerServer.Utils.TokenUtils;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Base.BaseValidator;
import com.alibaba.fastjson2.JSON;
import org.apache.tomcat.util.codec.binary.Base64;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.Assert;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author gdrfgdrf
 */
@SuppressWarnings("unchecked")
@EnableAsync
@EnableScheduling
@MapperScan(basePackages = "cn.gdrfgdrf.ConnectComputerServer.Mapper")
@SpringBootApplication(exclude = {JacksonAutoConfiguration.class})
public class App {
    public static void main(String[] args) throws Exception {
        init();

        JSON.register(MessageEnum.class, MessageEnumSerializer.INSTANCE);
        JSON.register(MessageEnum.class, MessageEnumDeserializer.INSTANCE);
        JSON.register(Information.class, InformationDeserializer.INSTANCE);

        initInformation();

        ApplicationContext context = SpringApplication.run(App.class, args);

        initHttpValidator();
        initNettyHandler(context);

        NettyServer.getInstance().run();
    }

    private static void initInformation() throws Exception {
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources(
                "classpath*:cn/gdrfgdrf/ConnectComputerServer/Bean/Information/**/**.class"
        );

        for (Resource resource : resources) {
            try {
                Class<?> clazz = getClassFromResource(resource);
                if (clazz == Information.class) {
                    continue;
                }
                if (clazz.asSubclass(Information.class) == null) {
                    continue;
                }

                InformationCollection.getMAP().put(clazz.getSimpleName(), (Class<? extends Information>) clazz);
            } catch (ClassCastException ignored) {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void initHttpValidator() throws Exception {
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources(
                "classpath*:cn/gdrfgdrf/ConnectComputerServer/Validation/Annotation/*.class"
        );

        for (Resource resource : resources) {
            try {
                Class<? extends Annotation> clazz = getClassFromResource(resource).asSubclass(Annotation.class);

                if (!clazz.isMemberClass()) {
                    Method method = clazz.getMethod("overrideMessageEnum");
                    BaseValidator.getCLASS_METHOD_MAP().put(clazz, method);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void initNettyHandler(ApplicationContext context) throws Exception {
        String[] array = context.getBeanNamesForType(PacketHandler.class);

        for (String name : array) {
            PacketHandler instance = (PacketHandler) context.getBean(name);
            Class<? extends PacketHandler> clazz = instance.getClass();

            Handler handler = AnnotatedElementUtils.findMergedAnnotation(clazz, Handler.class);
            assert handler != null;

            NettyServer.getInstance().HANDLER.put(handler.support(), instance);
        }
    }

    private static Class<?> getClassFromResource(Resource resource) throws Exception {
        String className = new SimpleMetadataReaderFactory()
                .getMetadataReader(resource)
                .getClassMetadata()
                .getClassName();
        return Class.forName(className);
    }

    private static void init() throws Exception {
        File application = new File("application.yml");
        FileUtils.saveResource("application.yml", application);

        Yaml yaml = new Yaml();
        Map<String, Object> yamlMap = yaml.load(FileUtils.getReader(application));

        if (yamlMap.get("avatarFilePath") != null
                && "avatar".equals(yamlMap.get("avatarFilePath").toString())
                && yamlMap.get("defaultAvatar") != null
                && "avatar/default_avatar.jpg".equals(yamlMap.get("defaultAvatar").toString())) {
            File avatarDir = new File("avatar");
            if (!avatarDir.exists()) {
                avatarDir.mkdirs();
            }

            File defaultAvatarFile = new File("avatar/default_avatar.jpg");
            FileUtils.saveResource("default_avatar.jpg", defaultAvatarFile);

            Object defaultAvatar = yamlMap.get("defaultAvatar");
            Assert.notNull(defaultAvatar, "application.yml 中 defaultAvatar 不能为空");

            String defaultAvatarStr = defaultAvatar.toString();
            File file = new File(defaultAvatarStr);

            if (file.exists()) {
                Global.DEFAULT_AVATAR = file;
            } else {
                throw new FileNotFoundException("application.yml 中 defaultAvatar 设置的文件找不到");
            }

            Global.DEFAULT_AVATAR_SHA256 = FileUtils.getSha256(Global.DEFAULT_AVATAR);

            if (yamlMap.get("private") == null || yamlMap.get("public") == null) {
                KeyPair keyPair = RSAUtils.generateRsaKeyPair();
                PrivateKey privateKey = keyPair.getPrivate();
                PublicKey publicKey = keyPair.getPublic();

                RSAKeyEnum.HTTP_KEY.setPrivateKey(privateKey);
                RSAKeyEnum.HTTP_KEY.setPublicKey(publicKey);

                yamlMap.put("private", Base64.encodeBase64String(privateKey.getEncoded()));
                yamlMap.put("public", Base64.encodeBase64String(publicKey.getEncoded()));

                Writer writer = FileUtils.getWriter(application);
                writer.write(yaml.dumpAsMap(yamlMap));
                writer.close();
            } else {
                boolean keyChange = false;

                String privateKeyStr = yamlMap.get("private").toString();
                String publicKeyStr = yamlMap.get("public").toString();

                while (!RSAUtils.validateKey(privateKeyStr, publicKeyStr)) {
                    keyChange = true;

                    KeyPair keyPair = RSAUtils.generateRsaKeyPair();
                    privateKeyStr = Base64.encodeBase64String(keyPair.getPrivate().getEncoded());
                    publicKeyStr = Base64.encodeBase64String(keyPair.getPublic().getEncoded());

                    yamlMap.put("private", privateKeyStr);
                    yamlMap.put("public", publicKeyStr);
                }

                if (keyChange) {
                    Writer writer = FileUtils.getWriter(application);
                    writer.write(yaml.dumpAsMap(yamlMap));
                    writer.close();
                }

                RSAKeyEnum.HTTP_KEY.setPrivateKey(RSAUtils.getPrivateKey(privateKeyStr));
                RSAKeyEnum.HTTP_KEY.setPublicKey(RSAUtils.getPublicKey(publicKeyStr));
            }

            if (yamlMap.get("privateToken") == null || yamlMap.get("publicToken") == null) {
                PrivateKey privateKey;
                PublicKey publicKey;

                do {
                    KeyPair keyPair = RSAUtils.generateRsaKeyPair();
                    privateKey = keyPair.getPrivate();
                    publicKey = keyPair.getPublic();
                } while (Base64.encodeBase64String(privateKey.getEncoded()).equals(Base64.encodeBase64String(RSAKeyEnum.HTTP_KEY.getPrivateKey().getEncoded())) ||
                        Base64.encodeBase64String(publicKey.getEncoded()).equals(Base64.encodeBase64String(RSAKeyEnum.HTTP_KEY.getPublicKey().getEncoded())));

                RSAKeyEnum.TOKEN_KEY.setPrivateKey(privateKey);
                RSAKeyEnum.TOKEN_KEY.setPublicKey(publicKey);

                yamlMap.put("privateToken", Base64.encodeBase64String(privateKey.getEncoded()));
                yamlMap.put("publicToken", Base64.encodeBase64String(publicKey.getEncoded()));

                Writer writer = FileUtils.getWriter(application);
                writer.write(yaml.dumpAsMap(yamlMap));
                writer.close();
            } else {
                boolean keyChange = false;

                String privateKeyStr = yamlMap.get("privateToken").toString();
                String publicKeyStr = yamlMap.get("publicToken").toString();

                PrivateKey privateKey;
                PublicKey publicKey;

                while (true) {
                    if (RSAUtils.validateKey(privateKeyStr, publicKeyStr)) {
                        privateKey = RSAUtils.getPrivateKey(privateKeyStr);
                        publicKey = RSAUtils.getPublicKey(publicKeyStr);

                        break;
                    }

                    keyChange = true;

                    KeyPair keyPair = RSAUtils.generateRsaKeyPair();
                    privateKeyStr = Base64.encodeBase64String(keyPair.getPrivate().getEncoded());
                    publicKeyStr = Base64.encodeBase64String(keyPair.getPublic().getEncoded());

                    yamlMap.put("privateToken", privateKeyStr);
                    yamlMap.put("publicToken", publicKeyStr);
                }

                while (Base64.encodeBase64String(privateKey.getEncoded()).equals(Base64.encodeBase64String(RSAKeyEnum.HTTP_KEY.getPrivateKey().getEncoded())) ||
                        Base64.encodeBase64String(publicKey.getEncoded()).equals(Base64.encodeBase64String(RSAKeyEnum.HTTP_KEY.getPublicKey().getEncoded()))) {

                    keyChange = true;
                    KeyPair keyPair = RSAUtils.generateRsaKeyPair();
                    privateKey = keyPair.getPrivate();
                    publicKey = keyPair.getPublic();

                    yamlMap.put("privateToken", Base64.encodeBase64String(privateKey.getEncoded()));
                    yamlMap.put("publicToken", Base64.encodeBase64String(publicKey.getEncoded()));
                }

                if (keyChange) {
                    Writer writer = FileUtils.getWriter(application);
                    writer.write(yaml.dumpAsMap(yamlMap));
                    writer.close();
                }

                RSAKeyEnum.TOKEN_KEY.setPrivateKey(privateKey);
                RSAKeyEnum.TOKEN_KEY.setPublicKey(publicKey);
            }

            if (yamlMap.get("privateNetty") == null || yamlMap.get("publicNetty") == null) {
                String privateKeyStr;
                String publicKeyStr;

                PrivateKey privateKey;
                PublicKey publicKey;

                do {
                    KeyPair keyPair = RSAUtils.generateRsaKeyPair();
                    privateKey = keyPair.getPrivate();
                    publicKey = keyPair.getPublic();

                    privateKeyStr = Base64.encodeBase64String(privateKey.getEncoded());
                    publicKeyStr = Base64.encodeBase64String(publicKey.getEncoded());
                } while (privateKeyStr.equals(Base64.encodeBase64String(RSAKeyEnum.HTTP_KEY.getPrivateKey().getEncoded())) ||
                        publicKeyStr.equals(Base64.encodeBase64String(RSAKeyEnum.HTTP_KEY.getPublicKey().getEncoded())) ||
                        privateKeyStr.equals(Base64.encodeBase64String(RSAKeyEnum.TOKEN_KEY.getPrivateKey().getEncoded())) ||
                        publicKeyStr.equals(Base64.encodeBase64String(RSAKeyEnum.TOKEN_KEY.getPublicKey().getEncoded())));

                RSAKeyEnum.NETTY_KEY.setPrivateKey(privateKey);
                RSAKeyEnum.NETTY_KEY.setPublicKey(publicKey);

                yamlMap.put("privateNetty", privateKeyStr);
                yamlMap.put("publicNetty", publicKeyStr);

                Writer writer = FileUtils.getWriter(application);
                writer.write(yaml.dumpAsMap(yamlMap));
                writer.close();
            } else {
                boolean keyChange = false;

                String privateKeyStr = yamlMap.get("privateNetty").toString();
                String publicKeyStr = yamlMap.get("publicNetty").toString();

                PrivateKey privateKey;
                PublicKey publicKey;

                while (true) {
                    if (RSAUtils.validateKey(privateKeyStr, publicKeyStr)) {
                        privateKey = RSAUtils.getPrivateKey(privateKeyStr);
                        publicKey = RSAUtils.getPublicKey(publicKeyStr);

                        break;
                    }

                    keyChange = true;

                    KeyPair keyPair = RSAUtils.generateRsaKeyPair();
                    privateKeyStr = Base64.encodeBase64String(keyPair.getPrivate().getEncoded());
                    publicKeyStr = Base64.encodeBase64String(keyPair.getPublic().getEncoded());

                    yamlMap.put("privateNetty", privateKeyStr);
                    yamlMap.put("publicNetty", publicKeyStr);
                }

                while (privateKeyStr.equals(Base64.encodeBase64String(RSAKeyEnum.HTTP_KEY.getPrivateKey().getEncoded())) ||
                        publicKeyStr.equals(Base64.encodeBase64String(RSAKeyEnum.HTTP_KEY.getPublicKey().getEncoded())) ||
                        privateKeyStr.equals(Base64.encodeBase64String(RSAKeyEnum.TOKEN_KEY.getPrivateKey().getEncoded())) ||
                        publicKeyStr.equals(Base64.encodeBase64String(RSAKeyEnum.TOKEN_KEY.getPublicKey().getEncoded()))) {

                    keyChange = true;
                    KeyPair keyPair = RSAUtils.generateRsaKeyPair();
                    privateKey = keyPair.getPrivate();
                    publicKey = keyPair.getPublic();

                    privateKeyStr = Base64.encodeBase64String(privateKey.getEncoded());
                    publicKeyStr = Base64.encodeBase64String(publicKey.getEncoded());

                    yamlMap.put("privateNetty", Base64.encodeBase64String(privateKey.getEncoded()));
                    yamlMap.put("publicNetty", Base64.encodeBase64String(publicKey.getEncoded()));
                }

                if (keyChange) {
                    Writer writer = FileUtils.getWriter(application);
                    writer.write(yaml.dumpAsMap(yamlMap));
                    writer.close();
                }

                RSAKeyEnum.NETTY_KEY.setPrivateKey(privateKey);
                RSAKeyEnum.NETTY_KEY.setPublicKey(publicKey);
            }

            if (yamlMap.get("avatarFileMaxSizeInKb") == null) {
                Global.AVATAR_FILE_MAX_SIZE_IN_KB = 5120L;
            } else {
                Global.AVATAR_FILE_MAX_SIZE_IN_KB = Long.parseLong(yamlMap.get("avatarFileMaxSizeInKb").toString());
            }

            if (yamlMap.get("avatarFilePath") == null) {
                Global.AVATAR_FILE_PATH = new File("/avatar");
            } else {
                Global.AVATAR_FILE_PATH = new File(yamlMap.get("avatarFilePath").toString());
            }

            if (yamlMap.get("tokenExpiredTime") == null) {
                TokenUtils.EXPIRED_TIME = 24L * 30 * 60 * 60 * 1000;
            } else {
                TokenUtils.EXPIRED_TIME = Long.parseLong(yamlMap.get("tokenExpiredTime").toString());
            }

            if (yamlMap.get("nettyTokenExpiredTime") == null) {
                TokenUtils.NETTY_EXPIRED_TIME = 24 * 60 * 60 * 1000;
            } else {
                TokenUtils.NETTY_EXPIRED_TIME = Long.parseLong(yamlMap.get("nettyTokenExpiredTime").toString());
            }

            Map<String, Object> serverMap = (Map<String, Object>) yamlMap.get("server");

            if (serverMap.get("nettyPort") == null) {
                NettyServer.PORT = 1234;
            } else {
                NettyServer.PORT = Integer.parseInt(serverMap.get("nettyPort").toString());
            }

            if (serverMap.get("nettySoBackLog") == null) {
                NettyServer.SO_BACK_LOG = 1024;
            } else {
                NettyServer.SO_BACK_LOG = Integer.parseInt(serverMap.get("nettySoBackLog").toString());
            }
        }

        if (yamlMap.containsKey("language")) {
            String language = yamlMap.get("language").toString();

            Assert.notNull(language, "The language in application.yml cannot be null");

            File file = new File(language);
            if (!file.exists()) {
                if ("zh_cn.json".equals(language)) {
                    FileUtils.saveResource("zh_cn.json", file);

                    if (!file.exists()) {
                        throw new FileNotFoundException("The file for the language setting in application.yml could not be found");
                    }
                } else {
                    throw new FileNotFoundException("The file for the language setting in application.yml could not be found");
                }
            }

            LanguageLoader.load(file);
        }
    }
}

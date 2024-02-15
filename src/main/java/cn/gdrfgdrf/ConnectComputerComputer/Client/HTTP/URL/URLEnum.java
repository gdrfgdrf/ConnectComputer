package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.URL;

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Handler.URLHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.ServerInfo;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.BeanManager;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.StringUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author gdrfgdrf
 */
@Getter
@ToString
public enum URLEnum {
    IS_AVAILABLE(
            URLMethod.GET,
            URLRequestMappingEnum.SERVER_INFO_REQUEST_MAPPING,
            "/available",
            null,
            false,
            false,
            false,
            false,
            false
    ),
    GET_PUBLIC_KEY(
            URLMethod.GET,
            URLRequestMappingEnum.SERVER_INFO_REQUEST_MAPPING,
            "/public-key",
            null,
            false,
            false,
            false,
            false,
            false
    ),
    LOGIN(
            URLMethod.POST,
            URLRequestMappingEnum.USER_REQUEST_MAPPING,
            "/login",
            null,
            true,
            true,
            false,
            true
    ),
    CHANGE_PASSWORD(
            URLMethod.PUT,
            URLRequestMappingEnum.USER_REQUEST_MAPPING,
            "/password",
            null,
            true,
            true,
            true,
            true
    ),
    LOGOUT(
            URLMethod.POST,
            URLRequestMappingEnum.USER_REQUEST_MAPPING,
            "/logout",
            null,
            true,
            true,
            true,
            true
    ),
    GET_COMPUTER_LIST(
            URLMethod.POST,
            URLRequestMappingEnum.COMPUTER_REQUEST_MAPPING,
            "/list",
            null,
            true,
            true,
            true,
            true
    );

    @NonNull
    public final URLMethod method;
    @NonNull
    public final URLRequestMappingEnum mapping;
    @NonNull
    public final String site;
    public final URLHandler urlHandler;
    @NonNull
    public final Boolean encrypted;
    @NonNull
    public final Boolean decrypted;
    @NonNull
    public final Boolean needToken;
    @NonNull
    public final Boolean needPublicKey;
    @NonNull
    public Boolean needClientVersion = true;

    private ServerInfo serverInfo;


    <T> URLEnum(
            URLMethod method,
            URLRequestMappingEnum mapping,
            String site, URLHandler<T> urlHandler,
            Boolean encrypted,
            Boolean decrypted,
            Boolean needToken,
            Boolean needPublicKey
    ) {
        this.method = method;
        this.mapping = mapping;
        this.site = site;
        this.urlHandler = urlHandler;
        this.encrypted = encrypted;
        this.decrypted = decrypted;
        this.needToken = needToken;
        this.needPublicKey = needPublicKey;
    }

    <T> URLEnum(
            URLMethod method,
            URLRequestMappingEnum mapping,
            String site, URLHandler<T> urlHandler,
            Boolean encrypted,
            Boolean decrypted,
            Boolean needToken,
            Boolean needPublicKey,
            Boolean needClientVersion
    ) {
        this.method = method;
        this.mapping = mapping;
        this.site = site;
        this.urlHandler = urlHandler;
        this.encrypted = encrypted;
        this.decrypted = decrypted;
        this.needToken = needToken;
        this.needPublicKey = needPublicKey;
        this.needClientVersion = needClientVersion;
    }

    public <T> String getFullURL(T[] args) {
        if (serverInfo == null) {
            DataStore dataStore = BeanManager.getInstance().getBean("DataStore");
            serverInfo = dataStore.getServerInfo();
        }

        if (urlHandler != null && args != null) {
            return serverInfo.httpProtocol() +
                    serverInfo.getIp() +
                    ":" +
                    serverInfo.getPort() +
                    mapping.getMapping() +
                    urlHandler.handle(site, args);
        }

        return serverInfo.httpProtocol() +
                serverInfo.getIp() +
                ":" +
                serverInfo.getPort() +
                mapping.getMapping() +
                site;
    }

    public static URLEnum getFromFullURL(String site) {
        Class<?> clazz = URLEnum.class;
        Object[] objects = clazz.getEnumConstants();

        if (objects == null) {
            return null;
        }

        for (Object o : objects) {
            URLEnum urlEnum = (URLEnum) o;
            String template = urlEnum.getMapping().getMapping() + urlEnum.getSite();

            if (urlEnum.getUrlHandler() != null) {
                if (template.equals(StringUtils.replaceToTemplate(template, site))) {
                    return urlEnum;
                }
            }

            if (template.equals(site)) {
                return urlEnum;
            }
        }

        return null;
    }
}

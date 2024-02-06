package cn.gdrfgdrf.ConnectComputerComputer.Utils;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.*;

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Interceptor.PrepareAndCallRequestInterceptor;
import okhttp3.OkHttpClient;

/**
 * @author gdrfgdrf
 */
public class HttpUtils {
    private HttpUtils() {}

    public static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
            .addInterceptor(new PrepareAndCallRequestInterceptor())
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build();

}

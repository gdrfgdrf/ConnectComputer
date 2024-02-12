package cn.gdrfgdrf.ConnectComputerServer.Utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;

import java.nio.charset.Charset;

/**
 * @author gdrfgdrf
 */
public class CharsetUtils {
    private CharsetUtils() {}

    public static Charset getRequestCharsetOrDefault(HttpServletRequest request, Charset defaultCharset) {
        MediaType mediaType = MediaType.parseMediaType(request.getContentType());
        Charset result = mediaType.getCharset();
        if (result == null) {
            result = defaultCharset;
        }

        return result;
    };

    public static Charset getRequestCharsetOrDefault(ServerHttpRequest request, Charset defaultCharset) {
        MediaType mediaType = request.getHeaders().getContentType();
        if (mediaType == null) {
            return defaultCharset;
        }

        Charset result = mediaType.getCharset();
        if (result == null) {
            result = defaultCharset;
        }

        return result;
    };
}

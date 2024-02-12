package cn.gdrfgdrf.ConnectComputerServer.Converter;

import cn.gdrfgdrf.ConnectComputerServer.Result.Result;
import cn.gdrfgdrf.ConnectComputerServer.Utils.JacksonUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author gdrfgdrf
 */
public class JacksonHttpMessageConverter extends MappingJackson2HttpMessageConverter {
    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        HttpHeaders headers = outputMessage.getHeaders();
        MediaType mediaType = headers.getContentType();
        Charset charset = mediaType != null ? mediaType.getCharset() : StandardCharsets.UTF_8;
        if (charset == null) {
            charset = StandardCharsets.UTF_8;
        }

        if (object instanceof String content) {
            try {
                byte[] strBytes = content.getBytes();
                int contentLength = strBytes.length;
                StreamUtils.copy(content, charset, outputMessage.getBody());

                if (headers.getContentLength() < 0) {
                    headers.setContentLength(contentLength);
                }
            } catch (IOException e) {
                throw new HttpMessageNotWritableException("I/O error while writing output message", e);
            }
            return;
        }
        if (object instanceof Result result) {
            try {
                String content = JacksonUtils.writeJsonString(result);

                byte[] strBytes = content.getBytes(charset);
                int contentLength = strBytes.length;
                StreamUtils.copy(content, charset, outputMessage.getBody());

                if (headers.getContentLength() < 0) {
                    headers.setContentLength(contentLength);
                }
            } catch (IOException e) {
                throw new HttpMessageNotWritableException("I/O error while writing output message", e);
            }
            return;
        }

        super.writeInternal(object, type, outputMessage);
    }
}

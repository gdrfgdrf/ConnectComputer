package cn.gdrfgdrf.ConnectComputerServer.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) {
        String ip = request.getRemoteAddr();
        String destination = request.getRequestURI();
        log.info(
                ip + " ------------------> {}",
                destination
        );

        return true;
    }
}

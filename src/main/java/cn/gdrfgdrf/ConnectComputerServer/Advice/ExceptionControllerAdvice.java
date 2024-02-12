package cn.gdrfgdrf.ConnectComputerServer.Advice;

import cn.gdrfgdrf.ConnectComputerServer.Bean.Information.Common.ErrorInformation;
import cn.gdrfgdrf.ConnectComputerServer.Exception.IllegalParameterException;
import cn.gdrfgdrf.ConnectComputerServer.Result.Result;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.ConnectComputerServer.Utils.JacksonUtils;
import cn.gdrfgdrf.ConnectComputerServer.Utils.RSAUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author gdrfgdrf
 */
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public Object constraintViolationExceptionHandle(HttpServletRequest httpServletRequest, ConstraintViolationException e) throws Exception {
        AtomicReference<ConstraintViolation<?>> constraintViolation = new AtomicReference<>();
        e.getConstraintViolations().forEach(constraintViolation::set);

        String message = constraintViolation.get().getMessage();
        String parameterName = constraintViolation.get().getPropertyPath().toString().split("\\.")[1];

        ErrorInformation errorInformation = new ErrorInformation();
        errorInformation.getErrorMessage().add(message.replace(
                "%VALUE%",
                parameterName
        ));

        return getResult(httpServletRequest, errorInformation);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object methodArgumentNotValidExceptionHandle(HttpServletRequest httpServletRequest, MethodArgumentNotValidException e) throws Exception {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> objectErrors = bindingResult.getAllErrors();

        ErrorInformation errorInformation = new ErrorInformation();
        objectErrors.forEach(error -> {
            FieldError fieldError = (FieldError) error;

            String errorMessage = error.getDefaultMessage()
                    .replace("%VALUE%", fieldError.getField());
            errorInformation.getErrorMessage().add(errorMessage);
        });

        return getResult(httpServletRequest, errorInformation);
    }

    @ExceptionHandler({
            NoSuchPaddingException.class,
            NoSuchAlgorithmException.class,
            InvalidKeyException.class,
            IllegalBlockSizeException.class,
            BadPaddingException.class,
    })
    public Object cryptExceptionHandle(HttpServletRequest httpServletRequest, Exception e) throws Exception {
        log.error(
                "Exception on {}: {}",
                httpServletRequest.getRemoteAddr(),
                e.getMessage()
        );

        Result result = new Result();
        result.setFromResultEnum(ResultEnum.ERROR_CRYPT);

        return encryptData(httpServletRequest, result);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object jsonExceptionHandle(HttpServletRequest httpServletRequest, HttpMessageNotReadableException e) throws Exception {
        log.error(
                "Exception on {}: {}",
                httpServletRequest.getRemoteAddr(),
                e.getMessage()
        );

        Result result = new Result();
        result.setFromResultEnum(ResultEnum.ERROR_HTTP_MESSAGE_NOT_READABLE);

        return encryptData(httpServletRequest, result);
    }

    @ExceptionHandler(Exception.class)
    public Object exceptionHandle(HttpServletRequest httpServletRequest, Exception e) throws Exception {
        Result result = new Result();

        if (e instanceof IllegalParameterException ||
                e.getCause() instanceof IllegalParameterException) {
            IllegalParameterException exception;

            if (e instanceof IllegalParameterException) {
                exception = (IllegalParameterException) e;
            } else {
                exception = (IllegalParameterException) e.getCause();
            }

            result.setCode(exception.getResultEnum().getCode());

            if (exception.getInformation() != null) {
                result.addData(exception.getInformation());
            }

            result.setFromResultEnum(exception.getResultEnum());
            log.warn(
                    "Exception on {}: {}",
                    httpServletRequest.getRemoteAddr(),
                    exception.getMessage()
            );

            if (exception.getResultEnum() == ResultEnum.ERROR_NOT_FOUND_PUBLIC_KEY_IN_HEADER) {
                return result;
            }
        } else {
            log.error(
                    "Exception on {}: {}",
                    httpServletRequest.getRemoteAddr(),
                    e.getMessage()
            );
            result.setFromResultEnum(ResultEnum.ERROR);

            e.printStackTrace();
        }

        return encryptData(httpServletRequest, result);
    }

    private Object getResult(HttpServletRequest httpServletRequest, ErrorInformation errorInformation) throws Exception {
        Result result = new Result().setFromResultEnum(ResultEnum.ERROR_PARAMETER);
        result.addData(errorInformation);

        return encryptData(httpServletRequest, result);
    }

    private Object encryptData(HttpServletRequest httpServletRequest, Result result) throws Exception {
        String publicKeyStr = httpServletRequest.getHeader("publicKey");
        if (publicKeyStr != null) {
            String resultStr = JacksonUtils.writeJsonString(result);
            PublicKey publicKey = RSAUtils.getPublicKey(publicKeyStr);
            resultStr = RSAUtils.publicEncrypt(resultStr, publicKey).toString();

            return resultStr;
        } else {
            return result;
        }
    }
}

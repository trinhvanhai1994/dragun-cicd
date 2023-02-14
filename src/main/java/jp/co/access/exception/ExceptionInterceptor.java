package jp.co.access.exception;

import jp.co.access.enums.ErrorCode;
import jp.co.access.factory.ResponseFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author HAITV
 */

@ControllerAdvice
public class ExceptionInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionInterceptor.class);

    public static final List<String> ACCEPT_LANGUAGES = new ArrayList<>();

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(HttpServletRequest req, Exception e) throws Exception {
        if (AnnotationUtils.findAnnotation(e.getClass(), org.springframework.web.bind.annotation.ResponseStatus.class) != null) {
            throw e;
        }

        try {
            if (e instanceof BasicException) {
                logger.error("Basic Exception with details: {}", e.toString());

                BasicException baseException = (BasicException) e;
                String code = baseException.getCode().toLowerCase();
                String message = baseException.getMessage();

                return ResponseFactory.error(code, message, baseException.getErrors());
            } else if (e instanceof MethodArgumentNotValidException) {
                MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;

                List<String> details = new ArrayList<>();
                for (ObjectError error : ex.getBindingResult().getAllErrors()) {
                    FieldError fieldError = (FieldError) error;

                    String defaultMessage = error.getDefaultMessage();
                    String message = getErrorMessageLanguage(req, defaultMessage);
                    if (StringUtils.isNotEmpty(message)) {
                        details.add(String.format("%s: %s", fieldError.getField(), message));
                    } else {
                        details.add(String.format("%s: %s", fieldError.getField(), defaultMessage));
                    }
                }

                return ResponseFactory.error(ErrorCode.INVALID_ARGUMENT.getCode(), ErrorCode.INVALID_ARGUMENT.getMessage(), details);
            } else if (e instanceof AccessDeniedException) {
                logger.error(e.getMessage(), e.toString());
                return ResponseFactory.error(ErrorCode.ACCESS_DENIED.getCode(), ErrorCode.ACCESS_DENIED.getMessage(), null);
            } else {
                logger.error(e.getMessage(), e.toString());
                return ResponseFactory.generalError(e.getMessage());
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex.toString());
            return ResponseFactory.generalError(ex.getMessage());
        }
    }

    public String getErrorMessageLanguage(HttpServletRequest req, String messageCode) {
        try {

            String acceptLanguage = req.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
            if (org.springframework.util.StringUtils.isEmpty(acceptLanguage) || !this.ACCEPT_LANGUAGES.contains(acceptLanguage)) {
                acceptLanguage = "";
            }

            Locale locale = new Locale(acceptLanguage, "");
            ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", locale, new UTF8Control());

            return resourceBundle.getString(messageCode);
        } catch (Exception e) {
            logger.error("Can not found message with code {}", messageCode);
            return null;
        }
    }
}

package jp.co.access.factory;

import jp.co.access.enums.ErrorCode;
import jp.co.access.utils.MessageConst;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

/**
 * @author HaiTV
 */
public class ResponseFactory {

    public static ResponseEntity error(String code, String message) {
        return error(HttpStatus.BAD_REQUEST, code, message, null);
    }

    public static ResponseEntity error(String code, String message, List<String> details) {
        return error(HttpStatus.BAD_REQUEST, code, message, details);
    }

    public static ResponseEntity error(HttpStatus httpStatus, String code, String message, List<String> details) {
        GenericResponse responseObject = new GenericResponse();
        responseObject
                .setSuccess(false)
                .setCode(code)
                .setMessage(message)
                .setDetails(details)
        ;
        return new ResponseEntity(responseObject, httpStatus);
    }

    public static ResponseEntity generalError() {
        GenericResponse responseObject = new GenericResponse();
        responseObject.setSuccess(false);
        responseObject.setCode(ErrorCode.INTERNAL_SERVER_ERROR.getCode());
        responseObject.setMessage(MessageConst.INTERNAL_SERVER_ERROR);
        responseObject.setDetails(List.of(MessageConst.E0000));
        return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity generalError(String details) {
        GenericResponse responseObject = new GenericResponse();
        responseObject.setSuccess(false);
        responseObject.setCode(ErrorCode.INTERNAL_SERVER_ERROR.getCode());
        responseObject.setMessage(MessageConst.INTERNAL_SERVER_ERROR);
        responseObject.setDetails(Collections.singletonList(details));
        return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static PageResponse buildPageResponse(Page page) {
        if (page == null) {
            return null;
        }
        int currentPage = page.getNumber() + 1;
        if (currentPage <= page.getTotalPages()) {
            return new PageResponse(page.getContent(), page.getTotalPages(), page.hasNext(), page.hasPrevious(), currentPage, page.getSize(), page.getTotalElements());
        } else {
            return new PageResponse(page.getContent(), page.getTotalPages(), null, null, null, null, page.getTotalElements());
        }
    }
}

package jp.co.access.enums;

import jp.co.access.utils.MessageConst;
import lombok.Data;

public enum ErrorCode {
    INTERNAL_SERVER_ERROR("500", MessageConst.INTERNAL_SERVER_ERROR),
    INVALID_ARGUMENT("403", MessageConst.INVALID_ARGUMENT),
    ACCESS_DENIED("401", MessageConst.ACCESS_DENIED),
    NOT_FOUND("404", MessageConst.NOT_FOUND);

    private String code;
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

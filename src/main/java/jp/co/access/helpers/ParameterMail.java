package jp.co.access.helpers;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * ParameterMail
 */
@Data
@Builder
public class ParameterMail {

    @NotNull
    private String baseUrl;

    private Long key;

    private String hash;

    private String code;

    private String mailAddress;

    private String userName;

    private String fullName;

    private boolean isAdmin;
}

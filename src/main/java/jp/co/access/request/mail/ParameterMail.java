package jp.co.access.request.mail;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author PhuocVD
 */

@Data
@Accessors(chain = true)
public class ParameterMail {
    private String sender;
    private String address;
}

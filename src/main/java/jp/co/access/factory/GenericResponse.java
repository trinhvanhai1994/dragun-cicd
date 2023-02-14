package jp.co.access.factory;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.access.enums.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author HAITV
 */

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GenericResponse implements Serializable {

    @JsonProperty(value = "success")
    private boolean success;
    @JsonProperty(value = "code")
    private String code;
    @JsonProperty(value = "message")
    private String message;
    @JsonProperty(value = "details")
    private List<String> details;

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.code = errorMessage.name().toLowerCase();
    }

}

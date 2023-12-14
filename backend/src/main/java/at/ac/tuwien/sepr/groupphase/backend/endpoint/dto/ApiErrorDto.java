package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class ApiErrorDto {

    @JsonIgnore
    private final HttpStatusCode httpStatusCode;

    private final LocalDateTime timestamp;
    private final String error;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> subErrors;

    public ApiErrorDto(HttpStatusCode httpStatusCode, String error) {
        this.httpStatusCode = httpStatusCode;
        this.error = error;
        this.timestamp = LocalDateTime.now();
    }

    public void addSubError(String key, String value) {
        if (subErrors == null) {
            subErrors = new HashMap<>();
        }
        subErrors.put(key, value);
    }

    @JsonProperty("status")
    public int getHttpsStatusAsInt() {
        return httpStatusCode.value();
    }

}
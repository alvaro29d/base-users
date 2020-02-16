package com.ado.base.users.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
@Getter
public class ErrorMessageDTO {

    @JsonIgnore
    private final HttpStatus status;

    private List<String> messages;

    public int getStatusCode() {
        return status.value();
    }

    public String getStatusDescription() {
        return status.getReasonPhrase();
    }

}

package com.ado.base.users.api.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ErrorMessageDTO {

    private int statusCode;
    private String statusDescription;
    private List<String> messages;

}

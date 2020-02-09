package com.ado.base.users.api.request;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateUserRequest {

    private String name;
    private String email;

}

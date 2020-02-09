package com.ado.base.users.api.request;

import lombok.*;

@Builder
@Getter
@ToString
public class CreateUserRequest {

    private String name;
    private String email;

}

package com.ado.base.users.api.request;

import lombok.*;

@Builder
@Getter
@ToString
public class CreateUserDTO {

    private String name;
    private String email;

}

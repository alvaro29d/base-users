package com.ado.base.users.api.request;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
public class UpsertUserDTO {

    private String name;
    private String email;

}

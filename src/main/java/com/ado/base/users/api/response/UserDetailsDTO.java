package com.ado.base.users.api.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDetailsDTO {

    private String id;
    private String name;
    private String email;
    private String dayOfBirth;

}

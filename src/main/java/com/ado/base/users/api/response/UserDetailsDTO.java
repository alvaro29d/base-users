package com.ado.base.users.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO {

    private String id;
    private String name;
    private String email;
    private String dayOfBirth;

}

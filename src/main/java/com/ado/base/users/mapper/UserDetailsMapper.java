package com.ado.base.users.mapper;

import com.ado.base.users.api.response.UserDetailsDTO;
import com.ado.base.users.model.User;
import org.apache.commons.lang3.ObjectUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public final class UserDetailsMapper {

    static DateTimeFormatter DOB_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private UserDetailsMapper() {}

    public static UserDetailsDTO getUserDetails(User user) {
        if(user == null) {
            return null;
        } else {
            return UserDetailsDTO.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .dayOfBirth(user.getDateOfBirth() == null ? null : DOB_FORMATTER.format(user.getDateOfBirth()))
                    .name(user.getFullName())
                    .build();
        }
    }

    public static List<UserDetailsDTO> getUsersDetails(List<User> users) {
        if(users == null) {
            return null;
        } else {
            return users.stream().map(UserDetailsMapper::getUserDetails).collect(Collectors.toList());
        }
    }

}

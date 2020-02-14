package com.ado.base.users.service;

import com.ado.base.users.api.request.UpsertUserDTO;
import com.ado.base.users.api.response.UserDetailsDTO;

import java.util.List;

public interface UserSvc {

    List<UserDetailsDTO> listUsers();

    UserDetailsDTO createUser(UpsertUserDTO user);

    void deleteUser(String id);

    UserDetailsDTO updateUser(String id, UpsertUserDTO user);

}

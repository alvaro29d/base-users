package com.ado.base.users.service;

import com.ado.base.users.api.request.CreateUserDTO;
import com.ado.base.users.api.request.UpdateUserDTO;
import com.ado.base.users.api.response.UserDetailsDTO;
import com.ado.base.users.model.User;

import java.util.List;

public interface UserSvc {

    List<UserDetailsDTO> listUsers();

    UserDetailsDTO createUser(CreateUserDTO user);

    void deleteUser(String id);

    UserDetailsDTO updateUser(String id, UpdateUserDTO user);

}

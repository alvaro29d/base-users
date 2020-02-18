package com.ado.base.users.service;

import com.ado.base.users.api.request.UpsertUserDTO;
import com.ado.base.users.model.User;

import java.util.List;

public interface UserSvc {

    List<User> listUsers();

    User createUser(UpsertUserDTO user);

    void deleteUser(String id);

    User updateUser(String id, UpsertUserDTO user);

}

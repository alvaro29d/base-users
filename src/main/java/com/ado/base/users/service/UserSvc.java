package com.ado.base.users.service;

import com.ado.base.users.api.request.CreateUserRequest;
import com.ado.base.users.api.request.UpdateUserRequest;
import com.ado.base.users.model.User;

import java.util.List;

public interface UserSvc {

    List<User> listUsers();

    User createUser(CreateUserRequest user);

    void deleteUser(String id);

    User updateUser(String id, UpdateUserRequest user);

}

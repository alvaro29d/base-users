package com.ado.base.users.api;

import com.ado.base.users.api.request.CreateUserRequest;
import com.ado.base.users.api.request.UpdateUserRequest;
import com.ado.base.users.model.User;
import com.ado.base.users.service.UserSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserSvc userSvc;

    public UserController(UserSvc userSvc) {
        this.userSvc = userSvc;
    }

    @ResponseBody
    @GetMapping
    public List<User> getUsers() {
        return userSvc.listUsers();
    }

    @PostMapping
    @ResponseBody
    public User addUser(@RequestBody CreateUserRequest user) {
        log.info("addUser, user={}", user);
        return userSvc.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable String id) {
        userSvc.deleteUser(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public User updateUser(@PathVariable String id, @RequestBody UpdateUserRequest user) {
        return userSvc.updateUser(id, user);
    }
}

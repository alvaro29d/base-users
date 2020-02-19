package com.ado.base.users.api;

import com.ado.base.users.api.request.UpsertUserDTO;
import com.ado.base.users.api.response.UserDetailsDTO;
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
    public List<UserDetailsDTO> getUsers() {
        return userSvc.listUsers();
    }

    @PostMapping
    @ResponseBody
    public UserDetailsDTO addUser(@RequestBody UpsertUserDTO user) {
        log.info("addUser, user={}", user);
        return userSvc.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable Integer id) {
        userSvc.deleteUser(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public UserDetailsDTO updateUser(@PathVariable Integer id, @RequestBody UpsertUserDTO user) {
        return userSvc.updateUser(id, user);
    }
}

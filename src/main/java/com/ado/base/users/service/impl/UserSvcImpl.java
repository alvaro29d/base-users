package com.ado.base.users.service.impl;

import com.ado.base.users.api.request.UpsertUserDTO;
import com.ado.base.users.dao.UserRepository;
import com.ado.base.users.model.User;
import com.ado.base.users.service.UserSvc;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserSvcImpl implements UserSvc {

    private final UserRepository userRepository;

    public UserSvcImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(UpsertUserDTO userRequest) {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .build();
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(String id, UpsertUserDTO user) {
        User userFromRepo = userRepository.getOne(id);
        userFromRepo.setEmail(user.getEmail());
        userFromRepo.setName(user.getName());
        return userRepository.save(userFromRepo);
    }

}

package com.ado.base.users.service.impl;

import com.ado.base.users.api.request.UpsertUserDTO;
import com.ado.base.users.api.response.UserDetailsDTO;
import com.ado.base.users.dao.UserRepository;
import com.ado.base.users.mapper.UserDetailsMapper;
import com.ado.base.users.model.User;
import com.ado.base.users.service.UserSvc;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSvcImpl implements UserSvc {

    private final UserRepository userRepository;

    public UserSvcImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDetailsDTO> listUsers() {
        return UserDetailsMapper.getUsersDetails(userRepository.findAll());
    }

    @Override
    public UserDetailsDTO createUser(UpsertUserDTO userRequest) {
        User user = User.builder()
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .build();
        return UserDetailsMapper.getUserDetails(userRepository.save(user));
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetailsDTO updateUser(Integer id, UpsertUserDTO user) {
        User userFromRepo = userRepository.getOne(id);
        userFromRepo.setEmail(user.getEmail());
        userFromRepo.setName(user.getName());
        return UserDetailsMapper.getUserDetails(userRepository.save(userFromRepo));
    }

}

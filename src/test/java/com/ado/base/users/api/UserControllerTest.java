package com.ado.base.users.api;

import com.ado.base.users.api.response.UserDetailsDTO;
import com.ado.base.users.model.User;
import com.ado.base.users.service.UserSvc;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserSvc userSvc;

    @Test
    @SneakyThrows
    public void testListUsers_empty() {
        when(userSvc.listUsers()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[]"));
    }

    @Test
    @SneakyThrows
    public void testListUsers() {
        UserDetailsDTO user = UserDetailsDTO.builder().name("fullName").email("email").id("id").build();
        when(userSvc.listUsers()).thenReturn(Collections.singletonList(user));
        String usersResponse = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        List<UserDetailsDTO> list = new ObjectMapper().readValue(usersResponse, new TypeReference<List<UserDetailsDTO>>() {});
        assertThat(list.get(0).getId(),is(user.getId()));

    }

}

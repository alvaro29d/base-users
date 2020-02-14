package com.ado.base.users.integration;


import com.ado.base.users.UsersApplication;
import com.ado.base.users.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = UsersApplication.class
)
@AutoConfigureMockMvc
@Slf4j
public class UserIntegrationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    public static final String EMAIL_1 = "valid@email.com";
    public static final String EMAIL_2 = "valid2@email.com";
    public static final String NAME = "validName";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAll_NoUsers() {
        assertThat(getUsers().size(), is(0));
    }

    @Test
    public void testFindAll_WithUsers() {
        String id1 = saveUser(buildUser(EMAIL_1)).getId();
        String id2 = saveUser(buildUser(EMAIL_2)).getId();

        assertThat(getUsers().size(), is(2));

        deleteUser(id1);
        deleteUser(id2);
    }

    @Test
    public void testSaveAndGetUser() {
        User user = saveUser(buildUser(EMAIL_1));

        assertThat(user, is(notNullValue()));
        assertThat(user.getEmail(), is(EMAIL_1));
//        assertThat(user.getFullName(), is(NAME));

        deleteUser(user.getId());
    }

    @Test
    public void testDeleteUser() {
        String id = saveUser(buildUser(EMAIL_1)).getId();
        assertThat(getUsers().size(), is(1));
        deleteUser(id);
        assertThat(getUsers().size(),is(0));
    }


    @Test
    public void testSaveInvalidUser() {
        try {
            saveUser(buildUser(""));
        } catch (Exception ex) {
            ex.toString();
        }
    }

    private User buildUser(String email) {
        return User.builder()
                .fullName(NAME)
                .email(email)
                .build();
    }

    @SneakyThrows
    private List<User> getUsers() {
        MvcResult mvcResult = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<User>>() {
        });
    }

    @SneakyThrows
    private void deleteUser(String id) {
        mockMvc.perform(delete("/users/{id}", id)).andExpect(status().isOk());
    }

    @SneakyThrows
    private User saveUser(User user) {
        MvcResult mvcResult = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
    }


}

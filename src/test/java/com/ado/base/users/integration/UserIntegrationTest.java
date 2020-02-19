package com.ado.base.users.integration;


import com.ado.base.users.UsersApplication;
import com.ado.base.users.api.request.UpsertUserDTO;
import com.ado.base.users.api.response.ErrorMessageDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.validation.ConstraintViolationException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.ado.base.users.model.User.UNIQUE_USER_EMAIL;
import static com.ado.base.users.model.User.UNIQUE_USER_NAME;
import static org.hamcrest.CoreMatchers.*;
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

    public static final String EMAIL_1 = "valid@email.com";
    public static final String EMAIL_2 = "valid2@email.com";
    public static final String NAME_1 = "validName";
    public static final String NAME_2 = "validName2";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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
        int id1 = saveUserSuccessfully(buildUser()).getId();
        int id2 = saveUserSuccessfully(UpsertUserDTO.builder()
                .email(EMAIL_2)
                .name(NAME_2)
                .build()).getId();

        assertThat(getUsers().size(), is(2));

        deleteUser(id1);
        deleteUser(id2);
    }

    @Test
    public void testSaveAndGetUser() {
        User user = saveUserSuccessfully(buildUser());

        assertThat(user, is(notNullValue()));
        assertThat(user.getEmail(), is(EMAIL_1));
        assertThat(user.getName(), is(NAME_1));

        deleteUser(user.getId());
    }

    @Test
    public void testDeleteUser() {
        int id = saveUserSuccessfully(buildUser()).getId();
        assertThat(getUsers().size(), is(1));
        deleteUser(id);
        assertThat(getUsers().size(),is(0));
    }

    @Test
    @SneakyThrows
    public void testUserDuplicateEmail_RespondsBadRequest (){
        User user = saveUserSuccessfully(buildUser());

        MvcResult mvcResult = saveUser(buildUser());

        verifyBadRequest(mvcResult, UNIQUE_USER_EMAIL);
        deleteUser(user.getId());
    }

    @Test
    @SneakyThrows
    public void testUserDuplicateName_RespondsBadRequest (){
        User user = saveUserSuccessfully(buildUser());

        UpsertUserDTO userDuplicated = buildUser();
        userDuplicated.setEmail(EMAIL_2);
        MvcResult mvcResult = saveUser(userDuplicated);

        verifyBadRequest(mvcResult, UNIQUE_USER_NAME);
        deleteUser(user.getId());
    }

    @SneakyThrows
    private void verifyBadRequest(MvcResult mvcResult, String errorMessage) {
        assertThat(mvcResult.getResponse().getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        ErrorMessageDTO errorMessageDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorMessageDTO.class);
        assertThat(errorMessageDTO.getStatusCode(), is(HttpStatus.BAD_REQUEST.value()));
        assertThat(errorMessageDTO.getStatusDescription(), is(HttpStatus.BAD_REQUEST.getReasonPhrase()));
        assertThat(errorMessageDTO.getMessages().get(0), is(errorMessage));
    }

    private UpsertUserDTO buildUser() {
        return UpsertUserDTO.builder()
                .name(NAME_1)
                .email(EMAIL_1)
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
    private void deleteUser(int id) {
        mockMvc.perform(delete("/users/{id}", id)).andExpect(status().isOk());
    }

    @SneakyThrows
    private User saveUserSuccessfully(UpsertUserDTO user) {
        MvcResult mvcResult = saveUser(user);
        assertThat(mvcResult.getResponse().getStatus(), is(HttpStatus.OK.value()));
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
    }

    @SneakyThrows
    private MvcResult saveUser(UpsertUserDTO user) {
        return  mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andReturn();
    }


}

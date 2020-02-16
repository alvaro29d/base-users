package com.ado.base.users.dao;

import com.ado.base.users.model.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByName_NotFound() {
        List<User> user = userRepository.findAllById(singletonList("id"));
        assertThat(user, is(emptyList()));
    }

    @Test
    public void testFindById_NotFound() {
        Optional<User> user = userRepository.findById("id");
        assertThat(user, is(Optional.empty()));
    }

    @Test
    public void testSaveUser() {
        userRepository.save(getUser());
        User user = getUser();
        user.setEmail("another@mail.com");
        userRepository.save(user);
        assertThat(userRepository.findAll().size(), is(2));
    }

    @Test
    public void testSaveUser_EmptyMail_ThrowsException() {
        expectedException.expect(ConstraintViolationException.class);
        expectedException.expectMessage("invalid.user.email.notEmpty");
        userRepository.save(User.builder().build());
    }

    @Test
    public void testSaveUser__ThrowsException() {
        expectedException.expect(ConstraintViolationException.class);
        expectedException.expectMessage("must be a well-formed email address");
        userRepository.save(User.builder().email("mail").build());
    }

    @Test
    public void testFindById() {
        User user = userRepository.save(getUser());
        Optional<User> retreivedUser = userRepository.findById(user.getId());
        assertThat(retreivedUser.isPresent(), is(Boolean.TRUE));
        assertThat(retreivedUser.get().getEmail(), is("e@mail.com"));
    }

    @Test
    public void testSaveUser_DuplicatedEmail_ThrowsException() {
        expectedException.expect(DataIntegrityViolationException.class);
        expectedException.expectMessage(
                allOf(containsString("could not execute statement"),
                containsString("constraint")));
        userRepository.save(getUser());
        userRepository.save(getUser());
    }

    @Test
    public void testDateOfBith_Future() {
        User user = getUser();
        user.setDateOfBirth(LocalDate.now().plusYears(300));
        expectedException.expect(ConstraintViolationException.class);
        expectedException.expectMessage(allOf(
                containsString("must be a date in the past or in the present"),
                containsString("dateOfBirth")));
        userRepository.save(user);
    }

    private User getUser() {
        return User.builder()
                .email("e@mail.com")
                .dateOfBirth(LocalDate.now())
                .build();
    }

}

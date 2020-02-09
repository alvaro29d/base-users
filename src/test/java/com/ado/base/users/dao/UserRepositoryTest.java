package com.ado.base.users.dao;

import com.ado.base.users.UsersApplication;
import com.ado.base.users.model.User;
import org.assertj.core.internal.bytebuddy.matcher.CollectionSizeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

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

    @Autowired
    private EntityManager entityManager;

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

    private User getUser() {
        return User.builder()
                .email("e@mail.com")
                .build();
    }

    @Test
    public void testSaveUser_EmptyMail_ThrowsException() {
        expectedException.expect(ConstraintViolationException.class);
        expectedException.expectMessage(
                allOf(containsString("must not be null"),
                        containsString("email")));
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
        assertThat(retreivedUser.get().getEmail(), is("e@mail.com"));
    }

}

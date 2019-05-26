package edu.iis.mto.blog.domain.repository;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;

import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setFirstName("Jan");
        user.setEmail("john@domain.com");
        user.setLastName("Kowalski");
        user.setAccountStatus(AccountStatus.NEW);
    }

    @Test
    public void shouldFindNoUsersIfRepositoryIsEmpty() {

        List<User> users = repository.findAll();

        Assert.assertThat(users, Matchers.hasSize(0));
    }

    @Test
    public void shouldFindOneUsersIfRepositoryContainsOneUserEntity() {
        User persistedUser = entityManager.persist(user);
        List<User> users = repository.findAll();

        Assert.assertThat(users, Matchers.hasSize(1));
        Assert.assertThat(users.get(0).getEmail(), Matchers.equalTo(persistedUser.getEmail()));
    }

    @Test
    public void shouldStoreANewUser() {

        User persistedUser = repository.save(user);

        Assert.assertThat(persistedUser.getId(), Matchers.notNullValue());
    }


    @Test
    public void shouldFindUsersByFirstName(){
        repository.save(user);

        String searchString = "jan";

        List<User> userList = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(searchString,searchString,searchString);

        Assert.assertThat(userList.contains(user), is(true));
    }

    @Test
    public void shouldFindUsersByPartialFistName(){
        repository.save(user);

        String searchString = "ja";

        List<User> userList = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(searchString,searchString,searchString);

        Assert.assertThat(userList.contains(user), is(true));
    }

    @Test
    public void shouldFindUsersByEmail(){
        repository.save(user);

        String searchString = "john@domain.com";

        List<User> userList = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(searchString,searchString,searchString);

        Assert.assertThat(userList.contains(user), is(true));
    }

    @Test
    public void shouldFindUsersByPartialEmail(){
        repository.save(user);

        String searchString = "@dom";

        List<User> userList = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(searchString,searchString,searchString);

        Assert.assertThat(userList.contains(user), is(true));
    }

    @Test
    public void shouldFindUsersByLastName(){
        repository.save(user);

        String searchString = "Kowalski";

        List<User> userList = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(searchString,searchString,searchString);

        Assert.assertThat(userList.contains(user), is(true));
    }

    @Test
    public void shouldFindUsersByPartialLastName(){
        repository.save(user);

        String searchString = "lsk";

        List<User> userList = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(searchString,searchString,searchString);

        Assert.assertThat(userList.contains(user), is(true));
    }

    @Test
    public void shouldNotFindUsersByInvalidData(){
        repository.save(user);

        String searchString = "fake";

        List<User> userList = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(searchString,searchString,searchString);

        Assert.assertThat(userList.contains(user), is(false));
    }
}

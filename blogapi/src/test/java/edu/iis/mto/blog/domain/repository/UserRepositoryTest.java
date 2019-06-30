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
        user.setLastName("Kowalski");
        user.setEmail("john@domain.com");
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
    public void shouldFindUserByFirstName(){

        List<User> usersList = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("Jan","","");

        Assert.assertThat(usersList.contains(user), Matchers.is(equals(true)));
    }

    @Test
    public void shouldFindUserByLastName(){

        List<User> usersList = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("","Kowlaski", "");

        Assert.assertThat(usersList.contains(user), Matchers.is(equals(true)));
    }

    @Test
    public void shouldFindUserByEmail(){

        List<User> usersList = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("","", "john@domain.com");

        Assert.assertThat(usersList.contains(user), Matchers.is(equals(true)));
    }

    @Test
    public void shouldNotFindUserByWrongFirstName(){
        List<User> usersList = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("Ja","","");

        Assert.assertThat(usersList.contains(user), Matchers.is(equals(false)));
    }

    @Test
    public void shouldNotFindUserByWrongLastName(){
        List<User> usersList = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("","Kowal","");

        Assert.assertThat(usersList.contains(user), Matchers.is(equals(false)));
    }

    @Test
    public void shouldNotFindUserByWrongEmail(){
        List<User> usersList = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("","","wrongTest@gmail.com");

        Assert.assertThat(usersList.contains(user), Matchers.is(equals(false)));
    }

}

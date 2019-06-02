package edu.iis.mto.blog.domain.repository;

import java.util.ArrayList;
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
    public void finding_user_respectively_by_firstname_by_lastname_by_email(){
        List<User> users;
        repository.save(user);
        List<User> result = new ArrayList<>();
        result.add(user);
        users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("Jan","","");
        Assert.assertThat(users.get(0),Matchers.equalTo(result.get(0)));
        users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("","Kowalski","");
        Assert.assertThat(users.get(0),Matchers.equalTo(result.get(0)));
        users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("","","john@domain.com");
        Assert.assertThat(users.get(0),Matchers.equalTo(result.get(0)));

    }

    @Test
    public void finding_user_when_all_data_in_capital_letters_or_lowercase_letters(){
        List<User> users;
        repository.save(user);
        List<User> result = new ArrayList<>();
        result.add(user);
        users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("JAN","KOWALSKI","JOHN@DOMAIN.COM");
        Assert.assertThat(users,Matchers.equalTo(result));
        users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("jan","kowalski","john@domain.com");
        Assert.assertThat(users,Matchers.equalTo(result));
    }

   @Test
    public void finding_user_witch_doesnt_exist_should_return_no_users(){
       List<User> users;
       repository.save(user);
       List<User> result = new ArrayList<>();
       users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("Rafał","asdn","Rafal@domain.com");
       Assert.assertThat(users,Matchers.equalTo(result));
   }
}

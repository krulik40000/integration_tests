package edu.iis.mto.blog.domain.repository;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private LikePostRepository likePostRepository;

    private User user;
    private BlogPost blogPost;
    private LikePost likePost;


    @Before
    public void init(){
        user = new User();
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setEmail("jk@gmail.com");
        user.setAccountStatus(AccountStatus.NEW);

        blogPost = new BlogPost();
        blogPost.setUser(user);

        likePost = new LikePost();
        likePost.setUser(user);
        likePost.setPost(blogPost);
    }

    @Test
    public void shouldFindNoLikes(){
        List<LikePost> likePosts = likePostRepository.findAll();

        Assert.assertThat(likePosts, Matchers.hasSize(0));
    }

}
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
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LikePostRepository likePostRepository;

    private LikePost likePost;

    private BlogPost blogPost;

    private User user;

    @Before
    public void setup(){

        user = new User();
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setEmail("john@domain.com");
        user.setAccountStatus(AccountStatus.CONFIRMED);
        entityManager.persist(user);

        blogPost = new BlogPost();
        blogPost.setEntry("test post");
        blogPost.setUser(user);
        entityManager.persist(blogPost);

        likePost = new LikePost();
        likePost.setPost(blogPost);
        likePost.setUser(user);
    }

    @Test
    public void if_likePostRepository_is_not_saved_should_not_find_liked_posts(){
        Optional<LikePost> liked = likePostRepository.findByUserAndPost(user,blogPost);
        Optional<LikePost> result = Optional.ofNullable(null);
        Assert.assertThat(liked , Matchers.equalTo(result));
    }
    @Test
    public void when_likePostRepository_is_saved_should_find_liked_posts(){
        likePostRepository.save(likePost);
        Optional<LikePost> liked = likePostRepository.findByUserAndPost(user,blogPost);
        Optional<LikePost> result = Optional.of(likePost);
        Assert.assertThat(liked , Matchers.equalTo(result));
    }
}

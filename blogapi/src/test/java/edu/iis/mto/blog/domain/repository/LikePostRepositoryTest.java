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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        repository.save(user);

        blogPost = new BlogPost();
        blogPost.setUser(user);
        blogPost.setEntry("test");
        entityManager.persist(blogPost);

        likePost = new LikePost();
        likePost.setUser(user);
        likePost.setPost(blogPost);
    }

    @Test
    public void shouldFindNoLikes(){
        List<LikePost> likePosts = likePostRepository.findAll();

        Assert.assertThat(likePosts, Matchers.hasSize(0));
    }

    @Test
    public void shouldFIndNoLikesForUser(){
        Optional<LikePost> likePosts = likePostRepository.findByUserAndPost(user, blogPost);

        Assert.assertThat(likePosts, Matchers.is(Optional.empty()));
    }

    @Test
    public void shouldFindOneLikePost(){
        likePostRepository.save(likePost);
        List<LikePost> likePosts = likePostRepository.findAll();

        Assert.assertThat(likePosts, Matchers.hasSize(1));
    }

    @Test
    public void shouldFindOneLikePostForUser(){
        likePostRepository.save(likePost);
        Optional<LikePost> optional = likePostRepository.findByUserAndPost(user, blogPost);
        List<LikePost> likePosts = optional.map(Collections::singletonList).orElseGet(Collections::emptyList);

        Assert.assertThat(likePosts, Matchers.hasSize(1));
        Assert.assertThat(likePosts.get(0), Matchers.equalTo(likePost));
    }

    @Test
    public void changeDataInDatabase(){
        likePostRepository.save(likePost);
        List<LikePost> likePosts = likePostRepository.findAll();

        likePosts.get(0).getPost().setEntry("NEW");

        likePostRepository.save(likePosts.get(0));

        likePosts = likePostRepository.findAll();

        Assert.assertThat(likePosts.get(0).getPost().getEntry(), Matchers.equalTo("NEW"));
    }

}
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private LikePostRepository likePostRepository;

    private LikePost likePost;
    private User user;
    private BlogPost blogPost;

    @Before
    public void init() {
        user = new User();
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setEmail("user@example.com");
        user.setAccountStatus(AccountStatus.CONFIRMED);

        testEntityManager.persist(user);

        blogPost = new BlogPost();
        blogPost.setUser(user);
        blogPost.setEntry("Test");

        testEntityManager.persist(blogPost);

        likePost = new LikePost();
        likePost.setUser(user);
        likePost.setPost(blogPost);
    }

    @Test
    public void shouldNotFindLikedPostsTest() {
        Optional<LikePost> likedPosts = likePostRepository.findByUserAndPost(user, blogPost);

        Assert.assertThat(likedPosts, is(Optional.empty()));
    }

    @Test
    public void shouldSaveLikedPostTest() {
        likePostRepository.save(likePost);
        List<LikePost> likePostList = likePostRepository.findAll();

        Assert.assertThat(likePostList.size(), is(equalTo(1)));
        Assert.assertThat(likePostList.get(0), is(equalTo(likePost)));
    }

    @Test
    public void shouldFindSaveLikedPostsTest() {
        likePostRepository.save(likePost);

        Optional<LikePost> likedPost = likePostRepository.findByUserAndPost(user, blogPost);

        Assert.assertThat(likedPost, Matchers.is(Optional.of(likePost)));
    }

    @Test
    public void shouldNotFindLikedPostWhenInvalidUserIsGivenTest() {
        likePostRepository.save(likePost);
        User invalidUser = new User();
        invalidUser.setEmail("secondExample@Email.com");
        invalidUser.setAccountStatus(AccountStatus.CONFIRMED);

        testEntityManager.persist(invalidUser);

        Optional<LikePost> likePost = likePostRepository.findByUserAndPost(invalidUser, blogPost);

        Assert.assertThat(likePost, is(Optional.empty()));
    }

    @Test
    public void shouldNotFindLikedPostWhenInvalidBlogPostIsGivenTest() {
        likePostRepository.save(likePost);
        BlogPost invalidBlogPost = new BlogPost();
        invalidBlogPost.setEntry("Test2");
        invalidBlogPost.setUser(user);

        testEntityManager.persist(invalidBlogPost);

        Optional<LikePost> likePost = likePostRepository.findByUserAndPost(user, invalidBlogPost);

        Assert.assertThat(likePost, is(Optional.empty()));
    }

    @Test
    public void shouldProperlyUpdateLikePostTest() {
        likePostRepository.save(likePost);
        Optional<LikePost> likedPost;
        likedPost = likePostRepository.findByUserAndPost(user, blogPost);
        Assert.assertThat(likedPost, Matchers.is(Optional.of(likePost)));

        likePost.getUser().setEmail("secondExample@Email.com");

        likePostRepository.save(likePost);

        likedPost = likePostRepository.findByUserAndPost(user, blogPost);
        Assert.assertThat(likedPost, Matchers.is(Optional.of(likePost)));

        Assert.assertThat(likePostRepository.findAll().size(), is(equalTo(1)));
    }
}

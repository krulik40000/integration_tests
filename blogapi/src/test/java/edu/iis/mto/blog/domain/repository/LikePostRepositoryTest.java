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

import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {

    @Autowired
    private LikePostRepository likePostRepository;

    @Autowired
    private TestEntityManager entityManager;

    private LikePost likePost;
    private BlogPost blogPost;
    private User user;

    @Before
    public void setup() {
        user = new User();
        user.setLastName("Bond");
        user.setFirstName("Jan");
        user.setEmail("janbond@mail.com");
        user.setAccountStatus(AccountStatus.CONFIRMED);

        blogPost = new BlogPost();
        blogPost.setUser(user);
        blogPost.setEntry("post about cats. Cats rule the world.");

        likePost = new LikePost();
        likePost.setUser(user);
        likePost.setPost(blogPost);

        entityManager.persist(user);
        entityManager.persist(blogPost);
    }

    @Test
    public void shouldFindNoLikesIfRepositoryIsEmpty() {
        List<LikePost> result = likePostRepository.findAll();

        Assert.assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void shouldFindOneLikePostIfRepositoryContainsOneEntity() {
        LikePost persistedLikePost = entityManager.persist(likePost);
        List<LikePost> result = likePostRepository.findAll();

        Assert.assertThat(result, Matchers.hasSize(1));
        Assert.assertThat(result.get(0)
                                .getPost(),
                Matchers.equalTo(persistedLikePost.getPost()));
        Assert.assertThat(result.get(0)
                                .getUser(),
                Matchers.equalTo(persistedLikePost.getUser()));
    }

    @Test
    public void shouldStoreANewLikePost() {
        LikePost persistedLikePost = likePostRepository.save(likePost);

        Assert.assertThat(persistedLikePost.getId(), Matchers.notNullValue());
    }

    @Test
    public void searchingLikePostByUserAndBlogPostShouldReturnLikePostEntity() {
        likePostRepository.save(likePost);

        Optional<LikePost> likePostResult = likePostRepository.findByUserAndPost(user, blogPost);

        Assert.assertThat(likePostResult.isPresent(), is(true));
        Assert.assertThat(likePostResult, Matchers.is(Optional.of(likePost)));
    }

    @Test
    public void searchingLikePostByWrongBlogPostShouldReturnEmptyResult() {
        BlogPost fakePost = new BlogPost();
        fakePost.setEntry("this one is about dogs.");
        fakePost.setUser(user);
        entityManager.persist(fakePost);

        Optional<LikePost> likePostResult = likePostRepository.findByUserAndPost(user, fakePost);

        Assert.assertThat(likePostResult.isPresent(), is(false));
    }
}

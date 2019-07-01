package edu.iis.mto.blog.domain.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import junit.framework.Assert;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LikePostRepository likePostRepository;
    
    private User user;
    private LikePost likePost;
    private BlogPost blogPost;
    
    @Before
    public void setUp() {
        user = new User();
        user.setFirstName("Jan");
        user.setLastName("Tester");
        user.setEmail("john@domain.com");
        user.setAccountStatus(AccountStatus.NEW);
        userRepository.save(user);
        
        blogPost = new BlogPost();
        blogPost.setEntry("test");
        blogPost.setUser(user);
        
        entityManager.persist(blogPost);
        
        likePost = new LikePost();
        likePost.setPost(blogPost);
        likePost.setUser(user);
    }
    
    @Test
    public void shouldFindNoLikesIfRepositoryEmpty() {
        List<LikePost> likePosts = likePostRepository.findAll();
        assertThat(likePosts,hasSize(0));
    }
    
    @Test
    public void shouldFindOneLikeIfIsInRepository() {
        likePostRepository.save(likePost);
        List<LikePost> likePosts = likePostRepository.findAll();
        assertThat(likePosts,hasSize(1));
        assertThat(likePosts.contains(likePost),is(true));
    }
}

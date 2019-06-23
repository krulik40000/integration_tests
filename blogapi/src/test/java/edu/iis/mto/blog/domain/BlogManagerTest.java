package edu.iis.mto.blog.domain;

import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.repository.BlogPostRepository;
import edu.iis.mto.blog.domain.repository.LikePostRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.api.request.UserRequest;
import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;
import edu.iis.mto.blog.domain.repository.UserRepository;
import edu.iis.mto.blog.mapper.BlogDataMapper;
import edu.iis.mto.blog.services.BlogService;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogManagerTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    BlogPostRepository blogPostRepository;

    @MockBean
    LikePostRepository likedPostRepository;

    @Autowired
    BlogDataMapper dataMapper;

    @Autowired
    BlogService blogService;



    @Test
    public void creatingNewUserShouldSetAccountStatusToNEW() {
        blogService.createUser(new UserRequest("John", "Steward", "john@domain.com"));
        ArgumentCaptor<User> userParam = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userParam.capture());
        User user = userParam.getValue();
        Assert.assertThat(user.getAccountStatus(), Matchers.equalTo(AccountStatus.NEW));
    }

    @Test
    public void confirmed_user_should_be_able_to_like_post(){

        User testuser1 = new User();
        testuser1.setEmail("test@test.test");
        testuser1.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testuser1));

        User testuser2 = new User();
        testuser2.setEmail("test2@test2.test2");
        testuser2.setId(2L);
        testuser2.setAccountStatus(AccountStatus.CONFIRMED);
        when(userRepository.findById(2L)).thenReturn(Optional.of(testuser2));

        BlogPost post = new BlogPost();
        post.setId(1L);
        post.setUser(testuser1);
        when(blogPostRepository.findById(1L)).thenReturn(Optional.of(post));

        when(likedPostRepository.findByUserAndPost(testuser2, post)).thenReturn(Optional.empty());
        blogService.addLikeToPost(testuser2.getId(), post.getId());

        ArgumentCaptor<LikePost> likePostParam = ArgumentCaptor.forClass(LikePost.class);
        verify(likedPostRepository).save(likePostParam.capture());
        LikePost likePost = likePostParam.getValue();

        Assert.assertThat(likePost.getPost(), Matchers.is(post));
        Assert.assertThat(likePost.getUser(), Matchers.is(testuser2));


    }
}

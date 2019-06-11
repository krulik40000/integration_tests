package edu.iis.mto.blog.domain;

import edu.iis.mto.blog.domain.errors.DomainError;
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
    private UserRepository userRepository;

    @MockBean
    private BlogPostRepository blogPostRepository;

    @MockBean
    private LikePostRepository likePostRepository;

    @Autowired
    private BlogDataMapper dataMapper;

    @Autowired
    private BlogService blogService;

    @Test
    public void creatingNewUserShouldSetAccountStatusToNEW() {
        blogService.createUser(new UserRequest("John", "Steward", "john@domain.com"));
        ArgumentCaptor<User> userParam = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userParam.capture());
        User user = userParam.getValue();
        Assert.assertThat(user.getAccountStatus(), Matchers.equalTo(AccountStatus.NEW));
    }

    @Test
    public void shouldSaveAddedLikeByConfirmedAccountTest(){
        User owner = new User();
        owner.setEmail("owner@test.com");
        owner.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));

        User liker = new User();
        liker.setEmail("liker@test.com");
        liker.setId(2L);
        liker.setAccountStatus(AccountStatus.CONFIRMED);
        when(userRepository.findById(2L)).thenReturn(Optional.of(liker));

        BlogPost blogPost = new BlogPost();
        blogPost.setId(1L);
        blogPost.setUser(owner);
        when(blogPostRepository.findById(1L)).thenReturn(Optional.of(blogPost));

        when(likePostRepository.findByUserAndPost(liker, blogPost)).thenReturn(Optional.empty());
        blogService.addLikeToPost(liker.getId(), blogPost.getId());

        ArgumentCaptor<LikePost> likePostParam = ArgumentCaptor.forClass(LikePost.class);
        verify(likePostRepository).save(likePostParam.capture());
        LikePost likePost = likePostParam.getValue();
        Assert.assertThat(likePost.getPost(), Matchers.is(blogPost));
        Assert.assertThat(likePost.getUser(), Matchers.is(liker));
    }

    @Test(expected = DomainError.class)
    public void shouldThrownDomainErrorWhenUserAddLikeToOwnPostTest() {
        User owner = new User();
        owner.setEmail("owner@test.com");
        owner.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));

        User liker = new User();
        liker.setEmail("liker@test.com");
        liker.setId(2L);
        when(userRepository.findById(2L)).thenReturn(Optional.of(liker));

        BlogPost blogPost = new BlogPost();
        blogPost.setId(1L);
        blogPost.setUser(owner);
        when(blogPostRepository.findById(1L)).thenReturn(Optional.of(blogPost));

        blogService.addLikeToPost(owner.getId(), blogPost.getId());
    }
}

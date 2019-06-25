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

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogManagerTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    BlogPostRepository blogPostRepository;

    @MockBean
    LikePostRepository likePostRepository;

    @Autowired
    BlogDataMapper dataMapper;

    @Autowired
    BlogService blogService;

    @Test
    public void creatingNewUserShouldSetAccountStatusToNEW() {
        blogService.createUser(new UserRequest("John", "Steward", "john@domain.com"));
        ArgumentCaptor<User> userParam = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userParam.capture());
        User user = userParam.getValue();
        Assert.assertThat(user.getAccountStatus(), Matchers.equalTo(AccountStatus.NEW));
    }

    @Test
    public void addLikeFromAccountWithStatusConfirmed_shouldLikeBeAdded(){
        User owner = new User();
        owner.setId(1L);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(owner));

        User secoundUser = new User();
        secoundUser.setId(2L);
        secoundUser.setAccountStatus(AccountStatus.CONFIRMED);
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(secoundUser));

        BlogPost blogPost = new BlogPost();
        blogPost.setId(1L);
        blogPost.setUser(owner);
        Mockito.when(blogPostRepository.findById(1L)).thenReturn(Optional.of(blogPost));

        blogService.addLikeToPost(secoundUser.getId(), blogPost.getId());

        ArgumentCaptor<LikePost> argumentCaptor = ArgumentCaptor.forClass(LikePost.class);
        Mockito.verify(likePostRepository).save(argumentCaptor.capture());
        LikePost likePost = argumentCaptor.getValue();

        Assert.assertThat(likePost.getPost(), Matchers.is(blogPost));
        Assert.assertThat(likePost.getUser(), Matchers.is(secoundUser));
    }

}

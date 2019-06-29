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

import static org.mockito.Mockito.verify;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

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
    public void userWithStatusConfirmedShouldBeAbleToLikePost() {
        User user = new User();
        user.setAccountStatus(AccountStatus.CONFIRMED);
        user.setFirstName("john");
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User postOwner = new User();
        postOwner.setAccountStatus(AccountStatus.CONFIRMED);
        postOwner.setFirstName("Peter");
        postOwner.setId(2L);
        when(userRepository.findById(2L)).thenReturn(Optional.of(postOwner));

        BlogPost post = new BlogPost();
        post.setUser(postOwner);
        post.setEntry("about cats again!");
        post.setId(1L);
        when(blogPostRepository.findById(1L)).thenReturn(Optional.of(post));

        blogService.addLikeToPost(user.getId(), post.getId());

        ArgumentCaptor<LikePost> likePostArgumentCaptor = ArgumentCaptor.forClass(LikePost.class);
        verify(likePostRepository).save(likePostArgumentCaptor.capture());
        LikePost like = likePostArgumentCaptor.getValue();
        Assert.assertThat(like.getUser(), is(user));
    }
}

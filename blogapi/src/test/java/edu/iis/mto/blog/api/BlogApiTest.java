package edu.iis.mto.blog.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.iis.mto.blog.api.request.UserRequest;
import edu.iis.mto.blog.domain.errors.DomainError;
import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.dto.Id;
import edu.iis.mto.blog.services.BlogService;
import edu.iis.mto.blog.services.DataFinder;

@RunWith(SpringRunner.class)
@WebMvcTest(BlogApi.class)
public class BlogApiTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BlogService blogService;

    @MockBean
    private DataFinder finder;

    @Test
    public void postBlogUserShouldResponseWithStatusCreatedAndNewUserId() throws Exception {
        Long newUserId = 1L;
        UserRequest user = new UserRequest();
        user.setEmail("john@domain.com");
        user.setFirstName("John");
        user.setLastName("Steward");
        Mockito.when(blogService.createUser(user))
               .thenReturn(newUserId);
        String content = writeJson(user);

        mvc.perform(post("/blog/user").contentType(MediaType.APPLICATION_JSON_UTF8)
                                      .accept(MediaType.APPLICATION_JSON_UTF8)
                                      .content(content))
           .andExpect(status().isCreated())
           .andExpect(content().string(writeJson(new Id(newUserId))));
    }

    @Test
    public void shouldReturnError404IfUserNotFound() throws Exception {
        Mockito.when(finder.getUserData(0L))
               .thenThrow(new DomainError(DomainError.USER_NOT_FOUND));
        mvc.perform(get("/blog/user/{id}", 0))
           .andExpect(status().isNotFound());
    }
    
    @Test
    public void shouldReturnError409IfDataIntegrityViolationExceptionIsThrown() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("Jan");
        userRequest.setLastName("Tester");
        userRequest.setEmail("john@domain.com");

        Mockito.when(blogService.createUser(userRequest))
               .thenThrow(new DataIntegrityViolationException("EXCEPTION"));

        String content = writeJson(userRequest);
        mvc.perform(post("/blog/user").contentType(MediaType.APPLICATION_JSON_UTF8)
                                      .accept(MediaType.APPLICATION_JSON_UTF8)
                                      .content(content))
           .andExpect(status().isConflict());
    }

    

    private String writeJson(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writer()
                                 .writeValueAsString(obj);
    }

}

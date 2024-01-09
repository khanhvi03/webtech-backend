package htwberlin.webtech.postTest;

import htwberlin.webtech.post.PostController;
import htwberlin.webtech.post.PostDto;
import htwberlin.webtech.post.PostRequest;
import htwberlin.webtech.post.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService service;

    @Test
    @DisplayName("should return all posts from post service")
    void testGetAllPosts() throws Exception {
        // given
        var posts = List.of(
                new PostDto("Title 1","Author 1", "Content 1"),
                new PostDto("Title 2", "Author 2", "Content 2")
        );
        doReturn(posts).when(service).getAll();

        // when
        mockMvc.perform(get("/api/posts"))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value("Title 1"))
                .andExpect(jsonPath("$[0].author").value("Author 1"))
                .andExpect(jsonPath("$[0].content").value("Content 1"))
                .andExpect(jsonPath("$[1].title").value("Title 2"))
                .andExpect(jsonPath("$[1].author").value("Author 2"))
                .andExpect(jsonPath("$[1].content").value("Content 2"));
    }

    @Test
    @DisplayName("should return post by id from post service")
    void testFindPostById() throws Exception {
        // given
        Long postId = 1L;
        var post = new PostDto("Title 1", "Author 1", "Content 1" );
        doReturn(post).when(service).findPostById(postId);

        // when
        mockMvc.perform(get("/api/posts/{postId}", postId))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.author").value(post.getAuthor()))
                .andExpect(jsonPath("$.content").value(post.getContent()));
    }

    @Test
    @DisplayName("should return 404 if post is not found")
    void testFindByIdForNotFoundPost() throws Exception{
        //given
        doReturn(null).when(service).findPostById(any());

        //when
        mockMvc.perform(get("/api/posts/123"))
                //then
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should create post and return 201")
    void testCreatePost() throws Exception {
        // given
        String postToCreateAsJson = "{\"title\": \"Title 1\", \"author\":\"Author 1\", \"content\":\"Content 1\"}";
        var post = new PostDto("Title 1", "Author 1", "Content 1" );
        doReturn(post).when(service).createPost(any(PostRequest.class));

        // when
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postToCreateAsJson))
                // then
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "/api/posts/" + post.getId()));
    }

    @Test
    @DisplayName("should delete post and return 200")
    void testDeletePost() throws Exception {
        // given
        Long postId = 1L;
        doReturn(true).when(service).deletePost(postId);

        // when
        mockMvc.perform(delete("/api/posts/{postId}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk());
    }


}

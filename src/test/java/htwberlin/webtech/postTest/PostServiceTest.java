package htwberlin.webtech.postTest;

import htwberlin.webtech.post.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository repo;

    @InjectMocks
    private PostService underTest;

    @Test
    @DisplayName("should return all posts")
    void testGetAllPosts() {
        //given
        List<PostDto> posts = Collections.emptyList();
        doReturn(posts).when(repo).findAll();
        //when
        List<PostDto> result = underTest.getAll();

        //then
        verify(repo).findAll();
        assertThat(result).isEqualTo(posts);
    }

    @Test
    @DisplayName("should return submission by id")
    void findPostById() {
        Long givenId = 1L;
        Post post = new Post();
        doReturn(Optional.of(post)).when(repo).findById(givenId);

        // when
        PostDto result = underTest.findPostById(givenId);

        // then
        verify(repo).findById(givenId);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("should return null for non-existing post by ID")
    void testFindNonExistingPostById() {
        // given
        Long givenId = 1L;
        doReturn(Optional.empty()).when(repo).findById(givenId);

        // when
        PostDto result = underTest.findPostById(givenId);

        // then
        verify(repo).findById(givenId);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("should return post by title as PostDto")
    void testFindPostByTitle() {
        // given
        String givenTitle = "Sample Title";
        Post post = new Post();
        doReturn(post).when(repo).findByTitle(givenTitle);

        // when
        PostDto result = underTest.findPostByTitle(givenTitle);

        // then
        verify(repo).findByTitle(givenTitle);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("should create and return a new post as PostDto")
    void testCreatePost() {
        // given
        PostRequest request = new PostRequest("Sample Title", "Sample Author", "Sample Content");
        Post createdPost = new Post();
        doReturn(createdPost).when(repo).save(any());

        // when
        PostDto result = underTest.createPost(request);

        // then
        verify(repo).save(any());
        assertThat(result).isEqualTo(PostDto.toPostDto(createdPost));
    }

    @Test
    @DisplayName("should update and return the updated post as PostDto")
    void testUpdatePost() {
        // given
        Long givenId = 1L;
        PostRequest request = new PostRequest("Updated Title", "Updated Author", "Updated Content");
        Post existingPost = new Post();
        doReturn(Optional.of(existingPost)).when(repo).findById(givenId);
        doReturn(new Post()).when(repo).save(any());

        // when
        PostDto result = underTest.updatePost(givenId, request);

        // then
        verify(repo).findById(givenId);
        verify(repo).save(any());
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("should return null for non-existing post when updating")
    void testUpdateNonExistingPost() {
        // given
        Long givenId = 1L;
        PostRequest request = new PostRequest("Updated Title", "Updated Author", "Updated Content");
        doReturn(Optional.empty()).when(repo).findById(givenId);

        // when
        PostDto result = underTest.updatePost(givenId, request);

        // then
        verify(repo).findById(givenId);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("should delete post by ID and return true")
    void testSuccessfulDeletePost() {
        // given
        Long givenId = 1L;
        doReturn(true).when(repo).existsById(givenId);

        // when
        boolean result = underTest.deletePost(givenId);

        // then
        verify(repo).existsById(givenId);
        verify(repo).deleteById(givenId);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("should return false if post to delete does not exist")
    void testUnsuccessfulDeletePost() {
        // given
        Long givenId = 1L;
        doReturn(false).when(repo).existsById(givenId);

        // when
        boolean result = underTest.deletePost(givenId);

        // then
        verify(repo).existsById(givenId);
        assertThat(result).isFalse();
    }

}

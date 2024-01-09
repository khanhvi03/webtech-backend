package htwberlin.webtech.postTest;

import htwberlin.webtech.post.Post;
import htwberlin.webtech.post.PostDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class PostDtoTest {

    @Test
    @DisplayName("should transform Post to PostDto")
    void testToPostDto() {
        // given
        var post = new Post();
        post.setId(1L);
        post.setTitle("Sample Title");
        post.setAuthor("Sample Author");
        post.setContent("Sample Content");
        post.setPublishedOn(LocalDateTime.now());
        post.setUpdatedOn(LocalDateTime.now());

        // when
        var result = PostDto.toPostDto(post);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Sample Title");
        assertThat(result.getAuthor()).isEqualTo("Sample Author");
        assertThat(result.getContent()).isEqualTo("Sample Content");
        assertThat(result.getPublishedOn()).isEqualTo(post.getPublishedOn());
        assertThat(result.getUpdatedOn()).isEqualTo(post.getUpdatedOn());
    }

    @Test
    @DisplayName("should transform PostDto to Post")
    void testToPost() {
        // given
        var postDto = PostDto.builder()
                .id(1L)
                .title("Sample Title")
                .author("Sample Author")
                .content("Sample Content")
                .publishedOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        // when
        var result = PostDto.toPost(postDto);

        // then
        assertThat(result.getTitle()).isEqualTo("Sample Title");
        assertThat(result.getAuthor()).isEqualTo("Sample Author");
        assertThat(result.getContent()).isEqualTo("Sample Content");
        assertThat(result.getPublishedOn()).isEqualTo(postDto.getPublishedOn());
        assertThat(result.getUpdatedOn()).isEqualTo(postDto.getUpdatedOn());
    }

}

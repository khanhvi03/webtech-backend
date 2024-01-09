package htwberlin.webtech.post;

import java.time.LocalDateTime;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Builder
public class PostDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private LocalDateTime publishedOn;
    private LocalDateTime updatedOn;
    private String admin;

    public PostDto(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.publishedOn = LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
    }

    public static PostDto toPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getAuthor())
                .content(post.getContent())
                .publishedOn(post.getPublishedOn())
                .updatedOn(post.getUpdatedOn())
                .admin(post.getAdmin())
                .build();
    }

    public static Post toPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setAuthor(postDto.getAuthor());
        post.setContent(postDto.getContent());
        post.setPublishedOn(postDto.getPublishedOn());
        post.setUpdatedOn(postDto.getUpdatedOn());
        post.setAdmin(postDto.getAdmin());
        return post;
    }
}

package htwberlin.webtech.post;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    private String content;
    private LocalDateTime publishedOn;
    private LocalDateTime updatedOn;


    public PostRequest(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
        publishedOn = LocalDateTime.now();
        updatedOn = LocalDateTime.now();
    }
}

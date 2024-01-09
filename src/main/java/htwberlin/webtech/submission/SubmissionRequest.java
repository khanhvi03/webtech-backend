package htwberlin.webtech.submission;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


import java.time.LocalDateTime;
@Data
public class SubmissionRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private LocalDateTime submitOn;

    public SubmissionRequest(String name, String email, String title, String content) {
        this.name = name;
        this.email = email;
        this.title = title;
        this.content = content;
        submitOn = LocalDateTime.now();
    }
}

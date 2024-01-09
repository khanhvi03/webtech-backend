package htwberlin.webtech.post;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;


@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId) {
        var result = postService.findPostById(postId);
        return result != null? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<Void> createPost(@Valid @RequestBody PostRequest request) throws URISyntaxException {
        var result = postService.createPost(request);
        URI uri = new URI("/api/posts/" + result.getId());
        return ResponseEntity
                .created(uri)
                .header("Access-Control-Expose-Headers", "Location")
                .build();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostRequest request) {
        var result = postService.updatePost(postId,request);
        return result !=null? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        var successful = postService.deletePost(postId);
        return successful? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }


}

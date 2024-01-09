package htwberlin.webtech.submission;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/submission")
@CrossOrigin(origins = "http://localhost:5173")
public class SubmissionController {

    private final SubmissionService service;

    public SubmissionController(SubmissionService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<?> findAllSubmissions(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{sid}")
    public ResponseEntity<?> findSubmissionById(@PathVariable Long sid) {
        var result = service.findSubmissionById(sid);
        return result != null? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @GetMapping("/find/title")
    public ResponseEntity<List<Submission>> findSubmissionByTitle(@RequestParam String title) {
        var result = service.findSubmissionByTitle(title);
        return result != null? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @GetMapping("/find/author")
    public ResponseEntity<List<Submission>> findSubmissionByAuthor(@RequestParam String author) {
        var result = service.findSubmissionByAuthor(author);
        return result != null? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Void> submit(@Valid @RequestBody SubmissionRequest request) throws URISyntaxException {
        var result = service.createSubmission(request);
        URI uri = new URI("/submission/" + result.getId());
        return ResponseEntity
                .created(uri)
                .header("Access-Control-Expose-Headers", "Location")
                .build();
    }

    @DeleteMapping("/{sid}")
    public ResponseEntity<?> deleteSubmission(@PathVariable Long sid) {
        var successful = service.deleteSubmission(sid);
        return successful? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}

package htwberlin.webtech.submission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepo repo;


    public List<Submission> getAll(){
        return repo.findAll();
    }

    public Submission findSubmissionById (Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<Submission> findSubmissionByTitle(String title){
        return repo.findSubmissionByTitle(title);
    }

    public List<Submission> findSubmissionByAuthor(String author){
        List<Submission> submissions = repo.findSubmissionByName(author);
        return submissions.isEmpty() ? null : submissions;
    }

    public Submission createSubmission(SubmissionRequest request) {
        Submission submit = new Submission(
                request.getName(),
                request.getEmail(),
                request.getTitle(),
                request.getContent()
        );
        return repo.save(submit);
    }

    public boolean deleteSubmission(Long id) {
        if(!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}

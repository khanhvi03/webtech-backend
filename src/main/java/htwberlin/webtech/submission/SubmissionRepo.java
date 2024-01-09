package htwberlin.webtech.submission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepo extends JpaRepository<Submission, Long> {

    List<Submission> findSubmissionByTitle(String title);
    List<Submission> findSubmissionByName(String name);
}

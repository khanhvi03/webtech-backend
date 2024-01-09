package htwberlin.webtech.submissionTest;

import htwberlin.webtech.submission.Submission;
import htwberlin.webtech.submission.SubmissionRepo;
import htwberlin.webtech.submission.SubmissionRequest;
import htwberlin.webtech.submission.SubmissionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubmissionServiceTest {

    @Mock
    private SubmissionRepo repo;

    @InjectMocks
    private SubmissionService underTest;

    @Test
    @DisplayName("should return all submissions")
    void testGetAllSubmissions() {
        // given
        List<Submission> submissions = Collections.emptyList();
        doReturn(submissions).when(repo).findAll();

        // when
        List<Submission> result = underTest.getAll();

        // then
        verify(repo).findAll();
        assertThat(result).isEqualTo(submissions);
    }

    @Test
    @DisplayName("should return submission by id")
    void findSubmissionById() {
        // given
        Long givenId = 111L;
        Submission submission = new Submission();
        doReturn(Optional.of(submission)).when(repo).findById(givenId);

        // when
        Submission result = underTest.findSubmissionById(givenId);

        // then
        verify(repo).findById(givenId);
        assertThat(result).isEqualTo(submission);
    }

    @Test
    @DisplayName("should return submission by title")
    void testFindSubmissionByTitle() {
        // given
        String givenTitle = "Sample Title";
        List<Submission> submissions = new ArrayList<>();
        doReturn(submissions).when(repo).findSubmissionByTitle(givenTitle);

        // when
        List<Submission> result = underTest.findSubmissionByTitle(givenTitle);

        // then
        verify(repo).findSubmissionByTitle(givenTitle);
        assertThat(result).isEqualTo(submissions);
    }

    @Test
    @DisplayName("should return null if submissions by author are empty")
    void testFindSubmissionsByAuthor() {
        // given
        String givenAuthor = "Sample Author";
        List<Submission> submissions = Collections.emptyList();
        doReturn(submissions).when(repo).findSubmissionByName(givenAuthor);

        // when
        List<Submission> result = underTest.findSubmissionByAuthor(givenAuthor);

        // then
        verify(repo).findSubmissionByName(givenAuthor);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("should return created submission")
    void testCreateSubmission() {
        // given
        SubmissionRequest request = new SubmissionRequest("John Doe", "john@example.com", "Sample Title", "Sample Content");
        Submission createdSubmission = new Submission();
        doReturn(createdSubmission).when(repo).save(any());

        // when
        Submission result = underTest.createSubmission(request);

        // then
        verify(repo).save(any());
        assertThat(result).isEqualTo(createdSubmission);
    }

    @Test
    @DisplayName("should return true if delete was successful")
    void testSuccessfulDelete() {
        // given
        Long givenId = 111L;
        doReturn(true).when(repo).existsById(givenId);

        // when
        boolean result = underTest.deleteSubmission(givenId);

        // then
        verify(repo).existsById(givenId);
        verify(repo).deleteById(givenId);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("should return false if submission to delete does not exist")
    void testDeleteNotExistedSubmission() {
        // given
        Long givenId = 111L;
        doReturn(false).when(repo).existsById(givenId);

        // when
        boolean result = underTest.deleteSubmission(givenId);

        // then
        verify(repo).existsById(givenId);
        verifyNoMoreInteractions(repo);
        assertThat(result).isFalse();
    }

}

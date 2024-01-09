package htwberlin.webtech.submissionTest;

import htwberlin.webtech.submission.Submission;
import htwberlin.webtech.submission.SubmissionController;
import htwberlin.webtech.submission.SubmissionRequest;
import htwberlin.webtech.submission.SubmissionService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubmissionController.class)
public class SubmissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubmissionService service;

    @Test
    @DisplayName("should return found submissions from submission service")
    void testFindAllFoundSubmission() throws Exception {
        // given
        var submissions = List.of(
                new Submission("John Doe", "john@example.com", "Title 1", "Content 1"),
                new Submission("Jane Smith", "jane@example.com", "Title 2", "Content 2")
        );
        doReturn(submissions).when(service).getAll();

        // when
        mockMvc.perform(get("/submission"))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"))
                .andExpect(jsonPath("$[0].title").value("Title 1"))
                .andExpect(jsonPath("$[0].content").value("Content 1"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"))
                .andExpect(jsonPath("$[1].email").value("jane@example.com"))
                .andExpect(jsonPath("$[1].title").value("Title 2"))
                .andExpect(jsonPath("$[1].content").value("Content 2"));
    }

    @Test
    @DisplayName("should return 404 if submission is not found")
    void testFindByIdForNotFoundSubmission() throws Exception{
        //given
        doReturn(null).when(service).findSubmissionById(any());

        //when
        mockMvc.perform(get("/submission/123"))
            //then
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should return 201 http status and Location header when creating a submission")
    void testSubmit() throws Exception {
        // given
        String submissionToCreateAsJson = "{\"name\": \"John Doe\", \"email\":\"john@example.com\", \"title\":\"Title 1\", \"content\":\"Content 1\"}";
        var submission = new Submission("John Doe", "john@example.com", "Title 1", "Content 1");
        doReturn(submission).when(service).createSubmission(any(SubmissionRequest.class));

        // when
        mockMvc.perform(
                        post("/submission/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(submissionToCreateAsJson)
                )
                // then
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "/submission/" + submission.getId()));
    }

    @Test
    @DisplayName("should validate create submission request")
    void testValidateCreateRequest() throws Exception {
        // given
        String submissionToCreateAsJson = "{\"name\": \"a\", \"email\":\"\", \"title\":\"Title 1\", \"content\":\"Content 1\"}";

        // when
        mockMvc.perform(
                        post("/submission/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(submissionToCreateAsJson)
                )
                // then
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return found submissions by author from submission service")
    void testFindSubmissionsByAuthor() throws Exception {
        // given
        String authorToFind = "John Doe";
        var submissions = List.of(
                new Submission("John Doe", "john@example.com", "Title 1", "Content 1"),
                new Submission("John Doe", "jane@example.com", "Title 2", "Content 2")
        );
        doReturn(submissions).when(service).findSubmissionByAuthor(authorToFind);

        // when
        mockMvc.perform(get("/submission/find/author")
                        .param("author", authorToFind))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"))
                .andExpect(jsonPath("$[0].title").value("Title 1"))
                .andExpect(jsonPath("$[0].content").value("Content 1"))
                .andExpect(jsonPath("$[1].name").value("John Doe"))
                .andExpect(jsonPath("$[1].email").value("jane@example.com"))
                .andExpect(jsonPath("$[1].title").value("Title 2"))
                .andExpect(jsonPath("$[1].content").value("Content 2"));
    }

    @Test
    @DisplayName("should return 404 if submissions by author are not found")
    void testSubmissionByNotFoundAuthor() throws Exception {
        // given
        String authorToFind = "Non-existent Author";
        doReturn(null).when(service).findSubmissionByAuthor(authorToFind);

        // when
        mockMvc.perform(get("/submission/find/author")
                        .param("author", authorToFind))
                // then
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should delete submission and return 200")
    void testDeleteSubmission() throws Exception {
        // given
        long submissionId = 1L;
        doReturn(true).when(service).deleteSubmission(submissionId);

        // when
        mockMvc.perform(delete("/submission/{sid}", submissionId)
                                .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk());
    }


}


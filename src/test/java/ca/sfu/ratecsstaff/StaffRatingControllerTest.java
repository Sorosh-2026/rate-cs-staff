package ca.sfu.ratecsstaff;

import ca.sfu.ratecsstaff.controller.StaffRatingController;
import ca.sfu.ratecsstaff.repository.StaffRatingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StaffRatingController.class)
class StaffRatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StaffRatingRepository repository;

    @Test
    void testIndexPageReturns200AndModelAttributes() throws Exception {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("ratings"));
    }

    @Test
    void testCreateSuccessRedirects() throws Exception {
        StaffRating savedRating = new StaffRating();
        savedRating.setId(1L);
        when(repository.save(any(StaffRating.class))).thenReturn(savedRating);
        when(repository.existsByEmail(any())).thenReturn(false);

        mockMvc.perform(post("/ratings")
                .param("name", "John Doe")
                .param("email", "john@sfu.ca")
                .param("roleType", "TA")
                .param("clarity", "8")
                .param("niceness", "9")
                .param("knowledgeableScore", "7"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ratings/1"));
    }

    @Test
    void testCreateFailureReturnsFormWithErrors() throws Exception {
        // Missing name and invalid email should fail validation
        mockMvc.perform(post("/ratings")
                .param("name", "")
                .param("email", "bad-email")
                .param("roleType", "TA")
                .param("clarity", "12") // invalid score
                .param("niceness", "9")
                .param("knowledgeableScore", "7"))
                .andExpect(status().isOk())
                .andExpect(view().name("form"))
                .andExpect(model().attributeHasFieldErrors("rating", "name", "email", "clarity"));
    }
}
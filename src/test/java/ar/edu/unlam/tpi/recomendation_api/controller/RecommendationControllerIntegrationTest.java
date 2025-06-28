package ar.edu.unlam.tpi.recomendation_api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import ar.edu.unlam.tpi.recomendation_api.dto.request.RecommendationRequest;
import ar.edu.unlam.tpi.recomendation_api.dto.request.TagRequest;
import ar.edu.unlam.tpi.recomendation_api.models.RecommendationEntity;
import ar.edu.unlam.tpi.recomendation_api.persistence.dao.RecommendationDAO;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RecommendationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RecommendationDAO recommendationDAO;

    @Test
    void givenValidRequest_whenPostRecommendation_thenReturnsCreated() throws Exception {
        RecommendationRequest request = RecommendationRequest.builder()
                .applicantId(1L)
                .tags(List.of(
                        TagRequest.builder().probability(0.8).tagName("wall_repair").build()))
                .build();

        mockMvc.perform(post("/recomendation-api/v1/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void givenRecommendations_whenGetRecommendations_thenReturnsRecommendations() throws Exception {

        RecommendationEntity entity = RecommendationEntity.builder()
                .applicantId(42L)
                .tag("wall_repair")
                .probability(0.85)
                .createdAt(LocalDateTime.now())
                .build();

        recommendationDAO.save(entity);

        mockMvc.perform(get("/recomendation-api/v1/recommendations")
                .param("applicantId", "42")
                .param("limit", "3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].applicantId").value(42))
                .andExpect(jsonPath("$.data[0].label").value("wall_repair"))
                .andExpect(jsonPath("$.data[0].category").value("CONTRACTOR")); // depende de config
    }

}

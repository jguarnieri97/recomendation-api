package ar.edu.unlam.tpi.recomendation_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import ar.edu.unlam.tpi.recomendation_api.Utils.RecommendationDataHelper;
import ar.edu.unlam.tpi.recomendation_api.persistence.dao.RecommendationDAO;
import ar.edu.unlam.tpi.recomendation_api.utils.CategoryConfig;
import ar.edu.unlam.tpi.recomendation_api.utils.RecommendationConverter;
import ar.edu.unlam.tpi.recomendation_api.service.impl.RecommendationServiceImpl;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceImplTest {

    @Mock
    private RecommendationDAO dao;

    @Mock
    private CategoryConfig config;

    @Mock
    private RecommendationConverter converter;

    @InjectMocks
    private RecommendationServiceImpl service;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(service, "threshold", 0.7);
    }

    @Test
    @DisplayName("Debe guardar recomendaciones válidas que superen el threshold y tengan categoría")
    void givenValidTags_whenCreateRecommendation_thenSaveOnlyMatchingOnes() {
        // Given
        var request = RecommendationDataHelper.buildValidRequest();
        var entity = RecommendationDataHelper.buildEntity();
        when(config.getCategoryByTag("dirty space")).thenReturn(Optional.of("CLEANING"));
        when(converter.convertToEntity(1L, request.getTags().get(0))).thenReturn(entity);

        // When
        service.createRecommendation(request);

        // Then
        verify(dao).saveAll(List.of(entity));
    }

    @Test
    @DisplayName("Debe retornar lista de recomendaciones por applicantId")
    void givenApplicantId_whenGetRecommendations_thenReturnResponseList() {
        // Given
        var entity = RecommendationDataHelper.buildEntity();
        var response = RecommendationDataHelper.buildResponse();
        when(dao.findByApplicantIdOrderByCreatedAtDesc(1L, PageRequest.of(0, 3)))
            .thenReturn(List.of(entity));
        when(config.getCategoryByTag("dirty space")).thenReturn(Optional.of("CLEANING"));
        when(converter.convertToResponse(entity, "CLEANING")).thenReturn(response);

        // When
        var result = service.getRecommendations(1L, 3);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("dirty space", result.get(0).getLabel());
        verify(dao).findByApplicantIdOrderByCreatedAtDesc(1L, PageRequest.of(0, 3));
    }
}

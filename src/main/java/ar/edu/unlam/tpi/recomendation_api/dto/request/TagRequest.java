package ar.edu.unlam.tpi.recomendation_api.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagRequest {
    private double probability;
    private String tagName;
}
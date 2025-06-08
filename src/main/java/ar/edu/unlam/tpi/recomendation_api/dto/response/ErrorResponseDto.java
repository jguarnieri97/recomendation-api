package ar.edu.unlam.tpi.recomendation_api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {
    private Integer code;
    private String message;
    private String detail;
}


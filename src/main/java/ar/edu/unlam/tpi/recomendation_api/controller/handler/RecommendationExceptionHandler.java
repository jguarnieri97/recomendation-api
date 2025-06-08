package ar.edu.unlam.tpi.recomendation_api.controller.handler;

import ar.edu.unlam.tpi.recomendation_api.dto.response.ErrorResponseDto;
import ar.edu.unlam.tpi.recomendation_api.exceptions.InternalException;
import ar.edu.unlam.tpi.recomendation_api.exceptions.NotFoundException;
import ar.edu.unlam.tpi.recomendation_api.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RecommendationExceptionHandler {

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<ErrorResponseDto> handleInternalException(InternalException ex) {
        return ResponseEntity
                .status(ex.getCode())
                .body(ErrorResponseDto.builder()
                        .code(ex.getCode())
                        .message(ex.getMessage())
                        .detail(ex.getDetail())
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity
                .status(ex.getCode())
                .body(ErrorResponseDto.builder()
                        .code(ex.getCode())
                        .message(ex.getMessage())
                        .detail(ex.getDetail())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity
                .status(Constants.STATUS_INTERNAL)
                .body(ErrorResponseDto.builder()
                        .code(Constants.STATUS_INTERNAL)
                        .message(Constants.INTERNAL_ERROR)
                        .detail(errors.toString())
                        .build());
    }
}
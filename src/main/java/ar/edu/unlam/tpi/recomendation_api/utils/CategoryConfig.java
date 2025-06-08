package ar.edu.unlam.tpi.recomendation_api.utils;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CategoryConfig {

    private final Map<String, List<String>> categories = new HashMap<>();

    public CategoryConfig() {
        loadCategoriesFromJson();
    }

    private void loadCategoriesFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.json")) {
            if (is == null) throw new FileNotFoundException("config.json not found in resources");

            TypeReference<Map<String, List<String>>> typeRef = new TypeReference<>() {};
            Map<String, List<String>> loaded = mapper.readValue(is, typeRef);
            categories.putAll(loaded);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load categories from config.json", e);
        }
    }

    public Optional<String> getCategoryByTag(String tag) {
        return categories.entrySet().stream()
                .filter(e -> e.getValue().contains(tag))
                .map(Map.Entry::getKey)
                .findFirst();
    }
}

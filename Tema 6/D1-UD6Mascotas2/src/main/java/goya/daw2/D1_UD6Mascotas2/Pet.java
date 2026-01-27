package goya.daw2.D1_UD6Mascotas2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Pet(Long id, String type, Double price) {
}
package hexagonal.link.port.dto;

import jakarta.validation.constraints.NotBlank;

public record LinkInput(
        @NotBlank
        String url,
        String slug) {
}

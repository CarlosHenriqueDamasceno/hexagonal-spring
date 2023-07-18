package hexagonal.link.port.dto;

import jakarta.validation.constraints.NotBlank;

public record LinkInput(
        @NotBlank(message = "A URL é obrigatória")
        String url,
        String slug) {
}

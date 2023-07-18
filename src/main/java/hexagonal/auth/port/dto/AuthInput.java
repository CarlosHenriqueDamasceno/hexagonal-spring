package hexagonal.auth.port.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthInput(
        @NotBlank(message = "O E-mail é obrigatório.")
        String email,
        @NotBlank(message = "A senha é obrigatória.")
        String password) {
}

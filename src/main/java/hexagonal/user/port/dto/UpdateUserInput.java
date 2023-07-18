package hexagonal.user.port.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserInput(
        @NotBlank(message = "O nome é obrigatório.")
        String name,
        @NotBlank(message = "O E-mail é obrigatório.")
        @Email(message = "O E-mail fornecido não é válido.")
        String email) {
}
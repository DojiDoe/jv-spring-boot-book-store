package mate.academy.bookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryDto(
        @NotBlank
        @NotNull
        String name,
        String description
) {
}

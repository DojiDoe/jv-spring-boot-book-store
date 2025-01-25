package mate.academy.bookstore.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateCartItemRequestDto(
        @NotNull
        Long bookId,
        @Min(1)
        @NotNull
        Integer quantity
) {
}

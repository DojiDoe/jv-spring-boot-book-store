package mate.academy.bookstore.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateShoppingCartItemRequestDto(
        @Min(1)
        @NotNull
        int quantity
) {
}

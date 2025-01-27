package mate.academy.bookstore.dto.cartitem;

import jakarta.validation.constraints.Positive;

public record UpdateShoppingCartItemRequestDto(
        @Positive
        int quantity
) {
}

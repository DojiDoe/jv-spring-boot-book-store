package mate.academy.bookstore.dto.cartitem;

public record CreateCartItemRequestDto(Long bookId, Integer quantity) {
}

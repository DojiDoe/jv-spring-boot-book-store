package mate.academy.bookstore.repository;

import mate.academy.bookstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByIdAndShoppingCartId(Long cartItemId, Long id);
}

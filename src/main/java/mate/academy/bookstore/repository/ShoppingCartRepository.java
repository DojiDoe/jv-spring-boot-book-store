package mate.academy.bookstore.repository;

import mate.academy.bookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    @Query("SELECT s FROM ShoppingCart s "
            + "LEFT JOIN FETCH s.user u "
            + "LEFT JOIN FETCH s.cartItems items "
            + "LEFT JOIN FETCH items.book "
            + "WHERE u.id = :userId")
    ShoppingCart findByUserId(@Param("userId") Long userId);
}

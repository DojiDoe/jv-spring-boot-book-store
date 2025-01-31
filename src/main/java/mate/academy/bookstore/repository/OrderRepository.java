package mate.academy.bookstore.repository;

import java.util.List;
import mate.academy.bookstore.model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"user", "orderItems", "orderItems.book"})
    List<Order> findAllByUserId(Long userId);

    @EntityGraph(attributePaths = {"user", "orderItems", "orderItems.book"})
    Order findByIdAndUserId(Long orderId, Long userId);
}

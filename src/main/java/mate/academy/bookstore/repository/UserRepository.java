package mate.academy.bookstore.repository;

import java.util.Optional;
import mate.academy.bookstore.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByEmail(String email);
}

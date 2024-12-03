package mate.academy.bookstore.repository.book;

import java.util.List;
import java.util.Optional;
import mate.academy.bookstore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @EntityGraph(attributePaths = {"categories"})
    Page<Book> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"categories"})
    Page<Book> findAll(Specification<Book> bookSpecification, Pageable pageable);

    @EntityGraph(attributePaths = {"categories"})
    Optional<Book> findById(Long id);

    @Query("SELECT b FROM Book b JOIN FETCH b.categories c WHERE c.id = :categoryId")
    List<Book> findAllByCategoryId(@Param("categoryId") Long categoryId);

}

package mate.academy.bookstore.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.bookstore.model.Book;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> getById(Long id);

    List<Book> findAll();
}

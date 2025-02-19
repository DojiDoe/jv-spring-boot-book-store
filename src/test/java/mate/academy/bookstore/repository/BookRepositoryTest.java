package mate.academy.bookstore.repository;

import java.util.List;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.repository.book.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("")
    @Sql(scripts = {
        "classpath:database/categories/insert-categories.sql",
        "classpath:database/books/insert-books.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/clean-up-data.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAll_ValidPageable_ShouldReturnListOfBook() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> actual = bookRepository.findAll(pageable).stream().toList();
        Assertions.assertEquals(3, actual.size());
        Assertions.assertEquals("1984", actual.get(0).getTitle());
        Assertions.assertEquals("978-0-596-52069-4", actual.get(1).getIsbn());
    }

}

package mate.academy.bookstore;

import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class BookStoreApplication {

    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("1984");
            book.setAuthor("Orvel");
            book.setIsbn("Something");
            book.setPrice(BigDecimal.valueOf(240));
            book.setDescription("Very cool");
            book.setCoverImage("Cover image fro 1984");

            bookService.save(book);
            System.out.println(bookService.findAll());
        };
    }

}

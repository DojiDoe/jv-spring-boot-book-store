package mate.academy.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.BookDto;
import mate.academy.bookstore.dto.BookSearchParametersDto;
import mate.academy.bookstore.dto.CreateBookRequestDto;
import mate.academy.bookstore.dto.UpdateBookRequestDto;
import mate.academy.bookstore.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "Endpoints for managing products")
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all books", description = "Get a list of all books")
    public List<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "get books by params", description = "Get a list of all"
            + " books with sent params")
    public List<BookDto> searchBooks(BookSearchParametersDto params, Pageable pageable) {
        return bookService.search(params,pageable);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    @Operation(summary = "Delete a book")
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new book")
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto book) {
        return bookService.save(book);
    }

    @PutMapping
    @Operation(summary = "update a book")
    public BookDto updateBook(@RequestBody UpdateBookRequestDto book) {
        return bookService.update(book);
    }
}

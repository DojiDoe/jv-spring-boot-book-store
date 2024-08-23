package mate.academy.bookstore.service;

import java.util.List;
import mate.academy.bookstore.dto.BookDto;
import mate.academy.bookstore.dto.BookSearchParametersDto;
import mate.academy.bookstore.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    BookDto getById(Long id);

    void deleteById(Long id);

    List<BookDto> findAll();

    public List<BookDto> search(BookSearchParametersDto params);
}

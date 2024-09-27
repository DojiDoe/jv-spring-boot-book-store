package mate.academy.bookstore.service;

import java.util.List;
import mate.academy.bookstore.dto.BookDto;
import mate.academy.bookstore.dto.BookSearchParametersDto;
import mate.academy.bookstore.dto.CreateBookRequestDto;
import mate.academy.bookstore.dto.UpdateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    BookDto getById(Long id);

    void deleteById(Long id);

    List<BookDto> findAll();

    List<BookDto> search(BookSearchParametersDto params);

    BookDto update(UpdateBookRequestDto book);
}

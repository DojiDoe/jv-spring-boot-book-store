package mate.academy.bookstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import mate.academy.bookstore.dto.book.BookDto;
import mate.academy.bookstore.dto.book.CreateBookRequestDto;
import mate.academy.bookstore.dto.book.UpdateBookRequestDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.BookMapper;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.model.Category;
import mate.academy.bookstore.repository.book.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Verify whether BookDto is valid when calling save() method")
    public void save_ValidCreateBookRequestDto_ReturnsBookDto() {
        // Given
        CreateBookRequestDto requestDto = CreateBookRequestDto.builder()
                .title("1984")
                .author("George Orwell")
                .isbn("978-0-596-52068-7")
                .price(BigDecimal.valueOf(9.99))
                .description("Dystopian vision of the future")
                .coverImage("Cover image")
                .categoryIds(List.of(1L))
                .build();
        Book book = Book.builder()
                .id(1L)
                .title(requestDto.getTitle())
                .author(requestDto.getAuthor())
                .isbn(requestDto.getIsbn())
                .price(requestDto.getPrice())
                .description(requestDto.getDescription())
                .coverImage(requestDto.getCoverImage())
                .categories(Set.of(new Category()))
                .build();
        BookDto bookDto = BookDto.builder()
                .id(1L)
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .description(book.getDescription())
                .coverImage(book.getCoverImage())
                .categoryIds(List.of(1L))
                .build();
        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);
        // When
        BookDto savedBookDto = bookService.save(requestDto);
        //Then
        assertThat(savedBookDto).isEqualTo(bookDto);
        verify(bookRepository, times(1)).save(book);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    @DisplayName("Verify whether BookDto is returned when calling "
            + "getById() method with valid id")
    public void getBookDto_ValidBookId_ShouldReturnValidBookDto() {
        //Given
        BookDto bookDto = BookDto.builder()
                .id(1L)
                .title("1984")
                .author("George Orwell")
                .isbn("978-0-596-52068-7")
                .price(BigDecimal.valueOf(9.99))
                .description("Dystopian vision of the future")
                .coverImage("Cover image of eye")
                .categoryIds(List.of(1L))
                .build();
        Book book = Book.builder()
                .id(1L)
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .isbn(bookDto.getIsbn())
                .price(bookDto.getPrice())
                .description(bookDto.getDescription())
                .coverImage(bookDto.getCoverImage())
                .categories(Set.of(new Category()))
                .build();
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);
        //When
        BookDto actual = bookService.getById(1L);
        //Then
        assertThat(actual).isEqualTo(bookDto);
        verify(bookRepository, Mockito.times(1)).findById(1L);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    @DisplayName("Verify whether EntityNotFoundException is thrown when calling"
            + " getById() method with invalid id")
    public void getBookDto_InvalidBookId_ShouldThrowEntityNotFoundException() {
        //Given
        Long invalidId = 100L;
        Mockito.when(bookRepository.findById(invalidId)).thenReturn(Optional.empty());
        //When
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.getById(invalidId)
        );
        // Then
        String expected = "Can't get book by id " + invalidId;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
        verify(bookRepository, times(1)).findById(invalidId);
        verifyNoMoreInteractions(bookRepository);

    }

    @Test
    @DisplayName("Verify whether book is deleted when calling "
            + "deleteById() method with valid id")
    public void deleteBook_ValidBookId_ShouldDeleteBook() {
        // Given
        Long bookId = 1L;
        // When
        bookService.deleteById(bookId);
        // Then
        verify(bookRepository, times(1)).deleteById(bookId);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("Verify whether list of books is returned when calling "
            + "findAll() method with valid id")
    public void findAll_ValidPageable_ShouldReturnListOfBookDto() {
        //Given
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> booksFromDB = Arrays.asList(
                Book.builder()
                        .id(1L)
                        .title("1984")
                        .author("George Orwell")
                        .isbn("978-0-596-52068-7")
                        .price(BigDecimal.valueOf(9.99))
                        .description("Dystopian vision of the future")
                        .coverImage("Cover image of eye")
                        .categories(Set.of(new Category()))
                        .build(),
                Book.builder()
                        .id(2L)
                        .title("Animal Farm")
                        .author("George Orwell")
                        .isbn("978-0-596-52069-4")
                        .price(BigDecimal.valueOf(6.99))
                        .description("Dystopian vision of the future for kids")
                        .coverImage("Cover image of a pig")
                        .categories(Set.of(new Category()))
                        .build());
        when(bookRepository.findAll(pageable)).thenReturn(new PageImpl<>(booksFromDB));
        when(bookMapper.toDto(any(Book.class))).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            return BookDto.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .isbn(book.getIsbn())
                    .price(book.getPrice())
                    .description(book.getDescription())
                    .coverImage(book.getCoverImage())
                    .categoryIds(book.getCategories()
                            .stream()
                            .map(Category::getId)
                            .collect(Collectors.toList()))
                    .build();
        });
        //When
        List<BookDto> resultsDto = bookService.findAll(pageable);
        //Then
        assertThat(resultsDto).hasSize(2);
        assertThat(resultsDto.get(0).getTitle()).isEqualTo(booksFromDB.get(0).getTitle());
        assertThat(resultsDto.get(1).getIsbn()).isEqualTo(booksFromDB.get(1).getIsbn());
        verify(bookRepository, times(1)).findAll(pageable);
        verify(bookMapper, times(resultsDto.size())).toDto(any(Book.class));
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    @DisplayName("Verify whether EntityNotFoundException is thrown when calling "
            + "update() method with invalid id")
    public void updateBook_InvalidUpdateRequestDto_ShouldThrowEntityNotFoundException() {
        // Given
        Long invalidId = 100L;
        UpdateBookRequestDto requestDto = UpdateBookRequestDto.builder()
                .id(invalidId)
                .title("A song of ice and fire")
                .author("George R. R. Martin")
                .isbn("978-0-596-52070-0")
                .price(BigDecimal.valueOf(20.99))
                .description("6 book of series")
                .coverImage("Cover image of fire")
                .categoryIds(List.of(1L))
                .build();
        Mockito.when(bookRepository.findById(invalidId)).thenReturn(Optional.empty());
        // When
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.update(requestDto)
        );
        // Then
        String expected = "Can't get book by id " + invalidId;
        String actual = exception.getMessage();
        assertThat(actual).isEqualTo(expected);
        verify(bookRepository, times(1)).findById(invalidId);
        verifyNoMoreInteractions(bookRepository);
    }
}

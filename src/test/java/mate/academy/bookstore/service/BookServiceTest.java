package mate.academy.bookstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import mate.academy.bookstore.dto.book.BookDto;
import mate.academy.bookstore.dto.book.CreateBookRequestDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.BookMapper;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.repository.book.BookRepository;
import mate.academy.bookstore.util.BookTestUtil;
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
        CreateBookRequestDto requestDto = BookTestUtil.createBookRequestDtoValue();
        Book book = BookTestUtil.getBookFromCreateBookRequestDto(requestDto);
        BookDto bookDto = BookTestUtil.getBookDtoFromBook(book);
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
        BookDto bookDto = BookTestUtil.createBookDtoValue();
        Book book = BookTestUtil.getBookFromBookDto(bookDto);
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
        List<Book> booksFromDB = BookTestUtil.createListOfBookValues();
        when(bookRepository.findAll(pageable)).thenReturn(new PageImpl<>(booksFromDB));
        when(bookMapper.toDto(any(Book.class))).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            return BookTestUtil.getBookDtoFromBook(book);
        });
        //When
        List<BookDto> resultsDto = bookService.findAll(pageable);
        //Then
        assertThat(resultsDto).hasSize(3);
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
        CreateBookRequestDto requestDto = BookTestUtil
                .createBookRequestDtoValue();
        Mockito.when(bookRepository.findById(invalidId)).thenReturn(Optional.empty());
        // When
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.update(invalidId, requestDto)
        );
        // Then
        String expected = "Can't get book by id " + invalidId;
        String actual = exception.getMessage();
        assertThat(actual).isEqualTo(expected);
        verify(bookRepository, times(1)).findById(invalidId);
        verifyNoMoreInteractions(bookRepository);
    }
}

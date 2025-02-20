package mate.academy.bookstore.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import mate.academy.bookstore.dto.book.BookDto;
import mate.academy.bookstore.dto.book.CreateBookRequestDto;
import mate.academy.bookstore.dto.book.UpdateBookRequestDto;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.model.Category;

public class BookTestUtil {

    private BookTestUtil() {
    }

    public static List<BookDto> createListOfBookDtoValues() {
        return Arrays.asList(
                BookDto.builder()
                        .id(1L)
                        .title("1984")
                        .author("George Orwell")
                        .isbn("978-0-596-52068-7")
                        .price(BigDecimal.valueOf(10))
                        .description("Dystopian vision of the future")
                        .coverImage("Cover image of eye")
                        .build(),
                BookDto.builder()
                        .id(2L)
                        .title("Animal Farm")
                        .author("George Orwell")
                        .isbn("978-0-596-52069-4")
                        .price(BigDecimal.valueOf(7))
                        .description("Dystopian vision of the future for kids")
                        .coverImage("Cover image of pig")
                        .build(),
                BookDto.builder()
                        .id(3L)
                        .title("I see that you are interested in darkness")
                        .author("Ilarion Pavliuk")
                        .isbn("978-0-596-52077-9")
                        .price(BigDecimal.valueOf(9))
                        .description("impenetrable human indifference and the darkness within us")
                        .coverImage("Cover image of eye")
                        .build());
    }

    public static List<Book> createListOfBookValues() {
        return Arrays.asList(
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
                        .build(),
                Book.builder()
                        .id(3L)
                        .title("I see that you are interested in darkness")
                        .author("Ilarion Pavliuk")
                        .isbn("978-0-596-52077-9")
                        .price(BigDecimal.valueOf(9.99))
                        .description("impenetrable human indifference and the darkness within us")
                        .coverImage("Cover image of eye")
                        .categories(Set.of(new Category()))
                        .build());
    }

    public static Book createBook() {
        return Book.builder()
                .id(1L)
                .title("1984")
                .author("George Orwell")
                .isbn("978-0-596-52068-7")
                .price(BigDecimal.valueOf(10))
                .description("Dystopian vision of the future")
                .coverImage("Cover image of eye")
                .categories(Set.of(new Category(1L)))
                .build();
    }

    public static CreateBookRequestDto createBookRequestDtoValue() {
        return CreateBookRequestDto.builder()
                .title("It")
                .author("Steven King")
                .isbn("978-0-596-52074-8")
                .price(BigDecimal.valueOf(12.99))
                .description("Demon clown")
                .coverImage("Cover image of clown")
                .categoryIds(List.of(1L))
                .build();
    }

    public static BookDto createBookDtoValue() {
        return BookDto.builder()
                .id(1L)
                .title("1984")
                .author("George Orwell")
                .isbn("978-0-596-52068-7")
                .price(BigDecimal.valueOf(10))
                .description("Dystopian vision of the future")
                .coverImage("Cover image of eye")
                .build();
    }

    public static UpdateBookRequestDto createInvalidUpdateRequestDtoValue(Long invalidId) {
        return UpdateBookRequestDto.builder()
                .id(invalidId)
                .title("A song of ice and fire")
                .author("George R. R. Martin")
                .isbn("978-0-596-52070-0")
                .price(BigDecimal.valueOf(20.99))
                .description("6 book of series")
                .coverImage("Cover image of fire")
                .categoryIds(List.of(1L))
                .build();
    }

    public static Book getBookFromCreateBookRequestDto(CreateBookRequestDto requestDto) {
        return Book.builder()
                .id(1L)
                .title(requestDto.getTitle())
                .author(requestDto.getAuthor())
                .isbn(requestDto.getIsbn())
                .price(requestDto.getPrice())
                .description(requestDto.getDescription())
                .coverImage(requestDto.getCoverImage())
                .categories(Set.of(new Category(1L)))
                .build();
    }

    public static BookDto getBookDtoFromBook(Book book) {
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
    }

    public static Book getBookFromBookDto(BookDto bookDto) {
        return Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .isbn(bookDto.getIsbn())
                .price(bookDto.getPrice())
                .description(bookDto.getDescription())
                .coverImage(bookDto.getCoverImage())
                .categories(Set.of(new Category()))
                .build();
    }
}

package mate.academy.bookstore.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import mate.academy.bookstore.dto.book.BookDto;
import mate.academy.bookstore.dto.book.CreateBookRequestDto;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.model.Category;

public class BookTestUtil {

    private BookTestUtil() {
    }

    public static List<BookDto> createListOfBookDtoValues() {
        return Arrays.asList(
                new BookDto()
                        .setId(1L)
                        .setTitle("1984")
                        .setAuthor("George Orwell")
                        .setIsbn("978-0-596-52068-7")
                        .setPrice(BigDecimal.valueOf(10))
                        .setDescription("Dystopian vision of the future")
                        .setCoverImage("Cover image of eye")
                        .setCategoryIds(List.of(1L)),
                new BookDto()
                        .setId(2L)
                        .setTitle("Animal Farm")
                        .setAuthor("George Orwell")
                        .setIsbn("978-0-596-52069-4")
                        .setPrice(BigDecimal.valueOf(7))
                        .setDescription("Dystopian vision of the future for kids")
                        .setCoverImage("Cover image of pig")
                        .setCategoryIds(List.of(1L)),
                new BookDto()
                        .setId(3L)
                        .setTitle("I see that you are interested in darkness")
                        .setAuthor("Ilarion Pavliuk")
                        .setIsbn("978-0-596-52077-9")
                        .setPrice(BigDecimal.valueOf(9))
                        .setDescription("impenetrable human indifference"
                                + " and the darkness within us")
                        .setCoverImage("Cover image of eye")
                        .setCategoryIds(List.of(1L)));
    }

    public static List<Book> createListOfBookValues() {
        return Arrays.asList(
                new Book()
                        .setId(1L)
                        .setTitle("1984")
                        .setAuthor("George Orwell")
                        .setIsbn("978-0-596-52068-7")
                        .setPrice(BigDecimal.valueOf(10))
                        .setDescription("Dystopian vision of the future")
                        .setCoverImage("Cover image of eye")
                        .setCategories(Set.of(new Category(1L))),
                new Book()
                        .setId(2L)
                        .setTitle("Animal Farm")
                        .setAuthor("George Orwell")
                        .setIsbn("978-0-596-52069-4")
                        .setPrice(BigDecimal.valueOf(7))
                        .setDescription("Dystopian vision of the future for kids")
                        .setCoverImage("Cover image of pig")
                        .setCategories(Set.of(new Category(1L))),
                new Book()
                        .setId(3L)
                        .setTitle("I see that you are interested in darkness")
                        .setAuthor("Ilarion Pavliuk")
                        .setIsbn("978-0-596-52077-9")
                        .setPrice(BigDecimal.valueOf(9))
                        .setDescription("impenetrable human indifference and "
                                + "the darkness within us")
                        .setCoverImage("Cover image of eye")
                        .setCategories(Set.of(new Category(1L))));
    }

    public static Book createBook() {
        return new Book()
                .setId(1L)
                .setTitle("1984")
                .setAuthor("George Orwell")
                .setIsbn("978-0-596-52068-7")
                .setPrice(BigDecimal.valueOf(10))
                .setDescription("Dystopian vision of the future")
                .setCoverImage("Cover image of eye")
                .setCategories(Set.of(new Category(1L)));
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
        return new BookDto()
                .setId(1L)
                .setTitle("1984")
                .setAuthor("George Orwell")
                .setIsbn("978-0-596-52068-7")
                .setPrice(BigDecimal.valueOf(10))
                .setDescription("Dystopian vision of the future")
                .setCoverImage("Cover image of eye")
                .setCategoryIds(List.of(1L));
    }

    public static Book getBookFromCreateBookRequestDto(CreateBookRequestDto requestDto) {
        return new Book()
                .setId(1L)
                .setTitle(requestDto.getTitle())
                .setAuthor(requestDto.getAuthor())
                .setIsbn(requestDto.getIsbn())
                .setPrice(requestDto.getPrice())
                .setDescription(requestDto.getDescription())
                .setCoverImage(requestDto.getCoverImage())
                .setCategories(Set.of(new Category(1L)));
    }

    public static BookDto getBookDtoFromBook(Book book) {
        return new BookDto()
                .setId(book.getId())
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setIsbn(book.getIsbn())
                .setPrice(book.getPrice())
                .setDescription(book.getDescription())
                .setCoverImage(book.getCoverImage())
                .setCategoryIds(book.getCategories()
                        .stream()
                        .map(Category::getId)
                        .collect(Collectors.toList()));
    }

    public static Book getBookFromBookDto(BookDto bookDto) {
        return new Book()
                .setId(bookDto.getId())
                .setTitle(bookDto.getTitle())
                .setAuthor(bookDto.getAuthor())
                .setIsbn(bookDto.getIsbn())
                .setPrice(bookDto.getPrice())
                .setDescription(bookDto.getDescription())
                .setCoverImage(bookDto.getCoverImage())
                .setCategories(Set.of(new Category(1L)));
    }
}

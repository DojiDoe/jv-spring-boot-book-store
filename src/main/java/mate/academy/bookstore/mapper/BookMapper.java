package mate.academy.bookstore.mapper;

import java.util.Optional;
import java.util.stream.Collectors;
import mate.academy.bookstore.config.MapperConfig;
import mate.academy.bookstore.dto.book.BookDto;
import mate.academy.bookstore.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.bookstore.dto.book.CreateBookRequestDto;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    void updateFromDto(@MappingTarget Book book, CreateBookRequestDto requestDto);

    @Mapping(target = "categoryIds", ignore = true)
    BookDto toDto(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto requestDto, Book book) {
        requestDto.setCategoryIds(book.getCategories().stream()
                .map(Category::getId)
                .toList());
    }

    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "id", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);

    @AfterMapping
    default void setCategories(@MappingTarget Book book, CreateBookRequestDto requestDto) {
        book.setCategories(requestDto.getCategoryIds().stream()
                .map(Category::new)
                .collect(Collectors.toSet()));
    }

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @Named("bookFromId")
    default Book bookFromId(Long id) {
        return Optional.ofNullable(id).map(Book::new).orElse(null);
    }
}

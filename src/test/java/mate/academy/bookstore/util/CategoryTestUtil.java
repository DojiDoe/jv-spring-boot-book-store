package mate.academy.bookstore.util;

import java.util.Arrays;
import java.util.List;
import mate.academy.bookstore.dto.category.CategoryDto;
import mate.academy.bookstore.model.Category;

public class CategoryTestUtil {
    private CategoryTestUtil() {
    }

    public static List<CategoryDto> createListOfCategoryDtoValues() {
        return Arrays.asList(
                new CategoryDto("Drama", "Drama desc"),
                new CategoryDto("Horror", "Horror desc"),
                new CategoryDto("Comedy", "Comedy desc"));
    }

    public static List<Category> createListOfCategoryValues() {
        return Arrays.asList(
                Category.builder()
                        .id(1L)
                        .name("Drama")
                        .description("Drama desc")
                        .build(),
                Category.builder()
                        .id(2L)
                        .name("Horror")
                        .description("Horror desc")
                        .build(),
                Category.builder()
                        .id(3L)
                        .name("Comedy")
                        .description("Comedy desc")
                        .build());
    }

    public static CategoryDto createCategoryDto() {
        return new CategoryDto("Drama", "Drama desc");
    }
}

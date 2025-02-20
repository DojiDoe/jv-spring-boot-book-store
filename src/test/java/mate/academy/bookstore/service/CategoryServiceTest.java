package mate.academy.bookstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import mate.academy.bookstore.dto.category.CategoryDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.CategoryMapper;
import mate.academy.bookstore.model.Category;
import mate.academy.bookstore.repository.CategoryRepository;
import mate.academy.bookstore.util.CategoryTestUtil;
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
public class CategoryServiceTest {
    public static final String EntityNotFoundExceptionMessage = "Can't get category by id ";
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Verify whether list of categories is returned when calling "
            + "findAll() method with valid id")
    public void findAll_ValidPageable_ShouldReturnListOfCategoryDto() {
        //Given
        Pageable pageable = PageRequest.of(0, 10);
        List<Category> categoryFromDB = CategoryTestUtil.createListOfCategoryValues();
        when(categoryRepository.findAll(pageable)).thenReturn(new PageImpl<>(categoryFromDB));
        when(categoryMapper.toDto(any(Category.class))).thenAnswer(invocation -> {
            Category category = invocation.getArgument(0);
            return new CategoryDto(category.getName(), category.getDescription());
        });
        // When
        List<CategoryDto> resultsDto = categoryService.findAll(pageable);
        // Then
        assertThat(resultsDto).hasSize(3);
        assertThat(resultsDto.get(0).name()).isEqualTo(categoryFromDB.get(0).getName());
        assertThat(resultsDto.get(1).description())
                .isEqualTo(categoryFromDB.get(1).getDescription());
        verify(categoryRepository, times(1)).findAll(pageable);
        verify(categoryMapper, times(resultsDto.size())).toDto(any(Category.class));
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

    @Test
    @DisplayName("Verify whether EntityNotFoundException is thrown when calling"
            + " getById() method with invalid id")
    public void getCategoryDto_InvalidBookId_ShouldThrowEntityNotFoundException() {
        //Given
        Long invalidId = 100L;
        Mockito.when(categoryRepository.findById(invalidId)).thenReturn(Optional.empty());
        //When
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.getById(invalidId)
        );
        // Then
        String expected = "Can't get category by id " + invalidId;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).findById(invalidId);
        verifyNoMoreInteractions(categoryRepository);

    }

    @Test
    @DisplayName("Verify whether CategoryDto is valid when calling save() method")
    public void save_ValidCreateBookRequestDto_ReturnsBookDto() {
        // Given
        CategoryDto categoryDto = CategoryTestUtil.createCategoryDto();
        Category category = Category.builder()
                .id(1L)
                .name(categoryDto.name())
                .description(categoryDto.description())
                .build();
        when(categoryMapper.toModel(categoryDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);
        // When
        CategoryDto savedCategoryDto = categoryService.save(categoryDto);
        //Then
        assertThat(savedCategoryDto).isEqualTo(categoryDto);
        verify(categoryRepository, times(1)).save(category);
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

    @Test
    @DisplayName("Verify whether EntityNotFoundException thrown when calling "
            + "update() method with invalid id")
    public void updateCategory_InvalidCategoryDto_ShouldThrowEntityNotFoundException() {
        // Given
        Long invalidId = 100L;
        CategoryDto categoryDto = CategoryTestUtil.createCategoryDto();
        when(categoryRepository.findById(invalidId)).thenReturn(Optional.empty());
        // When
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.update(invalidId, categoryDto)
        );
        // Then
        String expected = "Can't get category by id " + invalidId;
        String actual = exception.getMessage();
        assertThat(actual).isEqualTo(expected);
        verify(categoryRepository, times(1)).findById(invalidId);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    @DisplayName("Verify whether EntityNotFoundException thrown when calling "
            + "deleteById() method with invalid id")
    public void deleteBook_ValidBookId_ShouldDeleteBook() {
        // Given
        Long invalidId = 100L;
        when(categoryRepository.findById(invalidId)).thenReturn(Optional.empty());
        // When
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.deleteById(invalidId)
        );
        // Then
        String expected = EntityNotFoundExceptionMessage + invalidId;
        String actual = exception.getMessage();
        assertThat(actual).isEqualTo(expected);
        verify(categoryRepository, times(1)).findById(invalidId);
        verifyNoMoreInteractions(categoryRepository);
    }

}

package mate.academy.bookstore.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
@Builder
public class CreateBookRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    @NotNull
    @ISBN
    private String isbn;
    @Min(0)
    @NotNull
    private BigDecimal price;
    @Size(min = 10, max = 255)
    private String description;
    private String coverImage;
    @NotEmpty
    private List<Long> categoryIds;
}

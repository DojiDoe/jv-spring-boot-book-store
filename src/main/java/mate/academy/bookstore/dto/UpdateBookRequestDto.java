package mate.academy.bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;

@Getter
@Setter
public class UpdateBookRequestDto {
    @Min(0)
    @NotNull
    private Long id;
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
    @NotBlank
    @Size(min = 10, max = 255)
    private String description;
    @NotBlank
    private String coverImage;
}

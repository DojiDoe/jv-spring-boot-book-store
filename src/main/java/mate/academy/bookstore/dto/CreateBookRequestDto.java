package mate.academy.bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;

@Getter
@Setter
public class CreateBookRequestDto {
    @NotNull
    @Pattern(regexp = "^(?=\\s*\\S).*$")
    private String title;
    @NotNull
    @Pattern(regexp = "^(?=\\s*\\S).*$")
    private String author;
    @ISBN
    private String isbn;
    @Min(0)
    private BigDecimal price;
    @NotNull
    @Size(min = 10, max = 255)
    private String description;
    @NotNull
    private String coverImage;
}

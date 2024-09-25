package mate.academy.bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import mate.academy.bookstore.validation.Isbn;

@Getter
@Setter
public class CreateBookRequestDto {
    @NotNull
    private String title;
    @NotNull
    private String author;
    @Isbn
    private String isbn;
    @Min(0)
    private BigDecimal price;
    @NotNull
    private String description;
    @NotNull
    private String coverImage;
}

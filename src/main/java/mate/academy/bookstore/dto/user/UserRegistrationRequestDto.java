package mate.academy.bookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import mate.academy.bookstore.validation.FieldMatch;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(first = "password", second = "repeatPassword",
        message = "The password fields don't match")
public class UserRegistrationRequestDto {
    @Email
    @NotBlank
    private String email;
    @Length(min = 8, max = 35)
    @NotBlank
    private String password;
    @Length(min = 8, max = 35)
    @NotBlank
    private String repeatPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String shippingAddress;
}

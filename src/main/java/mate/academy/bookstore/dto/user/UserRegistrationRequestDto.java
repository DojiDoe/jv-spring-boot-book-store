package mate.academy.bookstore.dto.user;

import jakarta.validation.constraints.Email;
import lombok.Data;
import mate.academy.bookstore.validation.FieldMatch;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(first = "password", second = "repeatPassword",
        message = "The password fields don't match")
public class UserRegistrationRequestDto {
    @Email
    private String email;
    @Length(min = 8, max = 35)
    private String password;
    @Length(min = 8, max = 35)
    private String repeatPassword;
    private String firstName;
    private String lastName;
    private String shippingAddress;
}

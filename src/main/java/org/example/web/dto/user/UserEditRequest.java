package org.example.web.dto.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.user.model.user.GenderType;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEditRequest {

    @Size(min = 3, max = 20, message = "Дължината на името трябва да бъде между 3 и 20 символа!")
    @NotBlank(message = "Полето не може да бъде празно!")
    private String firstName;

    @Email(message = "Въведи валиден имейл!")
    @NotBlank(message = "Полето не може да бъде празно!")
    private String email;

    @Size(min = 3, max = 20, message = "Потребителското име трябва да бъде между 3 и 20 символа!")
    @NotBlank(message = "Полето не може да бъде празно!")
    private String username;

    @Min(value = 18, message = "Минималната възраст е 18 години!")
    @NotNull(message = "Полето не може да бъде празно!")
    private Integer age;

    @Size(min = 4, max = 20, message = "Имено на града трябва да бъде между 4 и 20 символа!")
    @NotBlank(message = "Полето не може да бъде празно!")
    private String town;

    private GenderType gender;

    @URL(message = "Въведи валиден URL!")
    private String profilePicture;

}

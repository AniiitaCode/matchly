package org.example.web.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @Size(min = 3, max = 20, message = "Потребителското име трябва да бъде между 3 и 20 символа!")
    @NotBlank(message = "Полето не може да бъде празно!")
    private String username;

    @Size(min = 3, max = 20, message = "Дължината на паролата трябва да бъде между 3 и 20 символа!")
    @NotBlank(message = "Полето не може да бъде празно!")
    private String password;

}

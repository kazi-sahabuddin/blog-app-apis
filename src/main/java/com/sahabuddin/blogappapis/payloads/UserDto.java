package com.sahabuddin.blogappapis.payloads;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;

    @NotEmpty
    @Size(min = 4, message = "Name Must be min of 4 characters!")
    private String name;

    @Email(message = "Email address is not valid!")
    private String email;

    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must be min of 3 chars and max of 10 chars!")
    private String password;

    @NotEmpty
    private String about;
}

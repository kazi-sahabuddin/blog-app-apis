package com.sahabuddin.blogappapis.payloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignInRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}

package com.example.lms.demo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank(message = "{NotBlank.name}")
    private String name;

    @Email(message = "{Email.email}")
    @NotBlank(message = "{NotBlank.email}")
    private String email;

    @NotBlank(message = "{NotBlank.phone}")
    private String phone;

    @NotBlank(message = "{NotBlank.address}")
    private String address;

    @NotBlank(message = "{NotBlank.username}")
    private String username;

    @NotBlank(message = "{NotBlank.password}")
    @Size(min = 6, message = "{Size.password}")
    private String password;

}

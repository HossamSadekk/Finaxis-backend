package com.finaxis.finaxis.model.authentication;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestModel {
    @NotNull(message = "name is required")
    private String username;

    @NotNull(message = "mobile phone number is required")
    private String phone;

    @NotNull(message = "passcode is required")
    @Size(min = 6, max = 6, message = "passcode must be exactly 6 digits long")
    private String passcode;
}
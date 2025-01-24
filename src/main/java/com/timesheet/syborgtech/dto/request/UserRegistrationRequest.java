package com.timesheet.syborgtech.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timesheet.syborgtech.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class UserRegistrationRequest {
    @NotBlank(message = "Username is required")
    private String userName;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Phone Number is Required")
    private String phoneNumber;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfBirth;

    private User.Status status;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfJoining;
}

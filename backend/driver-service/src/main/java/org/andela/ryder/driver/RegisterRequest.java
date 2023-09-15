package org.andela.ryder.driver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private String password;
    private String confirmedPassword;
    private String cabRegistrationNumber;
}

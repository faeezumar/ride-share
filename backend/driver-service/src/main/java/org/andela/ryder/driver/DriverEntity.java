package org.andela.ryder.driver;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private String password;
    private String confirmedPassword;
    private String cabRegistrationNumber;
    @Embedded
    private Location currentLocation;
    private LocalDateTime lastLocationUpdate;
}

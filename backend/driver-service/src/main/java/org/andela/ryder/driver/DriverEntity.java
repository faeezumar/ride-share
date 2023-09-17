package org.andela.ryder.driver;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "drivers")
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
    @Column(columnDefinition = "DATE")
    private LocalDate dateOfBirth;
    private String password;
    private String confirmedPassword;
    private String cabRegistrationNumber;
    @Embedded
    private Location currentLocation;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime lastLocationUpdate;
}

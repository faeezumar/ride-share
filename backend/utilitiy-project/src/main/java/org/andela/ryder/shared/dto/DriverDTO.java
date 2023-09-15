package org.andela.ryder.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverDTO {
    private Long id;
    private String name;
    private String email;
    private String cabRegistrationNumber;
    private LocationDTO currentLocation;
}

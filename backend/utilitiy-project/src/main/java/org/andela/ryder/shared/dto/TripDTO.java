package org.andela.ryder.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.andela.ryder.shared.entity.Trip;
import org.andela.ryder.shared.enums.TripStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripDTO implements Trip {
    private Long id;
    private Long customerId;
    private Long driverId;
    private String pickupLocation;
    private String destination;
    private double longitude;
    private double latitude;
    private int numberOfPassengers;
    private TripStatus tripStatus;
    private LocalDateTime time;
    private LocalDateTime createdAt;
}

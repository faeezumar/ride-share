package org.andela.ryder.trip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripRequest {
    private Long customerId;
    private String pickupLocation;
    private String destination;
    private int numberOfPassengers;
    private LocalDateTime requestTime;
}

package org.andela.ryder.trip;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.andela.ryder.shared.enums.TripStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "trip")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private Long driverId;
    private String pickupLocation;
    private String destination;
    private double longitude;
    private double latitude;
    private int numberOfPassengers;
    private TripStatus status;
    private LocalDateTime time;
    private LocalDateTime createdAt;
}

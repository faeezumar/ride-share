package org.andela.ryder.trip;

import org.andela.ryder.exception.BadCustomerStatusException;
import org.andela.ryder.exception.NoMatchingDriverException;
import org.andela.ryder.shared.dto.DriverDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class TripService {

    private final TripRepository tripRepository;
    private final WebClient webClient;

    @Autowired
    public TripService(TripRepository tripRepository, WebClient webClient) {
        this.tripRepository = tripRepository;
        this.webClient = webClient;
    }

    public void requestTrip(TripRequest tripRequest) {
        tripRequest.setRequestTime(LocalDateTime.now());
        var customerCanRequestRide = webClient.get()
                .uri("http://customer-service/api/v1/customers/{customerId}/canRequest", tripRequest.getCustomerId())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (!Boolean.TRUE.equals(customerCanRequestRide))
            throw new BadCustomerStatusException("Customer cannot request a ride");


        var matchedDriver = webClient.post()
                .uri("http://distro-service/api/v1/distro/requestDriver")
                .body(Mono.just(tripRequest), TripRequest.class)
                .retrieve()
                .bodyToMono(DriverDTO.class)
                .block();

        if (matchedDriver == null)
            throw new NoMatchingDriverException("Customer cannot request a ride");

        var tripInstance = TripEntity.builder()
                .customerId(tripRequest.getCustomerId())
                .driverId(matchedDriver.getId())
                .pickupLocation(tripRequest.getPickupLocation())
                .destination(tripRequest.getDestination())
                .status(TripStatus.PENDING)
                .numberOfPassengers(tripRequest.getNumberOfPassengers())
                .build();

        tripRepository.save(tripInstance);
    }

    private TripEntity toEntity(TripRequest tripRequest) {
        return new TripEntity();
    }

    public void acceptTrip(TripRequest tripRequest) {
    }
}

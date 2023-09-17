package org.andela.ryder.trip;

import org.andela.ryder.exception.NoMatchingDriverException;
import org.andela.ryder.shared.dto.TripDTO;
import org.andela.ryder.shared.enums.TripStatus;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class RideMatchingService {

    private final WebClient webClient;

    private final TripMapper tripMapper;

    private final TripRepository tripRepository;

    @Autowired
    public RideMatchingService(WebClient webClient, TripMapper tripMapper, TripRepository tripRepository) {
        this.webClient = webClient;
        this.tripMapper = tripMapper;
        this.tripRepository = tripRepository;
    }

    @Job(name = "Searching for driver", retries = 2 )
    public void findDriverForTrip(TripEntity tripEntity) {
        //var customerCanRequestRide = webClient.get()
        //        .uri("http://customer-service/api/v1/customers/{customerId}/canRequest", tripEntity.getCustomerId())
        //        .retrieve()
        //        .bodyToMono(Boolean.class)
        //        .block();

        //if (!Boolean.TRUE.equals(customerCanRequestRide))
        //    throw new BadCustomerStatusException("Customer cannot request a ride");

        var matchedDriverId = Objects.requireNonNull(webClient.post()
                .uri("http://distro-service/api/v1/distro/requestDriver")
                .body(Mono.just(tripMapper.toDTO(tripEntity)), TripDTO.class)
                .retrieve()
                .bodyToMono(TripDTO.class)
                .block()).getDriverId();

        if (matchedDriverId == null)
            throw new NoMatchingDriverException("Unable to find a matching driver");

        tripEntity.setDriverId(matchedDriverId);
        tripEntity.setStatus(TripStatus.ACCEPTED);
        tripRepository.save(tripEntity);
        
        notifyDriver(tripEntity);
    }

    private void notifyDriver(TripEntity tripEntity) {
        webClient.post()
                .uri("http://notification-service/api/v1/notificationns/notify-drivers/")
                .body(Mono.just(tripMapper.toDTO(tripEntity)), TripDTO.class)
                .retrieve()
                .bodyToMono(TripDTO.class);
    }
}

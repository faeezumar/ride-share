package org.andela.ryder.notification;

import org.andela.ryder.shared.dto.TripDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping(path = "/customers/{customerId}/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Notification> customerSubscribe(@PathVariable("customerId") Long customerId) {
        return notificationService.notificationByCustomerId(customerId);
    }

    @GetMapping(path = "/drivers/{driverId}/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Notification> driverSubscribe(@PathVariable("driverId") Long driverId) {
        return notificationService.notificationByDriverId(driverId);
    }

    @PostMapping("/notify-customer")
    public void notifyCustomer(@RequestBody TripDTO tripDTO)  {
        var notification = Notification.builder()
                .pickupLocation(tripDTO.getPickupLocation())
                .destination(tripDTO.getDestination())
                .time(LocalDateTime.now())
                .status(tripDTO.getTripStatus().name())
                .customerId(tripDTO.getCustomerId())
                .build();
        notificationService.saveCustomerNotification(notification);
    }

    @PostMapping("/notify-driver")
    public void notifyDriver(@RequestBody TripDTO tripDTO)  {
        var notification = Notification.builder()
                .id(tripDTO.getId())
                .customerId(tripDTO.getCustomerId())
                .driverId(tripDTO.getDriverId())
                .pickupLocation(tripDTO.getPickupLocation())
                .destination(tripDTO.getDestination())
                .status(tripDTO.getTripStatus().name())
                .time(LocalDateTime.now())
                .build();
        notificationService.saveDriverNotification(notification);
    }

}

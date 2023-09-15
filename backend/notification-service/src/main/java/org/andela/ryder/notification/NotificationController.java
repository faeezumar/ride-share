package org.andela.ryder.notification;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class NotificationController {
    private ArrayList<Notification> customerNotificationsList =  new ArrayList();
    private ArrayList<Notification> driverNotificationsList =  new ArrayList();

    @GetMapping(path = "/customers/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Notification> customerSubscribe() {
        final DateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        return Flux.fromIterable(customerNotificationsList);
    }

    @GetMapping(path = "/drivers/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Notification> driverSubscribe() {
        final DateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        return Flux.fromIterable(driverNotificationsList);
    }

    @PostMapping("/notify-customers")
    public ResponseEntity<Object> notifySubscribers()  {
        final DateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        var notification = Notification.builder()
                .pickupLocation("Mandawari Sabon Titi")
                .destination("Hotoro NNPC Junction.")
                .time(DATE_FORMATTER.format(new Date()))
                .status("APPROVED")
                .build();
        customerNotificationsList.add(notification);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/notify-drivers")
    public ResponseEntity<Object> notifyDriversSubscribers()  {
        final DateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        var notification = Notification.builder()
                .pickupLocation("Mandawari Sabon Titi")
                .destination("Hotoro NNPC Junction.")
                .time(DATE_FORMATTER.format(new Date()))
                .status("APPROVED")
                .build();
        driverNotificationsList.add(notification);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

}

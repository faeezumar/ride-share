package org.andela.ryder.notification;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    private final Map<Long, List<Notification>> customerNotificationRepository;
    private final Map<Long, List<Notification>> driverNotificationRepository;

    public NotificationService() {
        this.customerNotificationRepository = new HashMap<>();
        this.driverNotificationRepository = new HashMap<>();
    }

    public Flux<Notification> notificationByCustomerId(Long customerId) {
        return Flux.fromIterable(customerNotificationRepository.getOrDefault(customerId, new ArrayList<>()));
    }

    public Flux<Notification> notificationByDriverId(Long driverId) {
        return Flux.fromIterable(driverNotificationRepository.getOrDefault(driverId, new ArrayList<>()));
    }

    public void saveCustomerNotification(Notification notification) {
        var entry = customerNotificationRepository.getOrDefault(notification.getCustomerId(),
                new ArrayList<Notification>());
        entry.add(notification);
        customerNotificationRepository.put(notification.getCustomerId(), entry);
    }

    public void saveDriverNotification(Notification notification) {
        var entry = driverNotificationRepository.getOrDefault(notification.getDriverId(),
                new ArrayList<Notification>());
        entry.add(notification);
        driverNotificationRepository.put(notification.getDriverId(), entry);
    }
}

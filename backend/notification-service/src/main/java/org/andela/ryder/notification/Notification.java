package org.andela.ryder.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    private String pickupLocation;
    private String destination;
    private String time;
    private String status;
}
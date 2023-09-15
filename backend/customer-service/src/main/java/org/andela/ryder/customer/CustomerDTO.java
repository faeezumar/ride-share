package org.andela.ryder.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {
    private Long id;
    private String name;
    private String address;
    private String email;
    private LocalDateTime createdAt;

    //private List<Trip> tripHistory;
}
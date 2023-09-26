package org.andela.ryder.trip;

import org.andela.ryder.shared.dto.TripDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TripMapper {

    private final ModelMapper modelMapper;

    public TripMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TripEntity toEntity(TripDTO tripRequest) {
        return  TripEntity.builder()
                .id(tripRequest.getId())
                .time(tripRequest.getTime())
                .driverId(tripRequest.getDriverId())
                .createdAt(tripRequest.getCreatedAt())
                .status(tripRequest.getTripStatus())
                .numberOfPassengers(tripRequest.getNumberOfPassengers())
                .destination(tripRequest.getDestination())
                .pickupLocation(tripRequest.getPickupLocation())
                .customerId(tripRequest.getCustomerId())
                .latitude(tripRequest.getLatitude())
                .longitude(tripRequest.getLongitude())
                .build();
    }

    public TripDTO toDTO(TripEntity tripEntity) {;
        return TripDTO.builder()
                .id(tripEntity.getId())
                .driverId(tripEntity.getDriverId())
                .customerId(tripEntity.getCustomerId())
                .numberOfPassengers(tripEntity.getNumberOfPassengers())
                .pickupLocation(tripEntity.getPickupLocation())
                .destination(tripEntity.getDestination())
                .time(tripEntity.getTime())
                .longitude(tripEntity.getLongitude())
                .latitude(tripEntity.getLatitude())
                .createdAt(tripEntity.getCreatedAt())
                .tripStatus(tripEntity.getStatus())
                .build();
    }
}

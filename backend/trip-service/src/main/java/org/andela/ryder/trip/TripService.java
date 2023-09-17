package org.andela.ryder.trip;

import org.andela.ryder.shared.dto.TripDTO;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripService {

    private final TripRepository tripRepository;

    private final TripMapper tripMapper;

    private final JobScheduler jobScheduler;

    private final RideMatchingService rideMatchingService;

    @Autowired
    public TripService(TripRepository tripRepository, TripMapper tripMapper, JobScheduler jobScheduler, RideMatchingService rideMatchingService) {
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
        this.jobScheduler = jobScheduler;
        this.rideMatchingService = rideMatchingService;
    }

    public TripDTO requestTrip(TripDTO tripRequest) {
        var createdTrip = tripRepository.save(tripMapper.toEntity(tripRequest));
        jobScheduler.enqueue(() -> rideMatchingService.findDriverForTrip(createdTrip));
        return tripMapper.toDTO(createdTrip);
    }

    public void acceptTrip(TripDTO tripRequest) {

    }
}

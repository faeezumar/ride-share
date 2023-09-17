package org.andela.ryder.trip;

import org.andela.ryder.shared.dto.TripDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trip")
public class TripController {

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }


    @PostMapping("/request-trip")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TripDTO requestTrip(@RequestBody TripDTO tripRequest) {
        return tripService.requestTrip(tripRequest);
    }

    @PostMapping("/accept-trip")
    public ResponseEntity<Object> acceptTrip(@RequestBody TripDTO tripRequest) {
        tripService.acceptTrip(tripRequest);
        return ResponseEntity.ok("");
    }
}

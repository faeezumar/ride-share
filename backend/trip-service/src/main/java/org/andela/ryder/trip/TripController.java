package org.andela.ryder.trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/trip")
public class TripController {

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }


    @PostMapping("/request-trip")
    public ResponseEntity<Object> requestTrip(@RequestBody TripRequest tripRequest) {
        tripService.requestTrip(tripRequest);
        return ResponseEntity.ok("");
    }

    @PostMapping("/accept-trip")
    public ResponseEntity<Object> acceptTrip(@RequestBody TripRequest tripRequest) {
        tripService.acceptTrip(tripRequest);
        return ResponseEntity.ok("");
    }
}

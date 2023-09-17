package org.andela.ryder.driver;

import org.andela.ryder.shared.dto.DriverDTO;
import org.andela.ryder.shared.dto.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {

    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping("/register")
    public ResponseEntity<DriverDTO> registerDriver(@RequestBody RegisterRequest registerRequest) {
        var registeredDriver = this.driverService.registerDriver(registerRequest);
        return new ResponseEntity<>(registeredDriver, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DriverDTO>> getAllDrivers() {
        var driversList = driverService.getAvailableDrivers();
        return new ResponseEntity<>(driversList, HttpStatus.OK);
    }

    @PostMapping("/{driverId}/update-location")
    public ResponseEntity<String> updateDriverLocation(
            @PathVariable Long driverId,
            @RequestBody LocationDTO newLocation) {
        driverService.updateDriverLocation(driverId, newLocation);
        return ResponseEntity.ok("Driver's location updated successfully");
    }
}

package org.andela.ryder.distro;

import org.andela.ryder.shared.dto.DriverDTO;
import org.andela.ryder.shared.dto.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/distro")
public class DistroController {
    private final DistroService distroService;

    @Autowired
    public DistroController(DistroService distroService) {
        this.distroService = distroService;
    }

    @PostMapping("/requestDriver")
    public ResponseEntity<DriverDTO> requestDriver(@RequestBody LocationDTO location) {
        var matchedDriver =  distroService.getMatchedDriver(location);
        return new ResponseEntity<DriverDTO>(matchedDriver, HttpStatus.OK);
    }
}

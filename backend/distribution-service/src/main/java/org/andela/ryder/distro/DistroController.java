package org.andela.ryder.distro;

import org.andela.ryder.shared.dto.TripDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TripDTO findMatchingDriver(@RequestBody TripDTO tripDTO) {
        return distroService.findMatchingDriver(tripDTO);
    }
}

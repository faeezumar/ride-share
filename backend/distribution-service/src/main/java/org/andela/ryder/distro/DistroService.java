package org.andela.ryder.distro;

import org.andela.ryder.shared.dto.DriverDTO;
import org.andela.ryder.shared.dto.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

@Service
public class DistroService {

    private final QuadTreeImpl<Double, DriverDTO> quadTree;
    private final WebClient webClient;

    @Autowired
    public DistroService(QuadTreeImpl<Double, DriverDTO> quadTree, WebClient webClient) {
        this.quadTree = quadTree;
        this.webClient = webClient;
    }

    public DriverDTO getMatchedDriver(LocationDTO location) {
        var driverDTOArray = webClient.get()
                .uri("http://driver-service/api/v1/drivers/all")
                .retrieve()
                .bodyToMono(DriverDTO[].class)
                .block();

        if (driverDTOArray != null) {
            Arrays.stream(driverDTOArray).forEach(driver -> quadTree.insert(
                    driver.getCurrentLocation().getLongitude(),
                    driver.getCurrentLocation().getLatitude(), driver));
        }

        return quadTree.nearestNeighbor(location.getLongitude(), location.getLongitude());
    }
}

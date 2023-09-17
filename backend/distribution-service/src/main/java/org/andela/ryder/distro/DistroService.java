package org.andela.ryder.distro;

import org.andela.ryder.shared.dto.DriverDTO;
import org.andela.ryder.shared.dto.TripDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

@Service
public class DistroService {
    private final QuadTreeImpl<Double, Long> quadTree;

    private final CoverTreeImpl<Long> coverTree;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public DistroService(QuadTreeImpl<Double, Long> quadTree, CoverTreeImpl<Long> coverTree, WebClient.Builder webClientBuilder) {
        this.quadTree = quadTree;
        this.coverTree = coverTree;
        this.webClientBuilder = webClientBuilder;
    }

    public TripDTO findMatchingDriver(TripDTO trip) {
        var driversArray = webClientBuilder.build().get()
                .uri("http://driver-service/api/v1/drivers/all")
                .retrieve()
                .bodyToMono(DriverDTO[].class)
                .block();

        if (driversArray != null && driversArray.length > 0) {
            Arrays.stream(driversArray).forEach(driver -> {
                coverTree.insert(driver.getId(), new double[]{
                        driver.getCurrentLocation().getLongitude(),
                        driver.getCurrentLocation().getLatitude()});
            });

            var matchedDriverId = coverTree.getNearest(new double[]
                    {trip.getLongitude(), trip.getLatitude()});
            trip.setDriverId(matchedDriverId);
            return trip;
        }

        throw new ArithmeticException("No driver is available");
    }
}

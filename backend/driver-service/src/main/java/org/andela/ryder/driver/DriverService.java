package org.andela.ryder.driver;

import org.andela.ryder.exception.DriverNotFoundException;
import org.andela.ryder.shared.dto.DriverDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class DriverService {

    private final DriverRepository driverRepository;
    //private final PasswordEncoder passwordEncoder;

    @Autowired
    public DriverService(
            DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    public DriverDTO registerDriver(RegisterRequest registerRequest) {
        var driverEntity = toEntity(registerRequest);
        //driverEntity.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        var savedInstance =  driverRepository.save(driverEntity);
        return toDTO(savedInstance);

    }

    public List<DriverDTO> getAvailableDrivers() {
        Date oneMinuteAgo = new Date(System.currentTimeMillis() - 60 * 1000);
        return driverRepository.findAvailableDrivers(oneMinuteAgo).stream().map(this::toDTO).toList();
    }

    @Transactional
    public void updateDriverLocation(Long driverId, Location newLocation) {
         var driverInstance = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found with id: " + driverId));
        driverInstance.setCurrentLocation(newLocation);
        driverInstance.setLastLocationUpdate(LocalDateTime.now());
        driverRepository.save(driverInstance);
    }

    private DriverDTO toDTO(DriverEntity savedInstance) {
        return DriverDTO.builder()
                .id(savedInstance.getId())
                .name(savedInstance.getName())
                .email(savedInstance.getEmail())
                .build();
    }

    private DriverEntity toEntity(RegisterRequest registerRequest) {
        return DriverEntity.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .cabRegistrationNumber(registerRequest.getCabRegistrationNumber())
                .dateOfBirth(registerRequest.getDateOfBirth())
                .currentLocation(new Location())
                .build();
    }

}

package org.andela.ryder.driver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Long> {

    @Query("SELECT d FROM DriverEntity d WHERE d.lastLocationUpdate >= :oneMinuteAgo")
    List<DriverEntity> findAvailableDrivers(Date oneMinuteAgo);
}

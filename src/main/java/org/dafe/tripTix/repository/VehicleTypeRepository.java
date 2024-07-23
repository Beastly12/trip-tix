package org.dafe.tripTix.repository;

import org.dafe.tripTix.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {

    @Query("SELECT v FROM VehicleType v LEFT JOIN FETCH v.seats")
    List<VehicleType> findAllWithSeats();

}

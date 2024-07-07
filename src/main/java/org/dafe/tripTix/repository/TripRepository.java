package org.dafe.tripTix.repository;

import org.dafe.tripTix.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByFromAndTo(Location from, Destination to);
//    List<Trip> findByVehicle_Type(VehicleType type);

    List<Trip> findByUser(User user);

    List<Trip> findByFrom_NameAndTo_NameAndVehicle_VehicleType_Type(String fromName, String toName, String vehicleType);
}

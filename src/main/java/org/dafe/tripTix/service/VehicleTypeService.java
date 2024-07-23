package org.dafe.tripTix.service;

import lombok.RequiredArgsConstructor;
import org.dafe.tripTix.dto.CreateVehicleRequest;
import org.dafe.tripTix.dto.SeatDto;
import org.dafe.tripTix.dto.VehicleTypeDto;
import org.dafe.tripTix.entity.Seat;
import org.dafe.tripTix.entity.VehicleType;
import org.dafe.tripTix.repository.SeatRepository;
import org.dafe.tripTix.repository.VehicleTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VehicleTypeService {
    private VehicleTypeRepository vehicleTypeRepository;

    private SeatRepository seatRepository;

    public VehicleTypeService(VehicleTypeRepository vehicleTypeRepository, SeatRepository seatRepository) {
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.seatRepository = seatRepository;
    }

    public VehicleType createVehicle(CreateVehicleRequest request) {
        VehicleType vehicleType = new VehicleType();
        vehicleType.setType(request.getVehicleType().getType());
        vehicleType.setCapacity(request.getVehicleType().getCapacity());
        vehicleType.setPrice(request.getVehicleType().getPrice());

        VehicleType savedVehicleType = vehicleTypeRepository.save(vehicleType);

        List<Seat> seats = request.getSeats().stream().map(seatDTO -> {
            Seat seat = new Seat();
            seat.setAvailable(seatDTO.isAvailable());
            seat.setBooked(seatDTO.getBooked());
            seat.setBlocked(seatDTO.getBlocked());
            seat.setVehicleType(savedVehicleType);
            return seat;
        }).collect(Collectors.toList());

        seatRepository.saveAll(seats);

        return savedVehicleType;
    }

    public List<VehicleType> findAll() {
        return vehicleTypeRepository.findAllWithSeats();
    }

    public VehicleType save(VehicleType vehicleType) {
        return vehicleTypeRepository.save(vehicleType);
    }

    public void delete(Long id) {
        vehicleTypeRepository.deleteById(id);
    }
}


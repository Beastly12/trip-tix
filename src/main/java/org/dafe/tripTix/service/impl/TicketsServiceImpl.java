package org.dafe.tripTix.service.impl;

import org.dafe.tripTix.dto.AvailableSeatsDto;
import org.dafe.tripTix.dto.TicketsDto;
import org.dafe.tripTix.entity.*;
import org.dafe.tripTix.repository.TicketsRepository;
import org.dafe.tripTix.repository.VehicleSeatRepository;
import org.dafe.tripTix.repository.VehicleTicketRepository;
import org.dafe.tripTix.service.TicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.dafe.tripTix.mapper.TicketsMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketsServiceImpl implements TicketsService {


        @Autowired
        private TicketsRepository ticketRepository;

        @Autowired
        private VehicleTicketRepository vehicleTicketRepository;

        @Autowired
        private VehicleSeatRepository vehicleSeatRepository;

        @Autowired
        private TicketsMapper ticketMapper;


        @Override
        public List<TicketsDto> getAllTickets() {
            List<Tickets> tickets = ticketRepository.findAll();
            List<VehicleTicket> vehicleTickets = vehicleTicketRepository.findAll();
            return ticketMapper.toDTOs(tickets, vehicleTickets);
        }

        @Override
        public TicketsDto createTicket(TicketsDto ticketDTO) {
            Tickets ticket = ticketMapper.toEntity(ticketDTO);
            ticket = ticketRepository.save(ticket);
            return ticketMapper.toDTO(ticket, null);  // Add vehicle types if needed
        }

    @Override
    public List<AvailableSeatsDto> getAvailableSeatsByTicketId(int ticketId) {
        List<VehicleSeats> vehicleSeats = vehicleSeatRepository.findByTicketTicketId(ticketId);

        // Create a list to store available seats
        List<Integer> availableSeats = new ArrayList<>();

        // Populate the list with available seats
        for (VehicleSeats seat : vehicleSeats) {
            availableSeats.add(seat.getSeat().getAvailableSeats());
        }
        AvailableSeatsDto availableSeatsDto=new AvailableSeatsDto(availableSeats);

        List<AvailableSeatsDto> seats = new ArrayList<>();
        seats.add(availableSeatsDto);



        return seats;
    }

    public List<Tickets> getTicketsByUser(User user) {
        return ticketRepository.findByUser(user);
    }
}




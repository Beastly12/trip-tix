package org.dafe.tripTix.controller;

import lombok.RequiredArgsConstructor;
import org.dafe.tripTix.entity.TransportRoute;
import org.dafe.tripTix.service.TransportRouteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/routes")
public class TransportRouteController {
    private TransportRouteService transportRouteService;

    @GetMapping
    public List<TransportRoute> getAllRoutes() {
        return transportRouteService.findAll();
    }

    @PostMapping
    public TransportRoute createRoute(@RequestBody TransportRoute transportRoute) {
        return transportRouteService.save(transportRoute);
    }

    @DeleteMapping("/{id}")
    public void deleteRoute(@PathVariable Long id) {
        transportRouteService.delete(id);
    }
}

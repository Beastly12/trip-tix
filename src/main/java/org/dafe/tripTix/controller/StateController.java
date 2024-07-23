package org.dafe.tripTix.controller;

import lombok.RequiredArgsConstructor;
import org.dafe.tripTix.entity.State;
import org.dafe.tripTix.service.StateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/states")
public class StateController {
    private StateService stateService;

    @GetMapping
    public List<State> getAllStates() {
        return stateService.findAll();
    }

    @PostMapping
    public State createState(@RequestBody State state) {
        return stateService.save(state);
    }

    @DeleteMapping("/{id}")
    public void deleteState(@PathVariable Long id) {
        stateService.delete(id);
    }
}


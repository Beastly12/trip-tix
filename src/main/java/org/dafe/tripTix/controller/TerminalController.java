package org.dafe.tripTix.controller;

import lombok.RequiredArgsConstructor;
import org.dafe.tripTix.entity.Terminal;
import org.dafe.tripTix.service.TerminalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/terminals")
public class TerminalController {
    private TerminalService terminalService;

    @GetMapping
    public List<Terminal> getAllTerminals() {
        return terminalService.findAll();
    }

    @PostMapping
    public Terminal createTerminal(@RequestBody Terminal terminal) {
        return terminalService.save(terminal);
    }

    @DeleteMapping("/{id}")
    public void deleteTerminal(@PathVariable Long id) {
        terminalService.delete(id);
    }
}


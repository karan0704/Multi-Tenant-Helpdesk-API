package com.karan.helpdesk.controller;

import com.karan.helpdesk.dto.CreateTicketRequest;
import com.karan.helpdesk.dto.TicketDTO;
import com.karan.helpdesk.entity.Ticket;
import com.karan.helpdesk.entity.User;
import com.karan.helpdesk.repository.UserRepository;
import com.karan.helpdesk.service.TicketService;
import com.karan.helpdesk.tenant.TenantContext;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired private TicketService ticketService;
    @Autowired private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        List<TicketDTO> tickets = ticketService.getAllTicketsForCurrentTenant()
                .stream()
                .map(TicketDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tickets);
    }

    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@RequestBody @Valid CreateTicketRequest request) {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User creator = userRepository.findByEmail(currentEmail).orElseThrow();

        Ticket ticket = Ticket.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(Ticket.Priority.valueOf(request.getPriority().toUpperCase()))
                .status(Ticket.Status.OPEN)
                .createdBy(creator)
                .tenant(creator.getTenant())
                .build();

        Ticket saved = ticketService.createTicket(ticket);
        return ResponseEntity.ok(TicketDTO.fromEntity(saved));
    }

    @PatchMapping("/{ticketId}/assign/{engineerId}")
    public ResponseEntity<TicketDTO> assignTicket(@PathVariable UUID ticketId,
                                                  @PathVariable UUID engineerId) {
        Ticket updated = ticketService.assignTicket(ticketId, engineerId);
        return ResponseEntity.ok(TicketDTO.fromEntity(updated));
    }
}

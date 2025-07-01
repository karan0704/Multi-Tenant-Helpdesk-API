package com.karan.helpdesk.service;

import com.karan.helpdesk.entity.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketService {
    List<Ticket> getAllTicketsForCurrentTenant();
    Ticket createTicket(Ticket ticket);
    Ticket assignTicket(UUID ticketId, UUID engineerId);
}

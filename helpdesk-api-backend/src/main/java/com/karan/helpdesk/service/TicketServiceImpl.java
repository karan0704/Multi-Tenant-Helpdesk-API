package com.karan.helpdesk.service.impl;

import com.karan.helpdesk.entity.Ticket;
import com.karan.helpdesk.entity.Tenant;
import com.karan.helpdesk.repository.TicketRepository;
import com.karan.helpdesk.tenant.TenantContext;
import com.karan.helpdesk.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired private TicketRepository ticketRepository;

    @Override
    public List<Ticket> getAllTicketsForCurrentTenant() {
        UUID tenantId = TenantContext.getTenantId();
        return ticketRepository.findAll().stream()
                .filter(ticket -> ticket.getTenant().getId().equals(tenantId))
                .toList();
    }

    @Override
    public Ticket createTicket(Ticket ticket) {
        ticket.setTenant(new Tenant(TenantContext.getTenantId(), null, null));
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket assignTicket(UUID ticketId, UUID engineerId) {
        // Placeholder - add logic here in future
        return null;
    }
}

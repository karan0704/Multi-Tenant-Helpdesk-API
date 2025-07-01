package com.karan.helpdesk.repository;

import com.karan.helpdesk.entity.Ticket;
import com.karan.helpdesk.entity.Tenant;
import com.karan.helpdesk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findByTenant(Tenant tenant);
    List<Ticket> findByAssignedTo(User user);
    List<Ticket> findByCreatedBy(User user);
}

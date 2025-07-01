package com.karan.helpdesk.dto;

import com.karan.helpdesk.entity.Ticket;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private UUID id;
    private String title;
    private String status;
    private String priority;
    private String createdBy;
    private String assignedTo;

    public static TicketDTO fromEntity(Ticket ticket) {
        return TicketDTO.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .status(ticket.getStatus().name())
                .priority(ticket.getPriority().name())
                .createdBy(ticket.getCreatedBy().getFullName())
                .assignedTo(ticket.getAssignedTo() != null ? ticket.getAssignedTo().getFullName() : null)
                .build();
    }
}

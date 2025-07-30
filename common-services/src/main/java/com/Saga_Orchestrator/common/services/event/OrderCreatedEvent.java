package com.Saga_Orchestrator.common.services.event;


import com.Saga_Orchestrator.common.services.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
    private Long orderId;
    private Long userId;
    private List<BookDTO> books;


}


package com.Saga_Orchestrator.common.services.event;


import com.Saga_Orchestrator.common.services.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSelectedEvent {
    private Long userId;
    private List<BookDTO> selectedBooks;

    // Optional custom getter (only if you prefer getBooks() instead of getSelectedBooks())
    public List<BookDTO> getBooks() {
        return selectedBooks;
    }
}

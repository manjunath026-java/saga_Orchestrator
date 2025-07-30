package com.saga.book_service.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEventListener {
    private String orderId;
    private String bookId;
    private String userId;
    private String status;
    // Add other fields if needed
}


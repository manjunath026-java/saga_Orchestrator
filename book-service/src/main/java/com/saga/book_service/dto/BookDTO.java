package com.saga.book_service.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {
    private String bookId;
    private String title;
    private double price;
    private int quantity;

}
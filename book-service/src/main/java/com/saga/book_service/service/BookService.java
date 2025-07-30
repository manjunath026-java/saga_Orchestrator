package com.saga.book_service.service;


import com.Saga_Orchestrator.common.services.dto.BookDTO;
import com.saga.book_service.entity.Book;
import com.saga.book_service.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final KafkaTemplate<String, String> kafkaTemplate;








    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    public BookService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public List<BookDTO> getBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(book -> BookDTO.builder()
                        .bookId(book.getId())
                        .title(book.getTitle())
                        .price(book.getPrice())
                        .quantity(book.getStock())
                        .build())
                .collect(Collectors.toList());
    }
        public List<BookDTO> getAllBooks() {
            return bookRepository.findAll()
                    .stream()
                    .map(book -> modelMapper.map(book, BookDTO.class))
                    .collect(Collectors.toList());
        }



    public Book saveBook(BookDTO dto) {
        Book book = Book.builder()
                .title(dto.getTitle())
                .stock(dto.getQuantity())
                .price(dto.getPrice())
                .build();

        return bookRepository.save(book);
    }

    public void sendBookEvent(String bookTitle) {
        kafkaTemplate.send("book-topic", bookTitle)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println(" Sent to Kafka successfully");
                    } else {
                        System.err.println(" Failed to send to Kafka: " + ex.getMessage());
                    }
                });
    }


}

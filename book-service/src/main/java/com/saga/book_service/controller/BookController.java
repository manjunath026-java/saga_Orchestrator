package com.saga.book_service.controller;



import com.Saga_Orchestrator.common.services.dto.BookDTO;
import com.saga.book_service.entity.Book;
import com.saga.book_service.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {

        return ResponseEntity.ok(bookService.getBooks());
    }
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody BookDTO bookDTO) {
        Book savedBook = bookService.saveBook(bookDTO);
        return ResponseEntity.ok(savedBook);
    }



}

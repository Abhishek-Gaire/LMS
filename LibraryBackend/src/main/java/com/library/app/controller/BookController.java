package com.library.app.controller;

import com.library.app.dto.BookDTO;

import com.library.app.service.BookService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
@AllArgsConstructor

public class BookController {

    private BookService bookService;

    @PostMapping("addBook")
    // http://localhost:8080/api/books/addBook
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO){

        BookDTO savedBookDTO = bookService.addBook(bookDTO);
        return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
    }

    @GetMapping("listAllBooks")
    // http://localhost:8080/api/books/listAllBooks
    public ResponseEntity<List<BookDTO>> getAllBooks(){
        List<BookDTO> allBooks = bookService.getAllBooks();
        return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }

    @GetMapping("{id}")
    // http://localhost:8080/api/books/1
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") long bookId){
        BookDTO bookDTO = bookService.getBookById(bookId);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }
}

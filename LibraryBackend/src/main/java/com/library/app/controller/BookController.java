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

    @PatchMapping("updateBook/{id}")
    //http://localhost:8080/api/books/updateBook/1
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO){

        bookDTO.setId(id);
        BookDTO updatedBook = bookService.updateBook(bookDTO);

        return new ResponseEntity<>(updatedBook,HttpStatus.OK);
    }

    @DeleteMapping("deleteBook/{id}")
    //http://localhost:8080/api/books/deleteBook/3
    public ResponseEntity<String> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return new ResponseEntity<>("Book Successfully Deleted", HttpStatus.OK);
    }

    @GetMapping("search-title")
    //http://localhost:8080/api/books/search-title?title=Lord of The Rings

    public ResponseEntity<List<BookDTO>> searchBookByTitle(@RequestParam String title){
        List<BookDTO> books = bookService.findBooksByTitle(title);

        return new ResponseEntity<>(books,HttpStatus.OK);
    }

    @GetMapping("search-title-author")
    //http://localhost:8080/api/books/search-title-author?title=Lord&author=tolk

    public ResponseEntity<List<BookDTO>> searchBookByTitleAndAuthor(@RequestParam String title,@RequestParam String author){
        List<BookDTO> books = bookService.findBooksByTitleAndAuthor(title,author);

        return new ResponseEntity<>(books,HttpStatus.OK);
    }

    @GetMapping("search")
    // http:localhost:8080/api/books/search?title=lord&author=tolk
    public ResponseEntity<List<BookDTO>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String barcodeNumber){
        List<BookDTO> books = bookService.findBooksByCriteria(title,author,isbn,barcodeNumber);

        return new ResponseEntity<>(books,HttpStatus.OK);
    }

}

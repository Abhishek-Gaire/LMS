package com.library.app.service;

import com.library.app.dto.BookDTO;

import java.util.List;


public interface BookService {

    BookDTO addBook(BookDTO bookDTO);

    List<BookDTO> getAllBooks();

    BookDTO getBookById (Long bookId);
}

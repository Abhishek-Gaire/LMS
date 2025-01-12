package com.library.app.service.impl;

import com.library.app.dto.BookDTO;
import com.library.app.entity.Book;
import com.library.app.mapper.BookMapper;
import com.library.app.repository.BookRepository;
import com.library.app.service.BookService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        // save (CREATE) this book in DB

        Book book = BookMapper.mapToBookEntity(bookDTO);
        book = bookRepository.save(book);

        return BookMapper.mapToBookDTO(book);
    }

    @Override
    public List<BookDTO> getAllBooks() {

        // returns all the books in the database
        List<Book> books = bookRepository.findAll();

        // we have to iterate over the list of entities
        // then map every entity to dto
        // then return list of dto
        return books.stream()
                .map(BookMapper::mapToBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.get();

        return BookMapper.mapToBookDTO(book);
    }
}

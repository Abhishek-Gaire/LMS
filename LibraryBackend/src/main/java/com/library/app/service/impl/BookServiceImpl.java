package com.library.app.service.impl;

import com.library.app.dto.BookDTO;
import com.library.app.entity.Book;
import com.library.app.mapper.BookMapper;
import com.library.app.repository.BookRepository;
import com.library.app.service.BookService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private EntityManager entityManager;
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

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {
        // find the existing book by ID
        Optional<Book> bookOptional = bookRepository.findById(bookDTO.getId());

        // do partial update of the book
        Book bookToUpdate = bookOptional.get();
        updateBookEntityFromDTO(bookToUpdate,bookDTO);

        // save the updated book to a database
        Book savedBook = bookRepository.save(bookToUpdate);

        // return bookDTO using mapper
        return BookMapper.mapToBookDTO(savedBook);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<BookDTO> findBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        return books.stream()
                .map(BookMapper::mapToBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findBooksByTitleAndAuthor(String title, String author) {
        List<Book> books = bookRepository.findByTitleAndAuthorContainingIgnoreCase(title,author);
        return books.stream()
                .map(BookMapper::mapToBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findBooksByCriteria(String title, String author,String isbn, String barcodeNumber) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> book = cq.from(Book.class);

        List<Predicate> predicates = new ArrayList<>();
        if(title != null && !title.isEmpty()){
            predicates.add(cb.like(cb.lower(book.get("title")), "%"  + title.toLowerCase() + "%"));
        }
        if (author !=null && !author.isEmpty()){
            predicates.add(cb.like(cb.lower(book.get("author")), "%" + author.toLowerCase() + "%"));
        }
        if (isbn !=null && !isbn.isEmpty()){
            predicates.add(cb.like(cb.lower(book.get("isbn")), "%" + isbn.toLowerCase() + "%"));
        }
        if (barcodeNumber !=null && !barcodeNumber.isEmpty()){
            predicates.add(cb.like(cb.lower(book.get("barcodeNumber")), "%" + barcodeNumber.toLowerCase() + "%"));
        }
        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        List<Book> result = entityManager.createQuery(cq).getResultList();

        return result.stream()
                .map(BookMapper::mapToBookDTO)
                .collect(Collectors.toList());
    }

    private void updateBookEntityFromDTO(Book bookToUpdate, BookDTO bookDTO) {
        if(bookDTO.getTitle() != null){
            bookToUpdate.setTitle(bookDTO.getTitle());
        }
        if(bookDTO.getAuthor() != null){
            bookToUpdate.setAuthor(bookDTO.getAuthor());
        }
        if(bookDTO.getIsbn() != null){
            bookToUpdate.setIsbn(bookDTO.getIsbn());
        }
        if(bookDTO.getPublisher()!=null){
            bookToUpdate.setPublisher(bookDTO.getPublisher());
        }
        if(bookDTO.getBarcodeNumber() != null){
            bookToUpdate.setBarcodeNumber(bookDTO.getBarcodeNumber());
        }
        if(bookDTO.getYearOfPublication()!=null){
            bookToUpdate.setYearOfPublication(bookDTO.getYearOfPublication());
        }
        if(bookDTO.getPlaceOfPublication() != null){
            bookToUpdate.setPlaceOfPublication(bookDTO.getPlaceOfPublication());
        }
        if(bookDTO.getNoOfAvailableCopies() != null){
            bookToUpdate.setNoOfAvailableCopies(bookDTO.getNoOfAvailableCopies());
        }
    }
}

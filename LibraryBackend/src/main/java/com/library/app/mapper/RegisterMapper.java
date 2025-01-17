package com.library.app.mapper;

import com.library.app.dto.RegisterDTO;
import com.library.app.entity.Book;
import com.library.app.entity.CheckoutRegister;
import com.library.app.entity.Member;
import com.library.app.repository.BookRepository;
import com.library.app.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Component

public class RegisterMapper {

    private MemberRepository memberRepository;
    private BookRepository bookRepository;

    //map entity to dto
    public RegisterDTO mapToRegisterDTO(CheckoutRegister checkoutRegister){
        RegisterDTO dto = new RegisterDTO();

        dto.setId(checkoutRegister.getId());
        dto.setBookId(checkoutRegister.getBook().getId());
        dto.setMemberId(checkoutRegister.getMember().getId());

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        dto.setDueDate(checkoutRegister.getDueDate().format(formatter));
        dto.setCheckoutDate(checkoutRegister.getCheckoutDate().format(formatter));
        if(checkoutRegister.getReturnDate() != null)
            dto.setReturnDate(checkoutRegister.getReturnDate().format(formatter));

        dto.setOverdueFine(checkoutRegister.getOverdueFine());
        return dto;
    }
    //map dto to entity
    public CheckoutRegister mapToCheckoutRegisterEntity(RegisterDTO registerDTO){
        CheckoutRegister register = new CheckoutRegister();

        register.setId(registerDTO.getId());
        Member member = memberRepository.findById(registerDTO.getMemberId()).get();
        register.setMember(member);
        Book book = bookRepository.findById(registerDTO.getBookId()).get();
        register.setBook(book);


        register.setCheckoutDate(LocalDate.parse(registerDTO.getCheckoutDate()));
        if(registerDTO.getDueDate() != null )
            register.setDueDate(LocalDate.parse(registerDTO.getDueDate()));
        if(registerDTO.getReturnDate() != null)
            register.setReturnDate(LocalDate.parse(registerDTO.getReturnDate()));

        register.setOverdueFine(registerDTO.getOverdueFine());
        return register;
    }
}

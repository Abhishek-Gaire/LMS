package com.library.app.service.impl;

import com.library.app.dto.RegisterDTO;
import com.library.app.entity.CheckoutRegister;
import com.library.app.mapper.RegisterMapper;
import com.library.app.repository.CheckoutRegisterRepository;
import com.library.app.service.RegisterService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    @Value("${library.loanPeriodInDays}")
    private int loanPeriodInDays;

    private final RegisterMapper registerMapper;
    private final CheckoutRegisterRepository registerRepository;

    @Override
    public RegisterDTO createRegister(RegisterDTO registerDTO) {
        CheckoutRegister checkoutRegister = registerMapper.mapToCheckoutRegisterEntity(registerDTO);

        // calculate due date
        LocalDate dueDate = calculateDueDate(checkoutRegister.getCheckoutDate());
        checkoutRegister.setDueDate(dueDate);

        checkoutRegister = registerRepository.save(checkoutRegister);
        return registerMapper.mapToRegisterDTO(checkoutRegister);
    }

    @Override
    public List<RegisterDTO> getAllRegisters() {
        List<CheckoutRegister> registers = registerRepository.findAll();
        return registers.stream()
                .map(registerMapper::mapToRegisterDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RegisterDTO getRegisterById(Long id) {
        Optional<CheckoutRegister> optionalRegister = registerRepository.findById(id);
        CheckoutRegister register = optionalRegister.get();
        return registerMapper.mapToRegisterDTO(register);
    }
    private LocalDate calculateDueDate(LocalDate checkoutDate) {
        return checkoutDate.plusDays(loanPeriodInDays);
    }
}
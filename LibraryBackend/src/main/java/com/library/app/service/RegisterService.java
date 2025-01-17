package com.library.app.service;

import com.library.app.dto.RegisterDTO;
import com.library.app.entity.CheckoutRegister;

import java.util.List;

public interface RegisterService {

    RegisterDTO createRegister(RegisterDTO registerDTO);

    List<RegisterDTO> getAllRegisters();

    RegisterDTO getRegisterById(Long id);
}

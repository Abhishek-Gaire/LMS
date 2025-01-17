package com.library.app.controller;

import com.library.app.dto.RegisterDTO;
import com.library.app.service.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/registers")
public class RegistryController {
    private RegisterService registerService;

    @PostMapping("createRegister")
    // http:localhost:8080/api/members/createRegister
    public ResponseEntity<RegisterDTO> createRegister(@RequestBody RegisterDTO registerDTO){
        RegisterDTO register =registerService.createRegister(registerDTO);
        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }

    @GetMapping("listAll")
    // http:localhost:8080/api/registers/listALl
    public ResponseEntity<List<RegisterDTO>> getAllRegisters(){
        List<RegisterDTO> registers = registerService.getAllRegisters();
        return new ResponseEntity<>(registers,HttpStatus.OK);
    }

    @GetMapping("{id}")
    // http:localhost:8080/api/registers/1
    public ResponseEntity<RegisterDTO> getRegisterById(@PathVariable Long id){
        RegisterDTO register =registerService.getRegisterById(id);
        return new ResponseEntity<>(register,HttpStatus.OK);
    }
}

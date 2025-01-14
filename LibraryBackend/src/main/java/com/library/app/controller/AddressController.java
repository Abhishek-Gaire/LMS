package com.library.app.controller;

import com.library.app.dto.AddressDTO;
import com.library.app.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/addresses")

public class AddressController {
    private AddressService addressService;

    @PostMapping("createAddress")
    // http://localhost:8080/api/addresses/createAddress
    public ResponseEntity<AddressDTO> addAddress(@RequestBody AddressDTO addressDTO){
        AddressDTO savedAddressDTO = addressService.createAddress(addressDTO);
        return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);
    }

    @GetMapping("listAllAddresses")
    // http://localhost:8080/api/addresses/listAllAddresses
    public ResponseEntity<List<AddressDTO>> getAllAddresses(){
        List<AddressDTO> allAddresses = addressService.getAllAddresses();
        return new ResponseEntity<>(allAddresses,HttpStatus.OK);
    }

    @GetMapping("{id}")
    // http://localhost:8080/api/addresses/1
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id){
        AddressDTO addressDTO = addressService.getAddressById(id);
        return new ResponseEntity<>(addressDTO,HttpStatus.OK);
    }

    @PatchMapping("updateAddress/{id}")
    // http://localhost:8080/api/addresses/updateAddress/1

    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO){
        addressDTO.setId(id);
        AddressDTO updatedAddress = addressService.updateAddress(addressDTO);

        return new ResponseEntity<>(updatedAddress,HttpStatus.OK);
    }

    @DeleteMapping("deleteAddress/{id}")
    // http://localhost:8080/api/addresses/deletedAddress/1
    public ResponseEntity<String> deleteAddress(@PathVariable Long id){
        addressService.deleteAddress(id);

        return new ResponseEntity<>("Address Deleted Successfully",HttpStatus.OK);
    }
}

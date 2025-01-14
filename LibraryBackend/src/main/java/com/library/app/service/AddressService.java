package com.library.app.service;

import com.library.app.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    AddressDTO createAddress(AddressDTO addressDTO);

    List<AddressDTO> getAllAddresses();

    AddressDTO getAddressById(Long id);

    AddressDTO updateAddress(AddressDTO addressDTO);

    void deleteAddress(Long id);
}

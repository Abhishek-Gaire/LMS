package com.library.app.service.impl;

import com.library.app.dto.AddressDTO;
import com.library.app.entity.PostalAddress;
import com.library.app.mapper.AddressMapper;
import com.library.app.repository.AddressRepository;
import com.library.app.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        PostalAddress postalAddress = AddressMapper.mapToAddressEntity(addressDTO);

        postalAddress = addressRepository.save(postalAddress); // save out entity to database
        return AddressMapper.mapToAddressDTO(postalAddress);
    }

    @Override
    public List<AddressDTO> getAllAddresses() {

        List<PostalAddress> addresses = addressRepository.findAll();

        return addresses.stream()
                .map(AddressMapper::mapToAddressDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDTO getAddressById(Long id) {
        Optional<PostalAddress> optionalAddress = addressRepository.findById(id);
        PostalAddress postalAddress = optionalAddress.get();

        return AddressMapper.mapToAddressDTO(postalAddress);
    }

    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO) {

        Optional<PostalAddress> addressOptional= addressRepository.findById(addressDTO.getId());

        PostalAddress addressToUpdate = addressOptional.get();
        updateAddressEntityFromDTO(addressToUpdate,addressDTO);

        PostalAddress updatedAddress = addressRepository.save(addressToUpdate);

        return AddressMapper.mapToAddressDTO(updatedAddress);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    public void updateAddressEntityFromDTO(PostalAddress addressToUpdate, AddressDTO addressDTO) {
        if(addressDTO.getCountry() != null){
            addressToUpdate.setCountry(addressDTO.getCountry());
        }
        if(addressDTO.getStreetName() != null ){
            addressToUpdate.setStreetName(addressDTO.getStreetName());
        }
        if(addressDTO.getStreetNumber() != null){
            addressToUpdate.setStreetNumber(addressDTO.getStreetNumber());
        }
        if(addressDTO.getZipCode() != null ){
            addressToUpdate.setZipCode(addressDTO.getZipCode());
        }
        if(addressDTO.getPlaceName() != null){
            addressToUpdate.setPlaceName(addressDTO.getPlaceName());
        }
        if(addressDTO.getAdditionalInfo() != null){
            addressToUpdate.setAdditionalInfo(addressDTO.getAdditionalInfo());
        }
    }
}

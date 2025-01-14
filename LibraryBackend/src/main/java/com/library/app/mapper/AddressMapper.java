package com.library.app.mapper;

import com.library.app.dto.AddressDTO;
import com.library.app.entity.PostalAddress;

public class AddressMapper {

    // method to map entity to dto
    public static AddressDTO mapToAddressDTO(PostalAddress postalAddress){
        AddressDTO dto = new AddressDTO();
        dto.setId(postalAddress.getId());
        dto.setStreetName(postalAddress.getStreetName());
        dto.setStreetNumber(postalAddress.getStreetNumber());
        dto.setCountry(postalAddress.getCountry());
        dto.setZipCode(postalAddress.getZipCode());
        dto.setPlaceName(postalAddress.getPlaceName());
        dto.setAdditionalInfo(postalAddress.getAdditionalInfo());
        return dto;
    }
    //method to map dto to entity
    public static PostalAddress mapToAddressEntity(AddressDTO addressDTO){
        PostalAddress postalAddress = new PostalAddress();
        postalAddress.setId(addressDTO.getId());
        postalAddress.setStreetName(addressDTO.getStreetName());
        postalAddress.setStreetNumber(addressDTO.getStreetNumber());
        postalAddress.setZipCode(addressDTO.getZipCode());
        postalAddress.setPlaceName(addressDTO.getPlaceName());
        postalAddress.setCountry(addressDTO.getCountry());
        postalAddress.setAdditionalInfo(addressDTO.getAdditionalInfo());
        return postalAddress;
    }
}

package com.library.app.mapper;

import com.library.app.dto.MemberDTO;
import com.library.app.entity.Member;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MemberMapper {
    // to map member entity to dto
    public static MemberDTO mapToMemberDTO(Member member){
            MemberDTO dto = new MemberDTO();

            dto.setId(member.getId());
            dto.setFirstName(member.getFirstname());
            dto.setLastName(member.getLastName());

            // we need to store LocalDate as String
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            if(member.getDateOfBirth() != null )
                dto.setDateOfBirth(member.getDateOfBirth().format(formatter));

            // map postal address
            if (member.getPostalAddress() != null)
                dto.setAddress(AddressMapper.mapToAddressDTO(member.getPostalAddress()));

            dto.setEmail(member.getEmail());
            dto.setPhone(member.getPhone());
            dto.setBarcodeNumber(member.getBarcodeNumber());

            if(member.getMembershipEnded() != null)
                dto.setMembershipEnded(member.getMembershipEnded().format(formatter));
            if(member.getMembershipStarted() != null)
                dto.setMembershipStarted(member.getMembershipStarted().format(formatter));

            dto.setIsActive(member.getIsActive());
            return dto;
    }

    // to map dto to entity
    public static Member mapToMemberEntity(MemberDTO memberDTO){
        Member member = new Member();

        member.setId(memberDTO.getId());
        member.setFirstname(memberDTO.getLastName());
        member.setLastName(memberDTO.getLastName());

        // map String from dto to LocalDate in entity
        member.setDateOfBirth(LocalDate.parse(memberDTO.getDateOfBirth()));

        //postal address mapping
        if(memberDTO.getAddress() != null)
            member.setPostalAddress(AddressMapper.mapToAddressEntity(memberDTO.getAddress()));

        member.setEmail(memberDTO.getEmail());
        member.setPhone(memberDTO.getPhone());
        member.setBarcodeNumber(memberDTO.getBarcodeNumber());

        if(memberDTO.getMembershipEnded() != null)
        member.setMembershipEnded(LocalDate.parse(memberDTO.getMembershipEnded()));
        member.setMembershipStarted(LocalDate.parse(memberDTO.getMembershipStarted()));

        member.setIsActive(memberDTO.getIsActive());
        return member;
    }
}

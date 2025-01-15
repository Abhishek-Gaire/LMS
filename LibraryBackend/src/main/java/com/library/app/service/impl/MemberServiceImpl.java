package com.library.app.service.impl;

import com.library.app.dto.AddressDTO;
import com.library.app.dto.MemberDTO;
import com.library.app.entity.Member;
import com.library.app.entity.PostalAddress;
import com.library.app.mapper.AddressMapper;
import com.library.app.mapper.MemberMapper;
import com.library.app.repository.AddressRepository;
import com.library.app.repository.MemberRepository;
import com.library.app.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class MemberServiceImpl implements MemberService {

    private AddressRepository addressRepository;

    private MemberRepository memberRepository;

    @Override
    public MemberDTO addMember(MemberDTO memberDTO) {
        PostalAddress postalAddress = new PostalAddress();
        // first we have to deal with postal address

        AddressDTO addressDTO = memberDTO.getAddress();
        if(addressDTO != null ) {
            postalAddress = AddressMapper.mapToAddressEntity(addressDTO);
            postalAddress = addressRepository.save(postalAddress);
        }

        // convert memberDTO to member Entity
        Member member = MemberMapper.mapToMemberEntity(memberDTO);

        // add the address in the Entity
        if(postalAddress != null ) member.setPostalAddress(postalAddress);

        // save the entity in dm
        member = memberRepository.save(member);

        // convert the Entity back to dto and return it
        return MemberMapper.mapToMemberDTO(member);
    }
}

package com.library.app.service;

import com.library.app.dto.MemberDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {

    MemberDTO addMember(MemberDTO memberDTO);

    List<MemberDTO> getAllMembers();

    MemberDTO getMemberById(Long id);

    MemberDTO updateMember(MemberDTO memberDTO);

   void deleteMember(Long id);

   List<MemberDTO> findMembersByCriteria(Long id ,String firstName, String lastName,String barcodeNumber);
}

package com.library.app.service;

import com.library.app.dto.MemberDTO;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    MemberDTO addMember(MemberDTO memberDTO);
}

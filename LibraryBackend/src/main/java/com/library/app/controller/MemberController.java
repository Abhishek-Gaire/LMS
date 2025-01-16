package com.library.app.controller;

import com.library.app.dto.MemberDTO;
import com.library.app.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/members")

public class MemberController {
    private MemberService memberService;

    @PostMapping("addMember")
    // http://localhost:8080/api/members/addMember
    public ResponseEntity<MemberDTO> addMember(@RequestBody MemberDTO memberDTO){
        MemberDTO savedMember = memberService.addMember(memberDTO);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    @GetMapping("listAll")
    // http://localhost:8080/api/memebers/listAll
    public ResponseEntity<List<MemberDTO>> getAllMembers(){
        List<MemberDTO> allMembers = memberService.getAllMembers();
        return new ResponseEntity<>(allMembers,HttpStatus.OK);
    }

    @GetMapping("{id}")
    // http://localhost:8080/api/members/1
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id){
       MemberDTO memberDTO= memberService.getMemberById(id);
       return new ResponseEntity<>(memberDTO,HttpStatus.OK);
    }

    @PatchMapping("updateMember/{id}")
    // http://localhost:8080/api/members/updateMember/1
    public ResponseEntity<MemberDTO> updateMemberById(@PathVariable Long id, @RequestBody MemberDTO memberDTO){
        memberDTO.setId(id);
        MemberDTO updatedMember = memberService.updateMember(memberDTO);
        return new ResponseEntity<>(updatedMember,HttpStatus.OK);
    }

    @DeleteMapping("deleteMember/{id}")
    // http://localhost:8080/api/members/deleteMember/1
    public ResponseEntity<String> deleteMemberById(@PathVariable Long id){
        memberService.deleteMember(id);
        return new ResponseEntity<>("Member Deleted Successfully",HttpStatus.OK);
    }
    @GetMapping("search")
    // http://localhost:8080/api/members/search?firstName=hi&lastName=hijk
    public ResponseEntity<List<MemberDTO>> searchMembers(
            @RequestParam(required = false) Long cardNumber,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String barcodeNumber
    ){
        List<MemberDTO> members = memberService.findMembersByCriteria(cardNumber,firstName,lastName,barcodeNumber);
        return new ResponseEntity<>(members,HttpStatus.OK);
    }
}

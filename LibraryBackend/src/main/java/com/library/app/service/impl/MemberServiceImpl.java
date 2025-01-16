package com.library.app.service.impl;

import com.library.app.dto.AddressDTO;
import com.library.app.dto.MemberDTO;
import com.library.app.entity.Member;
import com.library.app.entity.PostalAddress;
import com.library.app.mapper.AddressMapper;
import com.library.app.mapper.MemberMapper;
import com.library.app.repository.AddressRepository;
import com.library.app.repository.MemberRepository;
import com.library.app.service.AddressService;
import com.library.app.service.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class MemberServiceImpl implements MemberService {

    private AddressRepository addressRepository;

    private MemberRepository memberRepository;

    private AddressServiceImpl addressServiceImpl;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
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

    @Override
    public List<MemberDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberMapper::mapToMemberDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MemberDTO getMemberById(Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        Member member = optionalMember.get();

        return MemberMapper.mapToMemberDTO(member);
    }

    @Override
    @Transactional
    public MemberDTO updateMember(MemberDTO memberDTO) {
        // find existing member by id
        Optional <Member> optionalMember = memberRepository.findById(memberDTO.getId());
        Member memberToUpdate = optionalMember.get();

        // do partial update of the member(only non-nullable)
        updateMemberEntityFromDTO(memberToUpdate,memberDTO);

        // save updated member to db
        memberToUpdate = memberRepository.save(memberToUpdate);

        // return updated memberDTO
        return MemberMapper.mapToMemberDTO(memberToUpdate);
    }

    @Override
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public List<MemberDTO> findMembersByCriteria(Long id, String firstName, String lastName, String barcodeNumber) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        Root<Member> memberRoot = cq.from(Member.class);
        List<Predicate> predicates = new ArrayList<>();

        if(id != null) predicates.add(cb.equal(memberRoot.get("id"),id));
        if(firstName != null)
            predicates.add(cb.like(cb.lower(memberRoot.get("firstName")),"%" + firstName.toLowerCase() + "%"));
        if(lastName != null)
            predicates.add(cb.like(cb.lower(memberRoot.get("lastName")),"%" + lastName.toLowerCase() + "%"));
        if(barcodeNumber != null)
            predicates.add(cb.like(cb.lower(memberRoot.get("barcodeNumber")),"%" + barcodeNumber.toLowerCase() + "%"));

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        List<Member> result = entityManager.createQuery(cq).getResultList();

        return result.stream()
                .map(MemberMapper::mapToMemberDTO)
                .collect(Collectors.toList());
    }

    private void updateMemberEntityFromDTO(Member memberToUpdate, MemberDTO memberDTO) {
        if(memberDTO.getFirstName() != null) memberToUpdate.setFirstname(memberDTO.getFirstName());
        if(memberDTO.getLastName() != null ) memberToUpdate.setLastName(memberDTO.getLastName());
        if(memberDTO.getDateOfBirth() != null) memberToUpdate.setDateOfBirth(LocalDate.parse(memberDTO.getDateOfBirth()));
        if(memberDTO.getEmail() != null) memberToUpdate.setEmail(memberDTO.getEmail());
        if(memberDTO.getPhone() != null) memberToUpdate.setPhone(memberDTO.getPhone());
        if(memberDTO.getBarcodeNumber() != null ) memberToUpdate.setBarcodeNumber(memberDTO.getBarcodeNumber());
        if(memberDTO.getMembershipStarted() != null ) memberToUpdate.setMembershipStarted(LocalDate.parse(memberDTO.getMembershipStarted()));

        // membership is active if membershipEnded = null
        if(memberDTO.getMembershipEnded() != null) {
            if(memberDTO.getMembershipStarted().isEmpty()){
                memberToUpdate.setMembershipEnded(null);
                memberToUpdate.setIsActive(true);
            } else {
                memberToUpdate.setMembershipEnded(LocalDate.parse(memberDTO.getMembershipEnded()));
                memberToUpdate.setIsActive(false);
            }
        }

        //updating the address
        if(memberDTO.getAddress() != null){
            // if the member already has the address,update it
            // otherwise create a new PostalEntity
            PostalAddress addressToUpdate;

            if(memberToUpdate.getPostalAddress() != null){
                addressToUpdate= memberToUpdate.getPostalAddress();
            } else {
                addressToUpdate = new PostalAddress();
            }
            // to update postal address entity we will use existing address service
            addressServiceImpl.updateAddressEntityFromDTO(addressToUpdate,memberDTO.getAddress());

            // save the updated address to database
            addressRepository.save(addressToUpdate);

            // associate the updated address to Member
            memberToUpdate.setPostalAddress(addressToUpdate);
        }

    }
}

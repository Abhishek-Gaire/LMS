package com.library.app.repository;

import com.library.app.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}

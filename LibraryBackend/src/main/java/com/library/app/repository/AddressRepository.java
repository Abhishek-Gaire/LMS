package com.library.app.repository;

import com.library.app.entity.PostalAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<PostalAddress,Long> {
}

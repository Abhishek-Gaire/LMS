package com.library.app.repository;

import com.library.app.entity.CheckoutRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRegisterRepository extends JpaRepository<CheckoutRegister,Long> {
}

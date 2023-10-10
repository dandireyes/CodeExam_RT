package com.risingtide.codeexam.repository;

import com.risingtide.codeexam.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {


}

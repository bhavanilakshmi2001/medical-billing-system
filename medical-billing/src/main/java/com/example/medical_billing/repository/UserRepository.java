package com.example.medical_billing.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.medical_billing.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
User findByEmail(String email);
}

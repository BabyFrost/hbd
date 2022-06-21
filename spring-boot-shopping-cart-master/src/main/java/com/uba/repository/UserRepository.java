package com.uba.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uba.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Param("email") String email);
    Optional<User> findByStaffid(@Param("staffid") String staffid);
    Optional<User> findByUsername(@Param("username") String username);
   
    @Query("SELECT u FROM User u WHERE u.active = 1")
    Page<User> findAllAUsers(Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE u.active = 1")
    List<User> findAUsers();
}

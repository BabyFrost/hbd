package com.uba.hbd.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uba.hbd.model.Staff;

@Repository
public interface StaffRepository extends CrudRepository< Staff, String> {

	Optional<Staff> findByUsername(String username);

}

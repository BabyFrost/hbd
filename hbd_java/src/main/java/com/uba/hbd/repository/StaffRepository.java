package com.uba.hbd.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uba.hbd.model.Staff;

@Repository
public interface StaffRepository extends CrudRepository< Staff, String> {

}

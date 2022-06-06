package com.uba.hbd.repository;

import org.springframework.data.repository.CrudRepository;

import com.uba.hbd.model.User;

public interface UserRepository extends CrudRepository< User, String> {

}

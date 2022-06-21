package com.uba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uba.model.FileDB;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, Long> {

}
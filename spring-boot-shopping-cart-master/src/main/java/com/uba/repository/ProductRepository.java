package com.uba.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uba.model.Product;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    
    @Query("SELECT p FROM Product p WHERE p.quantity > 0")
    Page<Product> findAllAvailableProducts(Pageable pageable);
    
    
    Page<Product> findByBidEndDateBefore(Date currentDate,Pageable pageable);
    
    @Query("select p FROM Product p WHERE p.bidEnd >= :currentDate and p.bidStart <= :currentDate")
    Page<Product> findAllWithEndBefore(@Param("currentDate") Date currentDate,Pageable pageable);
    
    @Query("select p FROM Product p WHERE p.bidEnd >= :currentDate and p.bidStart <= :currentDate")
    List<Product> findAllProducts(@Param("currentDate") Date currentDate);
    
    @Query("select p FROM Product p WHERE p.bidEnd >= :currentDate and p.bidStart <= :currentDate and p.id = :id")
    List<Product> findAllWith(@Param("currentDate") Date currentDate,@Param("id") Long id);
    
    //TimeBetween
}

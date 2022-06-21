package com.uba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.uba.model.CartItem;
import com.uba.model.User;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	List<CartItem> findByUser(User user);
}

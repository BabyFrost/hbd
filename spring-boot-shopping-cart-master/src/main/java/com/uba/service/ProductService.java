package com.uba.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.uba.model.Bid;
import com.uba.model.Bidder;
import com.uba.model.Product;
import com.uba.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> findById(Long id);

    Page<Product> findAllProductsPageable(Pageable pageable);
    
    void saveProduct(Product product);
    
    int getDays(LocalDate start, LocalDate end);
    List<Bid> findBidsByUser(User user);	
	List<Bid> findBidsByProduct(Product product); 
	Bid getWinningBid(Product product);
	Bid getWinniner(Product product); 
	Bid getCurrentBidder(BigDecimal amount,User user);
	BigDecimal getHighestBid(Product product);	
	boolean placeBid(Product product, User user, double amount);
	boolean retractBid(Bid bid, Product prod);
	long getPeriod(Date start, Date end) ;
	List<Bid> findByAmountAndUserList(BigDecimal amount,User user);
	
	List<Bidder> highestBiddersPerProduct();
	
	Bidder getHighestBidder(Product product);

}

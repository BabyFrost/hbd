package com.uba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

import com.uba.model.Bid;
import com.uba.model.Product;
import com.uba.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {
	
	List<Bid> findByUser(User user);
	
	@Query("SELECT B FROM Bid B WHERE B.product =?1 and B.active=1")
	List<Bid> findByActiveProduct(Product product);
	//@Query("select p FROM Product p WHERE p.bidEnd >= :currentDate and p.bidStart <= :currentDate")
	
	@Query("SELECT distinct b FROM  Bid b WHERE b.amount = (SELECT MAX(B2.amount) FROM Bid B2 WHERE B2.product =?1 and B2.active=1)")
	Optional<Bid> findHighestOffer(Product product);
	
	@Query("SELECT b FROM  Bid b WHERE b.amount = (SELECT MAX(B2.amount) FROM Bid B2 WHERE B2.product =?1 and B2.active=1)")
	List<Bid> findHighestOfferList(Product product);
	
	@Query("SELECT MAX(B.amount) FROM Bid B WHERE B.product =?1 and B.active=1")
	BigDecimal getHighestBid(Product product);
	
	@Query("SELECT b FROM  Bid b WHERE b.amount =?1 and b.user=?2 and b.active=1")
	Optional<Bid> findByAmountAndUser(BigDecimal amount,User user);
	
	@Query("SELECT b FROM  Bid b WHERE b.amount =?1 and b.user=?2 and b.active=1")
	List<Bid> findByAmountAndUserList(BigDecimal amount,User user);
	
	Optional<Bid> findByAmount(BigDecimal amount);
	//@Query("SELECT MAX(amount) AS amount FROM bid where product_id=?1")
	

}

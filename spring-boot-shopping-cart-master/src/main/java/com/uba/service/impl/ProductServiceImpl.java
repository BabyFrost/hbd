package com.uba.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.uba.model.Bid;
import com.uba.model.Bidder;
import com.uba.model.Product;
import com.uba.model.User;
import com.uba.repository.BidRepository;
import com.uba.repository.ProductRepository;
import com.uba.service.ProductService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BidRepository bidRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,BidRepository bidRepository) {
        this.productRepository = productRepository;
        this.bidRepository=bidRepository;
    }

    @Override
    public Page<Product> findAllProductsPageable(Pageable pageable) {
    	Date today=new Date(System.currentTimeMillis());
        return productRepository.findAllWithEndBefore(today,pageable);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
    @Override
	public void saveProduct(Product product) {
		// TODO Auto-generated method stub
		productRepository.save(product);
	}
    
    @Override
    public int getDays(LocalDate start, LocalDate end) {
		// TODO Auto-generated method stub   
    	    Period period = Period.between(start, end);
    	    int days = period.getDays();	    
    	    return days;
	}
    
    
    public long getPeriod(Date start, Date end) {
		// TODO Auto-generated method stub   
    	 long time_difference = end.getTime() - start.getTime();  
    	    // Calucalte time difference in days  
    	 long days_difference = (time_difference / (1000*60*60*24)) % 365;;	    
    	 return days_difference;
	}
    
    
      
    
    public boolean isProductAvailable(Product product) {
		// TODO Auto-generated method stub 
    	boolean value = false;
    	Date today = new Date(System.currentTimeMillis());
    	
    	List<Product> prods = productRepository.findAllWith(today, product.getId());

    	    if(prods.size()>0)
    	    	value = true;
    	    System.out.println("This is  :"+value); 
    	    System.out.println("This is  :"+today); 
    	    System.out.println("This is  :"+product.getBidEnd()); 
    	    
    	    return value;
	}
    
    

	@Override
	public List<Bid> findBidsByUser(User user) {
		// TODO Auto-generated method stub
		return bidRepository.findByUser(user);
	}

	@Override
	public List<Bid> findBidsByProduct(Product product) {
		// TODO Auto-generated method stub
		return bidRepository.findByActiveProduct(product);
	}

	@Override
	public BigDecimal getHighestBid(Product product) {
		// TODO Auto-generated method stub
		return bidRepository.getHighestBid(product);
	}

	@Override
	public Bid getWinningBid(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bid getWinniner(Product product) {
		// TODO Auto-generated method stub
		BigDecimal highestBid = bidRepository.getHighestBid(product);
		Bid winningBid = bidRepository.findByAmount(highestBid).get();
		return winningBid;
	}

	
	/*
	 * public Bid placeBid(Product product, User user, double amount) { // TODO
	 * Auto-generated method stub; //Ensure product is available for bidding and the
	 * bidder is giving a higher offer the the current. Bid bid = new Bid();
	 * bid.setAmount(new BigDecimal(0)); if(isProductAvailable(product) &&
	 * (amount>product.getCurrentAmount().doubleValue())) { LocalDateTime time =
	 * LocalDateTime.now(); bid = new Bid(); bid.setAmount(new BigDecimal(amount));
	 * bid.setUser(user); bid.setProduct(product); bid.setBidTime(time);
	 * bidRepository.save(bid); }
	 * 
	 * return bid; }
	 */
	@Override
	public boolean placeBid(Product product, User user, double amount) {
		// TODO Auto-generated method stub;
		//Ensure product is available for bidding and the bidder is giving a higher offer the the current.
		Bid bid = new Bid();
		boolean value = false;
		if(isProductAvailable(product) && (amount>product.getCurrentAmount().doubleValue()))
		{
			LocalDateTime time = LocalDateTime.now();
			bid = new Bid();
			bid.setAmount(new BigDecimal(amount));
			bid.setUser(user);
			bid.setProduct(product);
			bid.setBidTime(time);
			bid.setActive(true);
			bidRepository.save(bid);
			 System.out.println("This is  :l"+bid.getAmount());
			value = true;
		}
		
		return value;
	}
	
	public boolean retractBid(Bid bid, Product prod) {
		//Deactivate this bid
		//Bid bid = bidRepository.getOne(bidId);
		bid.setActive(false);
		bidRepository.save(bid);
		//Modify the highest offer on the product.
		//Product prod = productRepository.findById(bid.getId()).get();		
		double highestBid = 0;
		
		if(bidRepository.getHighestBid(prod)==null)
		highestBid=prod.getStartAmount().doubleValue();
		else
		highestBid =bidRepository.getHighestBid(prod).doubleValue();
		
	    prod.setNewAmount(new BigDecimal(highestBid));
	    prod.setCurrentAmount(new BigDecimal(highestBid));
	    productRepository.save(prod);			
	return true;
	}

	@Override
	public List<Bidder> highestBiddersPerProduct() {
		// TODO Auto-generated method stub
        Date today = new Date(System.currentTimeMillis());
    	List<Product> prods = productRepository.findAllProducts(today);
    	List<Bidder> highestBidders = new ArrayList<Bidder>();
    	 
    	for (Product product : prods) { 
    		
    		if(bidRepository.findHighestOfferList(product).size()>0)
    		{
    	    List<Bid> bids = bidRepository.findHighestOfferList(product);
    		
    	    Bid highestBid = bids.iterator().next();
    	    
    		Bidder bidder= new Bidder();    		
    		User user = highestBid.getUser();  		    		
    		bidder.setAccountNumber(user.getAccountnumber());
    		bidder.setEmail(user.getEmail());
    		bidder.setFullName(user.getName() +" "+user.getLastName());
    		bidder.setStaffId(user.getStaffid());
            bidder.setBidOffer(highestBid.getAmount().doubleValue());
            bidder.setProductName(product.getName());
            bidder.setProductDescription(product.getDescription());
            bidder.setProductId(product.getId());
    		highestBidders.add(bidder);	
    		}
    		
    	 }  	
		return highestBidders;
	}

	@Override
	public Bidder getHighestBidder(Product product) {
		// TODO Auto-generated method stub
		Bid highestBid = bidRepository.findHighestOffer(product).get();
		Bidder bidder= new Bidder();    		
		User user = highestBid.getUser();  		    		
		bidder.setAccountNumber(user.getAccountnumber());
		bidder.setEmail(user.getEmail());
		bidder.setFullName(user.getName() +" "+user.getLastName());
		bidder.setStaffId(user.getStaffid());
        bidder.setBidOffer(highestBid.getAmount().doubleValue());
        bidder.setProductName(product.getName());
        bidder.setProductDescription(product.getDescription());
        bidder.setProductId(product.getId());
		return bidder;
	}

	@Override
	public Bid getCurrentBidder(BigDecimal amount, User user) {
		// TODO Auto-generated method stub
		return bidRepository.findByAmountAndUser(amount, user).get();
	}

	@Override
	public List<Bid> findByAmountAndUserList(BigDecimal amount, User user) {
		// TODO Auto-generated method stub
		return bidRepository.findByAmountAndUserList(amount, user);
	}
    
	
    
    
  

  
}

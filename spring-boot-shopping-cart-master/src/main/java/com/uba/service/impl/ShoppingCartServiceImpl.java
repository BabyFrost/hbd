package com.uba.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.uba.exception.NotEnoughProductsInStockException;
import com.uba.model.Bidder;
import com.uba.model.CartItem;
import com.uba.model.Product;
import com.uba.model.User;
import com.uba.repository.CartItemRepository;
import com.uba.repository.ProductRepository;
import com.uba.repository.UserRepository;
import com.uba.service.ShoppingCartService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Shopping Cart is implemented with a Map, and as a session bean
 *
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ProductRepository productRepository;
    
    private final UserRepository userRepository;
    
    private final CartItemRepository cartItemRepository;

    private Map<Product, Integer> products = new HashMap<>();

    @Autowired
    public ShoppingCartServiceImpl(ProductRepository productRepository,CartItemRepository cartItemRepository,UserRepository userRepository) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    /**
     * If product is in the map just increment quantity by 1.
     * If product is not in the map with, add it with quantity 1
     *
     * @param product
     */
    @Override
    public void addProduct(Product product) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
    }

    /**
     * If product is in the map with quantity > 1, just decrement quantity by 1.
     * If product is in the map with quantity 1, remove it from map
     *
     * @param product
     */
    @Override
    public void removeProduct(Product product) {
        if (products.containsKey(product)) {
            if (products.get(product) > 1)
                products.replace(product, products.get(product) - 1);
            else if (products.get(product) == 1) {
                products.remove(product);
            }
        }
    }

    /**
     * @return unmodifiable copy of the map
     */
    @Override
    public Map<Product, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }

    /**
     * Checkout will rollback if there is not enough of some product in stock
     *
     * @throws NotEnoughProductsInStockException
     */
    @Override
    public void checkout() throws NotEnoughProductsInStockException {
        Product product;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            // Refresh quantity for every product before checking
            product = productRepository.findOne(entry.getKey().getId());
            if (product.getQuantity() < entry.getValue())
                throw new NotEnoughProductsInStockException(product);
            entry.getKey().setQuantity(product.getQuantity() - entry.getValue());
            
        }
        productRepository.save(products.keySet());
        productRepository.flush();
        products.clear();
    }
    
    /**
     * Checkout will rollback if there is not enough of some product in stock
     *
     * @throws NotEnoughProductsInStockException
     */
    @Override
    public void checkout(User user) throws NotEnoughProductsInStockException {
        Product product;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            // Refresh quantity for every product before checking
            product = productRepository.findOne(entry.getKey().getId());
            if (product.getQuantity() < entry.getValue())
                throw new NotEnoughProductsInStockException(product);
            entry.getKey().setQuantity(product.getQuantity() - entry.getValue());
            CartItem item = new CartItem();
            item.setProduct(product);
            item.setUser(user);
            item.setQuantity(products.size());
            cartItemRepository.save(item);
            
        }
        productRepository.save(products.keySet());
        productRepository.flush();
        products.clear();
    }

    @Override
    public BigDecimal getTotal() {
        return products.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

	@Override
	public Page<Bidder> getCurrentBidders(long affiliateId) {
		// TODO Auto-generated method stub
		List<User> users = userRepository.findAUsers();
		List<Bidder> bidders = new ArrayList();
		
		for (User user:users)
		{
			List<CartItem> items = cartItemRepository.findByUser(user);
			Bidder bidder = new Bidder();
			bidder.setAccountNumber(user.getAccountnumber());
			bidder.setUserid(user.getId().intValue());
			bidder.setEmail(user.getEmail());
			bidder.setFullName(user.getLastName()+" "+user.getName());
			bidder.setItems(items);
			bidder.setTotalQuantity(items.size());
			double amount = 0;
			for (CartItem item:items)
			{
				double price = item.getProduct().getPrice().doubleValue();
				amount= amount+price;
			}
				
			bidder.setTotalAmount(amount);			
			bidders.add(bidder);			
		}
		
		Page<Bidder> getCurrentBidders =  new PageImpl<>(bidders);
		return getCurrentBidders;
	}

	@Override
	public Bidder getBidderDetails(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CartItem> getBidderItemList(long userId) {
		// TODO Auto-generated method stub
		return null;
	}
}

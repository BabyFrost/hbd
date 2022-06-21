package com.uba.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.uba.exception.NotEnoughProductsInStockException;
import com.uba.model.Bidder;
import com.uba.model.CartItem;
import com.uba.model.Product;
import com.uba.model.User;

public interface ShoppingCartService {

    void addProduct(Product product);

    void removeProduct(Product product);

    Map<Product, Integer> getProductsInCart();

    void checkout() throws NotEnoughProductsInStockException;
    
    void checkout(User user) throws NotEnoughProductsInStockException;
    
    Page<Bidder> getCurrentBidders(long affiliateId);
    
    Bidder getBidderDetails(long userId);
    
    List<CartItem>  getBidderItemList(long userId);

    BigDecimal getTotal();
}

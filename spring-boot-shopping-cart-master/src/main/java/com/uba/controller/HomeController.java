package com.uba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uba.model.Bidder;
import com.uba.model.Product;
import com.uba.service.ProductService;
import com.uba.util.Pager;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private static final int INITIAL_PAGE = 0;

    private final ProductService productService;

    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping("/bidders")
    public ModelAndView bidders() {
        List<Bidder> bidders = productService.highestBiddersPerProduct();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("bidders", bidders);
        modelAndView.setViewName("/bidders");
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView home(@RequestParam("page") Optional<Integer> page) {

        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Product> products = productService.findAllProductsPageable(new PageRequest(evalPage, 10));
        
        LocalDate start = LocalDate.of(2021,Month.JULY, 20); 
        LocalDate end = LocalDate.of(2021, Month.JULY, 23); 
       	int maxDays = 4;
        Date today = new Date();
		
		  Product product = new Product(); product.setDescription("decript");
		  product.setQuantity(50); 
		  product.setPrice(new BigDecimal(500000));
		  product.setMaxDays(4); 
		  product.setBidStart(new Date()); 
		  product.setBidEnd(new Date());
		  product.setMaxDays(maxDays); product.setSolid("5299");
		  product.setCurrentAmount(product.getPrice());
		  product.setStartAmount(product.getPrice()); product.setName("Computer");
		  //productService.saveProduct(product);
		 
        
		/*
		 * for (Product product : products) { LocalDate start = LocalDate.of(2021,
		 * Month.JULY, 20); LocalDate end = LocalDate.of(2021, Month.JULY, 23); int
		 * maxDays = 4;
		 * 
		 * 
		 * product.setBidStart(start); product.setBidEnd(end);
		 * product.setMaxDays(maxDays);
		 * 
		 * product.setCurrentAmount(product.getPrice());
		 * product.setStartAmount(product.getPrice());
		 * productService.saveProduct(product); }
		 */
        
        Pager pager = new Pager(products);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.addObject("pager", pager);
        modelAndView.setViewName("/home");
        return modelAndView;
    }
    
    @GetMapping("getImg/{id}")
    public ResponseEntity<byte[]> fromDatabaseAsResEntity(@PathVariable("id") Long id) 
            throws SQLException {

    	Optional<Product> primeMinisterOfIndia = productService.findById(id);
    	byte[] imageBytes = null;
    	if (primeMinisterOfIndia.isPresent()) {
    	
    		imageBytes = primeMinisterOfIndia.get().getData();
    	}
    	
    	//return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    	return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
    }

}

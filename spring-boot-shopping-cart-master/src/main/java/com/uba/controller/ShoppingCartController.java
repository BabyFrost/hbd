package com.uba.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uba.exception.NotEnoughProductsInStockException;
import com.uba.model.Bid;
import com.uba.model.Bidder;
import com.uba.model.Product;
import com.uba.model.User;
import com.uba.service.ProductService;
import com.uba.service.ShoppingCartService;
import com.uba.service.UserService;
import com.uba.service.impl.UserExcelExporter;
import com.uba.util.Pager;

@Controller
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    private final ProductService productService;
    
    private static final int INITIAL_PAGE = 0;
    
    private final UserService userService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, ProductService productService,UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/shoppingCart")
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("/shoppingCart");
        modelAndView.addObject("products", shoppingCartService.getProductsInCart());
        modelAndView.addObject("total", shoppingCartService.getTotal().toString());
        return modelAndView;
    }

    @GetMapping("/shoppingCart/addProduct/{productId}")
    public ModelAndView addProductToCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(shoppingCartService::addProduct);
        return shoppingCart();
    }
    
    @GetMapping("/shoppingCart/placjjeBid/{productId}")
    public ModelAndView placeBidx(@PathVariable("productId") Long productId) {
        //productService.findById(productId).ifPresent(shoppingCartService::addProduct);
       
        
        return shoppingCart();
    }
    
    @GetMapping("/shoppingCart/placeBid/{productId}")
    public ModelAndView placeBid(@PathVariable("productId") Long productId) {
        ModelAndView modelAndView = new ModelAndView();
        Product product = productService.findById(productId).get();
        
        int bidsCount= productService.findBidsByProduct(product).size();
        product.setNumberOfBids(bidsCount);
        
        modelAndView.addObject("product", product);
        modelAndView.setViewName("/placeBid");
        return modelAndView;
    }
    
    @GetMapping("/shoppingCart/bidders")
    public ModelAndView bidders() {
        List<Bidder> bidders = productService.highestBiddersPerProduct();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("bidders", bidders);
        modelAndView.setViewName("/bidders");
        return modelAndView;
    }
    
    @GetMapping("/shoppingCart/bidders_excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        List<Bidder> listUsers = productService.highestBiddersPerProduct();
         
        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);
         
        excelExporter.export(response);    
    }  
    
    @RequestMapping(value = "/placeBid", method = RequestMethod.POST,params = { "Offer" })
    public ModelAndView placeBid_(@Valid Product product, BindingResult bindingResult) {
    	ModelAndView modelAndView = new ModelAndView();
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
    	if (principal instanceof UserDetails) {
    	  username = ((UserDetails)principal).getUsername();
    	} else {
    	  username = principal.toString();
    	}
    	
    	 User user= userService.findByUsername(username).get();
         Product prod = productService.findById(product.getId()).get();
         int bidsCount= productService.findBidsByProduct(product).size();
      
    	 if(product.getNewAmount()==null) {}
    	 else
    	 {
    		 prod.setNewAmount(product.getNewAmount());
    		 boolean success = productService.placeBid(prod, user, prod.getNewAmount().doubleValue());
			  if (success && prod.getNewAmount().doubleValue()>prod.getCurrentAmount().doubleValue())
			  { 				  
				  bidsCount=bidsCount+1;
				  prod.setCurrentAmount(product.getNewAmount());
				  prod.setNumberOfBids(bidsCount);
				  productService.saveProduct(prod);	
				  modelAndView.addObject("product", prod);
				  modelAndView.addObject("successMessage", "Your offer for the product:"+prod.getName()+" was successfully submitted. Highest offer: "+product.getNewAmount()+ " XAF.");
			      modelAndView.setViewName("/placeBid");
			      
			  }
			  else
			  {
				  
				  modelAndView.addObject("failMessage", "Failed to submit your offer for this product. Please insert a higher amount than: "+prod.getCurrentAmount().doubleValue()+ "XAF.");
				  
				  
				  bindingResult.rejectValue("newAmount", "error.product", "Failed to submit your offer for this product");
				  modelAndView.addObject("product", prod);
			      modelAndView.setViewName("/placeBid");
			      
			  }		 
    	 }
    	 
    	 return modelAndView;
        
    }
    
    @RequestMapping(value = "/placeBid", method = RequestMethod.POST,params = { "Retract" })
    public ModelAndView retractBid_(@Valid Product product, BindingResult bindingResult) {
    	ModelAndView modelAndView = new ModelAndView();
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
    	if (principal instanceof UserDetails) {
    	  username = ((UserDetails)principal).getUsername();
    	} else {
    	  username = principal.toString();
    	}
    	
    	 User user= userService.findByUsername(username).get();
         Product prod = productService.findById(product.getId()).get();
         int bidsCount= productService.findBidsByProduct(product).size()-1;
         List<Bid> findByAmountAndUserList = productService.findByAmountAndUserList(prod.getCurrentAmount(), user);
         
         if(findByAmountAndUserList.size()>0)
         {
         Bid bid = productService.getCurrentBidder(prod.getCurrentAmount(), user);
         prod.setNumberOfBids(bidsCount);
			  if (productService.retractBid(bid,prod))
			  { 	
				  prod = productService.findById(product.getId()).get();
				  modelAndView.addObject("product", prod);
				  modelAndView.addObject("successMessage", "Your request to cancel this offer was successfully submitted.");
			      modelAndView.setViewName("/placeBid");			      
			  }
			  else
			  {
				  
				  modelAndView.addObject("failMessage", "Failed to retract this product.");
				  
				  
				  bindingResult.rejectValue("newAmount", "error.product", "Failed to retract this product");
				  modelAndView.addObject("product", prod);
			      modelAndView.setViewName("/placeBid");
			      
			  }
         }
         else
         {
        	 
        	  modelAndView.addObject("failMessage", "You have not submitted any offer for this product.");		  
			  bindingResult.rejectValue("newAmount", "error.product", "You have no offer for this product.");
			  modelAndView.addObject("product", prod);
		      modelAndView.setViewName("/placeBid"); 
         }
    	 
    	 return modelAndView;
        
    }

    @GetMapping("/shoppingCart/removeProduct/{productId}")
    public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(shoppingCartService::removeProduct);
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/checkout")
    public ModelAndView checkout() {
    	
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
    	if (principal instanceof UserDetails) {
    	  username = ((UserDetails)principal).getUsername();
    	} else {
    	  username = principal.toString();
    	}
    	
    	 User user= userService.findByUsername(username).get();
    	
        try {
            shoppingCartService.checkout(user);
        } catch (NotEnoughProductsInStockException e) {
            return shoppingCart().addObject("outOfStockMessage", e.getMessage());
        }
        return new ModelAndView("redirect:/home");
    }
    
    

}

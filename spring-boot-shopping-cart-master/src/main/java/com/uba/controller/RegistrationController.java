package com.uba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.uba.model.FileDB;
import com.uba.model.Product;
import com.uba.model.User;
import com.uba.service.ProductService;
import com.uba.service.UserService;
import com.uba.service.impl.FileStorageService;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.Valid;

@Controller
public class RegistrationController {


    @Autowired
    private UserService userService;
    
    @Autowired
    private ProductService productService;

    @Autowired
    private FileStorageService storageService;

    @PostMapping("/uploadpicture")
    public ModelAndView  uploadFile(@RequestParam("name") MultipartFile file,
    		@RequestParam("staffid") String staffid,
    		@RequestParam("firstname") String firstname,
    		@RequestParam("birtdate") String birtdate,
    		@RequestParam("accountnumber") String accountnumber,
    		@RequestParam("lastname") String lastname,
    		@RequestParam("email") String email
    		) {
    	ModelAndView modelAndView = new ModelAndView("redirect:fileupload");
      String message = "";
      
      try {
    	  FileDB fileToStore = storageService.store(file);
    	  User user = new User();
    	  user.setActive(1);
    	  user.setAccountnumber(accountnumber);
    	  user.setStaffid(staffid);
    	  user.setEmail(email);
    	  user.setDob(new Date());
    	  user.setLastName(lastname);
    	  user.setName(firstname);
    	  user.setPassword(".");
    	  user.setUsername(".");
    	  user.setFile(fileToStore);
    	  userService.saveUser(user);
    	  //modelAndView.addObject("user", user);
    	  
          
      } catch (Exception e) {
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        return null;
      }
      
      
      
      return modelAndView;
    }
    


    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {

        if (userService.findByEmail(user.getEmail()).isPresent()) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/registration");
        } else {
            // Registration successful, save user
            // Set user role to USER and set it as active
            userService.saveUser(user);

            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("/registration");
        }
        return modelAndView;
    }
    
    @RequestMapping(value = "/fileupload", method = RequestMethod.GET)
    public ModelAndView file() {
        ModelAndView modelAndView = new ModelAndView();
        FileDB file = new FileDB ();
        modelAndView.addObject("fileDB", file);
        modelAndView.setViewName("/upload");
        return modelAndView;
    }
    
    @PostMapping("/uploadAssetImg")
    public ModelAndView  uploadAssetImg(@RequestParam("filename") MultipartFile file,
    		@RequestParam("name") String name,
    		@RequestParam("description") String description,
    		@RequestParam("quantity") int quantity,
    		@RequestParam("price") double price,
    		@RequestParam("solid") String solid
    		) {
    	ModelAndView modelAndView = new ModelAndView("redirect:fileupload");
      String message = "";
      
      try {
    	  //storageService.store(file);
    	  Product p = new Product();
    	  p.setName(name);
    	  p.setDescription(description);
    	  p.setQuantity(quantity);
    	  p.setPrice(new BigDecimal(price));
    	  p.setData(file.getBytes());
    	  p.setSolid(solid);
    	  
    	  productService.saveProduct(p);
    	 
      } catch (Exception e) {
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        return null;
      }
      
      
      
      return modelAndView;
    }
    
    
}

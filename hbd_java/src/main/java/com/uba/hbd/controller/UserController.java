package com.uba.hbd.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uba.hbd.dto.LoginDTO;
import com.uba.hbd.dto.StaffDTO;
import com.uba.hbd.exception.BadRequestException;
import com.uba.hbd.exception.ResourceConflictException;
import com.uba.hbd.exception.ResourceNotFoundException;
import com.uba.hbd.model.Staff;
import com.uba.hbd.model.User;
import com.uba.hbd.service.UserService;
import com.uba.hbd.service.StaffService;

@RestController
@RequestMapping("/auth")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	StaffService staffService;

	@PostMapping()
	public ResponseEntity<StaffDTO> authentication(@Valid @ModelAttribute LoginDTO loginDTO ) throws ResourceConflictException, ResourceNotFoundException, BadRequestException{
		
		//Authenticate User from LDAP
		User loginForm = new User( loginDTO.getLogin(), null, loginDTO.getPassword() );
		User user = userService.authenticateUser(loginForm);
		
		//Get Users HBD Informations
		Staff staff = staffService.getStaffById( user.getStaffId() );
		StaffDTO staffDTO = new StaffDTO( staff );
		
		return new ResponseEntity<>( staffDTO, HttpStatus.OK );
	}
	
}

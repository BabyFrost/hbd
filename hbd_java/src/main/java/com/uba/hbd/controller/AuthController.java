package com.uba.hbd.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uba.hbd.dto.CredentialsDTO;
import com.uba.hbd.dto.StaffDTO;
import com.uba.hbd.exception.BadRequestException;
import com.uba.hbd.exception.ResourceConflictException;
import com.uba.hbd.exception.ResourceNotFoundException;
import com.uba.hbd.model.Staff;
import com.uba.hbd.service.AuthService;
import com.uba.hbd.service.StaffService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	@Autowired
	StaffService staffService;

	@PostMapping()
	public ResponseEntity<StaffDTO> authentication( @ModelAttribute CredentialsDTO credentials ) throws ResourceConflictException, ResourceNotFoundException, BadRequestException{
		
		String login = credentials.getLogin();
		String password = credentials.getPassword();
		System.out.println("AuthController credentials.getLogin() : "+login);
		
		//Authenticate User from LDAP
		Staff staff = staffService.authenticateUser( login, password);
		
		return new ResponseEntity<>( new StaffDTO( staff ), HttpStatus.OK );
		
	}
	
}

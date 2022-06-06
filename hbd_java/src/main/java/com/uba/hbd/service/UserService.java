package com.uba.hbd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uba.hbd.exception.BadRequestException;
import com.uba.hbd.exception.ResourceNotFoundException;
import com.uba.hbd.model.User;
import com.uba.hbd.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthService authService;
	
	public User getUserById( String id ) throws ResourceNotFoundException {
		return userRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No such user !") );
	}
	
	public User authenticateUser( User loginForm ) throws ResourceNotFoundException, BadRequestException {
		User user = getUserById( loginForm.getLogin() );
		if ( !(user.getPassword().equals(loginForm.getPassword())) ) {
			throw new BadRequestException("Incorrect Login or Password !!!");
		}
		return user;
	}
	
//	public User authenticateUser( User loginForm ) throws ResourceNotFoundException, BadRequestException {
//		if ( !(authService.authenticateUser(loginForm.getLogin(), loginForm.getPassword())) ) {
//			throw new BadRequestException("Bad Credentials !!!");
//		}	
//		User user = getUserById( loginForm.getLogin() );
//		return user;
//	}
	
}

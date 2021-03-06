package com.uba.hbd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uba.hbd.exception.BadRequestException;
import com.uba.hbd.exception.ResourceConflictException;
import com.uba.hbd.exception.ResourceNotFoundException;
import com.uba.hbd.model.Staff;
import com.uba.hbd.repository.StaffRepository;

@Service
public class StaffService {
	
	@Autowired
	AuthService authService;
	
	@Autowired
	StaffRepository staffRepository;
	
	//Throw An Exception if the id does not exist
	public Staff getStaffById( String id ) throws ResourceNotFoundException {
		return staffRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No such staff !") );
	}
	
	public Staff getStaffByUsername( String username ) throws ResourceNotFoundException {
		return staffRepository.findByUsername(username).orElseThrow( () -> new ResourceNotFoundException("No such staff !") );
	}

	public List<Staff> getAllStaff( String id ) throws ResourceNotFoundException {
		
		List<Staff> staffs = new ArrayList<>();	
		if ( id != null ) {
			staffs.add( getStaffById( id ) ) ;
		} else {
			staffRepository.findAll().forEach(staffs::add);
		}
		
		return staffs;
	}
	
	public Staff saveStaff( Staff staff ) {
		return staffRepository.save( staff );
	}
	
	public Staff createStaff( Staff staff ) throws ResourceConflictException {
		
		Optional<Staff> staffTmp = staffRepository.findById( staff.getId() );
		if ( staffTmp.isPresent() ) { throw new ResourceConflictException("Staff already exists"); }
		
		return staffRepository.save( staff );
	}
	
	public Staff authenticateUser( String username, String password ) throws ResourceNotFoundException, BadRequestException {
		if ( !(authService.authenticateUser( username, password)) ) {
			System.out.println("Bad LDAP Credentials");
			throw new BadRequestException("Bad Credentials !!!");
		}
		System.out.println("StaffService Looking for staff locally : "+username);
		Staff staff = getStaffByUsername( username );
		System.out.println("StaffService Found staff locally");
		
		return staff;
	}

}
package com.uba.hbd.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.uba.hbd.dto.StaffDTO;
import com.uba.hbd.exception.ResourceConflictException;
import com.uba.hbd.exception.ResourceNotFoundException;
import com.uba.hbd.file.FileStorageService;
import com.uba.hbd.model.Staff;
import com.uba.hbd.service.AuthService;
import com.uba.hbd.service.StaffService;

@RestController
@RequestMapping("/staff")
public class StaffController {

	@Autowired
	StaffService staffService;
	
	@Autowired
	AuthService authService;
	
	@Autowired
    private FileStorageService fileStorageService;
	
	@GetMapping()
	public List<StaffDTO> getAllStaff( @RequestParam(required=false) String id ) throws ResourceNotFoundException {
		
		List<Staff> staffs = staffService.getAllStaff(id);
		List<StaffDTO> staffsDTO = new ArrayList<>();
		for (int i=0; i<staffs.size(); i++) {
			staffsDTO.add( new StaffDTO( staffs.get(i) ) );
		}
		
		return staffsDTO;	
	}
	
	@PostMapping()
	public ResponseEntity<String> saveStaff(@Valid @ModelAttribute StaffDTO staffDTO ) throws ResourceConflictException {
		
		
		Staff staff = new Staff( staffDTO.getId(), staffDTO.getName(), staffDTO.getEmail(), null  );
		staff.setUsername( staffDTO.getUsername() );
		staff.setLastname( staffDTO.getLastname() );
		staffService.createStaff(staff);
		
		String fileDownloadUri = null;
		if ( staffDTO.getPhoto() != null ) {
			System.out.println( "There is a file" );
			String fileName = fileStorageService.storeFile( staffDTO.getPhoto(), staffDTO.getId());
	        fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/staffPhotos/").path(fileName).toUriString();
		}
		
		staff.setPhotoUrl(fileDownloadUri);
		staffService.saveStaff(staff);
		
		return new ResponseEntity<>( staff.getPhotoUrl(), HttpStatus.NO_CONTENT );
	}
	
	@PatchMapping("/photo")
	public ResponseEntity<String> updateStaffImage( @Valid @ModelAttribute StaffDTO staffDTO ) throws ResourceNotFoundException {
		
		String fileDownloadUri = null;
		if ( staffDTO.getPhoto() != null ) {
			String fileName = fileStorageService.storeFile( staffDTO.getPhoto(), staffDTO.getId());
	        fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/staffPhotos/").path( fileName ).toUriString();
		}
		
		Staff staff = staffService.getStaffById( staffDTO.getId() );
		staff.setPhotoUrl(fileDownloadUri);
		staffService.saveStaff(staff);
		
		return new ResponseEntity<>( staff.getPhotoUrl(), HttpStatus.NO_CONTENT );	
	}
	
	/*
	 * @PostMapping("/auth") public ResponseEntity<StaffDTO>
	 * authenticateUser( @Valid @ModelAttribute LoginDTO loginDTO ) throws
	 * ResourceConflictException, ResourceNotFoundException, BadRequestException {
	 * 
	 * String login = loginDTO.getLogin(); String password = loginDTO.getPassword();
	 * 
	 * if ( authService.authenticateUser( login, password ) ) { Staff staff =
	 * staffService.getStaffById( login ); StaffDTO staffDTO = new StaffDTO( staff
	 * ); return new ResponseEntity<>( staffDTO, HttpStatus.NO_CONTENT ); } else {
	 * throw new BadRequestException("Bad Credentials !!!"); }
	 * 
	 * }
	 */
	
	
}

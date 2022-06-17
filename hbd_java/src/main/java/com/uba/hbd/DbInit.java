package com.uba.hbd;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.uba.hbd.model.Staff;
import com.uba.hbd.repository.StaffRepository;

@Configuration
public class DbInit {
	
	@Autowired
	StaffRepository staffRepository;
	
	@PostConstruct
	private void postConstruct() {
		
		Staff staff = new Staff( "CMR001", "admin", "email", "photo.png" );
		staff.setUsername("admin");
		staff.setLastname("Vouf");
		staffRepository.save(staff);
		
	}
	

}

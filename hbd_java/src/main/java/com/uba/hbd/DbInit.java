package com.uba.hbd;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.uba.hbd.model.Staff;
import com.uba.hbd.model.User;
import com.uba.hbd.repository.StaffRepository;
import com.uba.hbd.repository.UserRepository;

@Configuration
public class DbInit {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StaffRepository staffRepository;
	
	@PostConstruct
	private void postConstruct() {
		
		User user = new User( "admin", "CMR001", "admin");
		userRepository.save(user);
		
		Staff staff = new Staff( "CMR001", "admin", "email", "photo.png" );
		staffRepository.save(staff);
		
		user = new User( "christian", "CMR002", "christian");
		userRepository.save(user);
		
		staff = new Staff( "CMR002", "Christian Ebage", "email", "photo.png" );
		staffRepository.save(staff);
		
	}
	

}

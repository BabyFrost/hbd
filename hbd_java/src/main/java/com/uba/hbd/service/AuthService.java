package com.uba.hbd.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.uba.hbd.exception.BadRequestException;

@Service
public class AuthService {
	
	public boolean authenticateUser(String username, String password) throws BadRequestException {
		RestTemplate restTemplate = new RestTemplate();
		
	    String url_= "http://paperless.ubagroup.com/ad.service/api/AD/AuthenticateUser";
			  
		String jsonContent = "{\r\n" + "\"username\" :\"" + username + "\",\r\n" +"\"password\" : \"" + password + "\"\r\n" + "}";	 
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(jsonContent,headers);
//		return restTemplate.postForObject(url_, entity, Boolean.class);
		return true;

	}

}

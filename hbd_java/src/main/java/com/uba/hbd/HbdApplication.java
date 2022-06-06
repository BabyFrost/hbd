package com.uba.hbd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.uba.hbd.file.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({ FileStorageProperties.class })
public class HbdApplication {

	public static void main(String[] args) {
		SpringApplication.run(HbdApplication.class, args);
	}

}

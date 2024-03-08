package com.machinecoding.logwatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling()
public class MachinecodingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MachinecodingApplication.class, args);
	}

}

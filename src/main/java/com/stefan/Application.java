package com.stefan;

import com.stefan.customer.Customer;
import com.stefan.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Bean
//	CommandLineRunner runner (CustomerRepository repository) {
//		return args -> {
//			Customer dan = Customer.builder()
//					.name("Dan")
//					.email("dan@gmail.com")
//					.age(30)
//					.build();
//
//			Customer eli = Customer.builder()
//					.name("Eli")
//					.email("eli@gmail.com")
//					.age(27)
//					.build();
//
//			repository.saveAll(List.of(dan,eli));
//		};
//	}
}

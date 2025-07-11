package com.example.medical_billing;



import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.medical_billing.model.User;
import com.example.medical_billing.repository.UserRepository;

@SpringBootApplication
public class MedicalBillingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalBillingApplication.class, args);
	}

	@Bean
	public PasswordEncoder passowrdEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner createDefaultUser(UserRepository userReposiotry, PasswordEncoder encoder) {
		return args -> {
			final String defaultEmail = "admin@gmail.com";
			final String defaultPassword = "12345";

			if (userReposiotry.findByEmail(defaultEmail) == null) {
				User user = new User();
				user.setEmail(defaultEmail);
				user.setPassword(encoder.encode(defaultPassword));
				userReposiotry.save(user);
				System.out.println("Default User Created........");
			}
		};
	}

}
package br.com.felippepuhle.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.felippepuhle.model.User;
import br.com.felippepuhle.repository.UserRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
		
	@Override
	public void run(String... arg0) throws Exception {
		this.userRepository.save(new User("Felippe Rodrigo Puhle", "felippe.puhle@gmail.com", "felippe", "123456"));
	}	

}

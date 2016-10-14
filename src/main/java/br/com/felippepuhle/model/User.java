package br.com.felippepuhle.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import br.com.felippepuhle.util.StringUtil;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private String email;
	private String login;
	private String password;
	private String token;

	public User(){
		
	}
	
	public User(String name, String email, String login, String password) {
		this.name = name;
		this.email = email;
		this.login = login;
		this.password = password;
		this.token = StringUtil.generateToken();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getToken() {
		return token;
	}

}
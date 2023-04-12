package com.mysite.stockmanager.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.DatatypeConverter;
import lombok.EqualsAndHashCode;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "users")
@Entity(name = "users")
@EqualsAndHashCode(of = "id")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Nonnull
	private String fullName;
	
	@Nonnull
	private String email;
	
	@Nonnull
	private String password;
	
	private int sessionToken = 0;
		
	public User(String fullName, String email, String password) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.password = password;
	}
	public User() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	
	
	
	public int getSessionToken() {
		return sessionToken;
	}
	
	public void setSessionToken(int sessionToken) {
		this.sessionToken = sessionToken;
	}
	
	public String encrypt(String password) {
		try {
			String result;
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte[] hash = md.digest();
			result = DatatypeConverter.printHexBinary(hash).toLowerCase();
			return result;			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
		}
		return null;
	}

	public void setPassword(String password) {
		this.password = encrypt(password);
	}
}

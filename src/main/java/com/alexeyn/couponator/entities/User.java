package com.alexeyn.couponator.entities;


import com.alexeyn.couponator.enums.UserType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "userId")
	private long userId;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "password", unique = false, nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", unique = false, nullable = false)
	private UserType type;

	@Column(name = "companyId", unique = true, nullable = true)
	private Long companyId;

	@OneToOne
	private Customer customer;

	@ManyToOne
	private Company company;

	// Default constructor
	public User() {
	}

	// Constructor with id only (in order to create customer)
	public User(long userId) {
		this.userId = userId;
	}

	// Full constructor without id
	public User(String username, String password, UserType type, long companyId) {
		this.username = username;
		this.password = password;
		this.type = type;
		this.companyId = companyId;
	}

	// Full constructor with id
	public User(long userId, String username, String password, UserType type, long companyId) {
		this(username, password, type, companyId);
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}


	@Override
	public String toString() {
		return "User [" +
				"id=" + userId +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", type=" + type +
				", companyId=" + companyId +
				']';
	}
}

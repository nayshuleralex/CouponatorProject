package com.alexeyn.couponator.entities;


import com.alexeyn.couponator.enums.UserType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "userId")
	private Long userId;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private UserType type;

//	@Column(name = "companyId")
//	private Long companyId;

	@OneToOne
	@JoinColumn(name = "customerId", foreignKey = @ForeignKey(name = "FK_USER_CUSTOMER_ID"))
	private Customer customer;


	@ManyToOne()
	@JoinColumn(name = "companyId", foreignKey = @ForeignKey(name = "FK_USER_COMPANY_ID"))
	private Company company;

	// Default constructor
	public User() {
	}

	// Constructor with id only (in order to create customer)
	public User(Long userId) {
		this.userId = userId;
	}

	// Full constructor without id
	public User(String username, String password, UserType type) {
		this.username = username;
		this.password = password;
		this.type = type;
//		this.companyId = companyId;
	}

	// Full constructor with id
	public User(Long userId, String username, String password, UserType type) {
		this(username, password, type);
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
//	public Long getCompanyId() {
//		return companyId;
//	}
//
//	public void setCompanyId(Long companyId) {
//		this.companyId = companyId;
//	}


	@Override
	public String toString() {
		return "User [" +
				"id=" + userId +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", type=" + type + ']';
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return username.equals(user.username) &&
				password.equals(user.password) &&
				type == user.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, password, type);
	}
}

package com.alexeyn.couponator.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "customers")
public class Customer implements Serializable {

    @Id
    @Column(name = "customerId", unique = true, nullable = false)
    private Long customerId;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "customer")
    private List<Purchase> purchases;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "userId",foreignKey = @ForeignKey(name = "FK_CUSTOMER_USER_ID"))
    private User user;

    // Empty constructor

    public Customer() {
    }
    // Full constructor without customerId
    public Customer(User user, String firstName, String lastName, String address, String email) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
    }

    // Full constructor without user

    public Customer(Long customerId, String firstName, String lastName, String address, String email) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
    }

    public Customer(String firstName, String lastName, String address, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
    }

    // Full constructor
    public Customer(Long customerId, User user, String firstName, String lastName, String address, String email) {
        this(user, firstName, lastName, address, email);
        this.customerId = customerId;
    }
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long userId) {
        this.customerId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "Customer [" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", user=" + user +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerId.equals(customer.customerId) &&
                firstName.equals(customer.firstName) &&
                lastName.equals(customer.lastName) &&
                address.equals(customer.address) &&
                email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, firstName, lastName, address, email);
    }
}

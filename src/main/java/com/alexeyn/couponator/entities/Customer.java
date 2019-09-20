package com.alexeyn.couponator.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Customers")
public class Customer implements Serializable {

    @Id
    @Column(name = "customerId", unique = true, nullable = false)
    private long customerId;

    @Column(name = "firstName", unique = false, nullable = false)
    private String firstName;

    @Column(name = "lastName", unique = false, nullable = false)
    private String lastName;

    @Column(name = "address", unique = false, nullable = false)
    private String address;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "customerId")
    private List<Purchase> purchases;

    @OneToOne(cascade = CascadeType.REMOVE)
    private User user;

    // Empty constructor

    public Customer() {
    }
    public Customer(User user, String firstName, String lastName, String address, String email) {
        // Full constructor without customerId
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
    }

    // Full constructor without user

    public Customer(long customerId, String firstName, String lastName, String address, String email) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
    }

    // Full constructor
    public Customer(long customerId, User user, String firstName, String lastName, String address, String email) {
        this(user, firstName, lastName, address, email);
        this.customerId = customerId;
    }
    public long getId() {
        return customerId;
    }

    public void setId(long userId) {
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
}

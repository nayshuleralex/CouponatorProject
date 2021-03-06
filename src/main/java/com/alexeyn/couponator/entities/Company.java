package com.alexeyn.couponator.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "companies")
public class Company implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "companyId")
    private Long companyId;

    @Column(name = "companyName", unique = true, nullable = false)
    private String companyName;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "company", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Coupon> coupons;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "company", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> users;

    // Default constructor
    public Company() {
    }

    // Full constructor without id
    public Company(Long companyId, String companyName, String address) {
        this(companyName, address);
        this.companyId = companyId;
    }

    // Full constructor without id
    public Company(String companyName, String address) {
        this.companyName = companyName;
        this.address = address;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Company [" +
                "id=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", address='" + address + '\'' +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return companyId.equals(company.companyId) &&
                companyName.equals(company.companyName) &&
                address.equals(company.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, companyName, address);
    }
}

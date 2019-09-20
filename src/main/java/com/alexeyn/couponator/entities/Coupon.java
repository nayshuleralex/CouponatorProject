package com.alexeyn.couponator.entities;

import com.alexeyn.couponator.enums.CouponsCategories;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "coupons")
public class Coupon implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "couponId")
    private long couponId;

	@Column(name = "title", unique = true, nullable = false)
    private String title;

	@Column(name = "startDate", unique = false, nullable = false)
	private Date startDate;

	@Column(name = "endDate", unique = false, nullable = false)
	private Date endDate;

	@Column(name = "price", unique = false, nullable = false)
    private float price;

	@Column(name = "type", unique = false, nullable = false)
    private CouponsCategories type;

	@Column(name = "description", unique = false, nullable = false)
    private String description;

	@Column(name = "amount", unique = false, nullable = false)
    private int amount;

	@Column(name = "companyId", unique = false, nullable = false)
    private long companyId;

	@ManyToOne
	private Company company;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "couponId")
	private List<Purchase> purchases;

	// Empty constructor
    public Coupon() {
    }

    // Full constructor without id
	public Coupon(String title, Date startDate, Date endDate, float price, CouponsCategories type, String description, int amount,
				  long companyId) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.type = type;
		this.description = description;
		this.amount = amount;
		this.companyId = companyId;
	}

	// Full constructor with id
    public Coupon(long couponId, String title, Date startDate, Date endDate, float price, CouponsCategories type, String description,
                  int amount, long companyId) {
    	this(title, startDate, endDate, price, type, description, amount, companyId);
        this.couponId = couponId;
    }

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public CouponsCategories getType() {
        return type;
    }

    public void setType(CouponsCategories type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "Coupon [id=" + couponId + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
                + ", price=" + price + ", type=" + type + ", description=" + description + ", amount=" + amount
                + ", companyID=" + companyId + "]";
    }


}
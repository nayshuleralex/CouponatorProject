package com.alexeyn.couponator.entities;

import com.alexeyn.couponator.enums.CouponsCategories;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "coupons")
public class Coupon implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "couponId")
    private Long couponId;

	@Column(name = "title", unique = true, nullable = false)
    private String title;

	@Column(name = "startDate", nullable = false)
	private Date startDate;

	@Column(name = "endDate", nullable = false)
	private Date endDate;

	@Column(name = "price", nullable = false)
    private float price;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
    private CouponsCategories type;

	@Column(name = "description", nullable = false)
    private String description;

	@Column(name = "amount", nullable = false)
    private int amount;

	@ManyToOne
    @JoinColumn(name = "companyId", foreignKey = @ForeignKey(name = "FK_COUPON_COMPANY_ID"))
	private Company company;

	@OneToMany(mappedBy = "coupon", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private List<Purchase> purchases;

	// Empty constructor
    public Coupon() {
    }

    // Full constructor without id
	public Coupon(String title, Date startDate, Date endDate, float price, CouponsCategories type, String description, int amount) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.type = type;
		this.description = description;
		this.amount = amount;
	}

	// Full constructor with id
    public Coupon(long couponId, String title, Date startDate, Date endDate, float price, CouponsCategories type, String description,
                  int amount) {
    	this(title, startDate, endDate, price, type, description, amount);
        this.couponId = couponId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
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


    @Override
    public String toString() {
        return "Coupon [id=" + couponId + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
                + ", price=" + price + ", type=" + type + ", description=" + description + ", amount=" + amount
                + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return Float.compare(coupon.price, price) == 0 &&
                amount == coupon.amount &&
                couponId.equals(coupon.couponId) &&
                title.equals(coupon.title) &&
                startDate.equals(coupon.startDate) &&
                endDate.equals(coupon.endDate) &&
                type == coupon.type &&
                description.equals(coupon.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couponId, title, startDate, endDate, price, type, description, amount);
    }
}

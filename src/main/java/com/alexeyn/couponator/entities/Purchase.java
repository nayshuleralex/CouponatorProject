package com.alexeyn.couponator.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "purchases")
public class Purchase implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "purchaseId")
    private long purchaseId;

    @Column(name = "customerId", nullable = false)
    private long customerId;

    @Column(name = "couponId", nullable = false)
    private long couponId;

    @Column(name = "amount", nullable = false)
    private int amount;

    @ManyToOne
    private Coupon coupon;

    @ManyToOne
    private Customer customer;

    // Empty constructor
    public Purchase() {
    }

    // Full constructor without id
    public Purchase(long customerId, long couponId, int amount) {
        this.customerId = customerId;
        this.couponId = couponId;
        this.amount = amount;
    }

    // Full constructor with id
    public Purchase(long purchaseId, long customerId, long couponId, int amount) {
        this(customerId, couponId, amount);
        this.purchaseId = purchaseId;
    }

    public long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return "Purchase [" +
                "id=" + purchaseId +
                ", customerId=" + customerId +
                ", couponId=" + couponId +
                ", amount=" + amount +
                ']';
    }
}

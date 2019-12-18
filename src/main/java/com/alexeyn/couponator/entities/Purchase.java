package com.alexeyn.couponator.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "purchases")
public class Purchase implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "purchaseId")
    private Long purchaseId;

//    @Column(name = "customerId", nullable = false)
//    private Long customerId;
//
//    @Column(name = "couponId", nullable = false)
//    private Long couponId;

    @Column(name = "amount", nullable = false)
    private int amount;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "couponId", foreignKey = @ForeignKey(name = "FK_PURCHASE_COUPON_ID"))
    private Coupon coupon;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "customerId", foreignKey = @ForeignKey(name = "FK_PURCHASE_CUSTOMER_ID"))
    private Customer customer;

    // Empty constructor
    public Purchase() {
    }

    public Purchase(int amount, Coupon coupon, Customer customer) {
        this.amount = amount;
        this.coupon = coupon;
        this.customer = customer;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
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
                ", amount=" + amount +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return purchaseId.equals(purchase.purchaseId) &&
                amount == purchase.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseId, amount);
    }
}

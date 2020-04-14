
package com.example.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Cargo {

    private Cargo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    @Column
    private String product;

    @Column
    private Integer quantity;

    @Column
    private Date orderDate;

    public Cargo(Long id, Customer customer, String product, Integer quantity, Date orderDate) {
        this.id = id;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    public Long getId() {
        return this.id;
    }

    public String getProduct() {
        return this.product;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                ", customer=" + customer +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", orderDate=" + orderDate +
                '}';
    }
}
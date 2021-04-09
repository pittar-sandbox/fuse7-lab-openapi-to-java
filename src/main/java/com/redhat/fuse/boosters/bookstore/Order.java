package com.redhat.fuse.boosters.bookstore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
@NamedQuery(name = "findAllOrders", query = "select b from Order b")
@NamedQuery(name = "findOrderById", query = "select b from Order b where b.id = :orderId")
public class Order implements Serializable {

    @Id
    private String id;

    private LocalDate orderDate;

    public Order() {
    }

    public Order(String id, LocalDate orderDate) {
        this.id = id;
        this.orderDate = orderDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}

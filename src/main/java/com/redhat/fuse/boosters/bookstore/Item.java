package com.redhat.fuse.boosters.bookstore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "items")
@NamedQuery(name = "findItemById", query = "select b from Item b where b.id = :id")
@NamedQuery(name = "findItemByOrderId", query = "select b from Item b where b.orderId = :orderId")
public class Item implements Serializable {

    @Id
    private String id;
    private String orderId;
    private Integer quantity;

    private String isbn;

    public Item() {
    }

    public Item(String id, String orderId, Integer quantity, String isbn) {
        this.id = id;
        this.orderId = orderId;
        this.quantity = quantity;
        this.isbn = isbn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}

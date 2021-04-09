package com.redhat.fuse.boosters.bookstore;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "books")
@NamedQuery(name = "findAllBooks", query = "select b from Book b")
@NamedQuery(name = "findBookByIsbn", query = "select b from Book b where b.isbn = :isbn")
public class Book implements Serializable {

    @Id
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private String genre;

    public Book() {
    }

    public Book(String isbn, String title, String author, String publisher, String genre) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}

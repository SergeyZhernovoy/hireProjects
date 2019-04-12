package ru.biblio.web.domain;

import javax.persistence.*;

/**
 * Книга
 */

@Entity
@Table(name = "books")
public class Book {
    /**Идентификатор книги в бд*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**Название книги*/
    private String title;
    /**Фио автора */
    private String author;

    /**Идентификатор полки */
    @ManyToOne(targetEntity = Shelf.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "shelf_id")
    private Shelf shelf;

    /**Владелец книги */
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User  owner;

    private byte[] blob;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}

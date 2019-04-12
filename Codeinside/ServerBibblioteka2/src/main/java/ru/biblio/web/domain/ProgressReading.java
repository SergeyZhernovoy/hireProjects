package ru.biblio.web.domain;

import org.codehaus.groovy.reflection.GeneratedMetaMethod;

import javax.persistence.*;

@Entity
@Table(name = "progress_reading")
public class ProgressReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**Пользователь */
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Integer page;

    /**Книга */
    @ManyToOne(targetEntity = Book.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "book_id")
    private Book book;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

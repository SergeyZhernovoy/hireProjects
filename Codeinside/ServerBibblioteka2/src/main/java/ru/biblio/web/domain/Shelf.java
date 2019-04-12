package ru.biblio.web.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Полка
 */
@Entity
@Table(name = "shelves")
public class Shelf {
    /**Идентификатор полки */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**Название полки */
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

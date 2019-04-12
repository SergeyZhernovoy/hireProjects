package ru.biblio.web.service;

import ru.biblio.web.domain.Shelf;

import java.util.List;

public interface ShelfService {
    Shelf saveShelf(Shelf shelf);

    List<Shelf> getShelves();

    void delete(Long id);

}

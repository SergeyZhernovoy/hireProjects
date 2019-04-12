package ru.biblio.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.biblio.web.domain.Shelf;
import ru.biblio.web.repository.ShelfRepository;
import ru.biblio.web.service.ShelfService;

import java.util.List;

@Service
public class ShelfServiceImpl implements ShelfService {

    @Autowired
    private ShelfRepository shelfRepository;

    @Override
    public Shelf saveShelf(Shelf shelf) {
        return this.shelfRepository.save(shelf);
    }

    @Override
    public List<Shelf> getShelves() {
        return this.shelfRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        this.shelfRepository.delete(id);
    }
}

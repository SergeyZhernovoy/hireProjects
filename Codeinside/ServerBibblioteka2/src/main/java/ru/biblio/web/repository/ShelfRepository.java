package ru.biblio.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.biblio.web.domain.Shelf;

import javax.transaction.Transactional;

@Transactional
public interface ShelfRepository extends JpaRepository<Shelf,Long> {
}

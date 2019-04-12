package ru.biblio.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.biblio.web.domain.Book;
import ru.biblio.web.domain.Shelf;
import ru.biblio.web.domain.User;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface BookRepository extends JpaRepository<Book,Integer> {

    List<Book> findBookByOwnerIsNotAndShelf(User owner,Shelf shelf);

    List<Book> findBookByOwnerOrderByShelf(User user);
}

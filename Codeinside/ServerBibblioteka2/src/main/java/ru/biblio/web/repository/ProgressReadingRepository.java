package ru.biblio.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.biblio.web.domain.Book;
import ru.biblio.web.domain.ProgressReading;
import ru.biblio.web.domain.User;

public interface ProgressReadingRepository extends JpaRepository<ProgressReading,Long> {
    ProgressReading findByUserAndAndBook(User user, Book book);
}

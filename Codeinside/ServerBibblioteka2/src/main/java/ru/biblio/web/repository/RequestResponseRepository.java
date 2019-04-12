package ru.biblio.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.biblio.web.domain.RequestResponseBook;
import ru.biblio.web.domain.User;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public interface RequestResponseRepository extends JpaRepository<RequestResponseBook,Long> {

    @Query("SELECT R FROM RequestResponseBook R " +
            "WHERE R.response = :user " +
            "AND (R.enabled is null " +
            "OR R.enabled = :enabled " +
            "AND R.answer is null)")
    List<RequestResponseBook> getRequestResponseBookByResponseAndEnabled(@Param("user") User user, @Param("enabled") Boolean enabled);

    RequestResponseBook getRequestResponseBookById(Integer id);

    List<RequestResponseBook> findAllByRequestAndEnabledAndExpiredNullOrExpiredIsGreaterThan(User request,Boolean enabled, Date current);
}

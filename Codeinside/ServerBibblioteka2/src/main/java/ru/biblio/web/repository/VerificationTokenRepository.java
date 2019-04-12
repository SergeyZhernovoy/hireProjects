package ru.biblio.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.biblio.web.domain.User;
import ru.biblio.web.domain.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}

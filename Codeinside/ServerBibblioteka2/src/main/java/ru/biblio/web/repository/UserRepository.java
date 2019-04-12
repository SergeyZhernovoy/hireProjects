package ru.biblio.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.biblio.web.domain.User;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserRepository extends JpaRepository<User,Long>{
    User findUsersByNameAndPasswordAndEnabledTrue(String name, String password);
}

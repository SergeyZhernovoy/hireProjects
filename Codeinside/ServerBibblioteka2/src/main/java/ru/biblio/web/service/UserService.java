package ru.biblio.web.service;

import ru.biblio.web.domain.User;
import ru.biblio.web.domain.VerificationToken;
import ru.biblio.web.registration.EmailExistsException;

import java.util.Optional;

public interface UserService {

    User registerNewUserAccount(User user);

    User getUser(String verificationToken);

    Optional<User> findUser(String username, String password);

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    User getCurrentUser();

    void setCurrentUser(User user);

}

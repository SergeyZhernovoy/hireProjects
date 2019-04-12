package ru.biblio.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.biblio.web.domain.Registration;
import ru.biblio.web.domain.User;
import ru.biblio.web.domain.VerificationToken;
import ru.biblio.web.registration.EmailExistsException;
import ru.biblio.web.repository.UserRepository;
import ru.biblio.web.repository.VerificationTokenRepository;
import ru.biblio.web.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements UserService {

    private ThreadLocal<User> currentUsers = ThreadLocal.withInitial(() -> null);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public User registerNewUserAccount(User user) {
        return userRepository.save(user);
    }

    public User getUser(String verificationToken) {
        User user = verificationTokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    @Override
    public Optional<User> findUser(String username, String password) {
        return Optional.ofNullable(this.userRepository.findUsersByNameAndPasswordAndEnabledTrue(username,password));
    }

    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        verificationTokenRepository.save(myToken);
    }

    public VerificationToken getVerificationToken(String verificationToken) {
        return verificationTokenRepository.findByToken(verificationToken);
    }

    @Override
    public User getCurrentUser() {
        return this.currentUsers.get();
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUsers.set(user);
    }
}

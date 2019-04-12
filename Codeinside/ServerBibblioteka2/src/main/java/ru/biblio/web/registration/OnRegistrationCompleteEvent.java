package ru.biblio.web.registration;

import org.springframework.context.ApplicationEvent;
import ru.biblio.web.domain.User;

import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private User user;
    private String url;
    private Locale locale;

    public OnRegistrationCompleteEvent(User user, String url,Locale locale) {
        super(user);
        this.user = user;
        this.url = url;
        this.locale = locale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAppUrl() {
        return url;
    }

    public void setAppUrl(String url) {
        this.url = url;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}

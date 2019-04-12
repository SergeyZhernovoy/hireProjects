package ru.biblio.web.service;

/**
 * @author Sergey Zhernovoy
 * create on 25.01.2018.
 */
public interface RecaptchaService {
    String verifyCaptcha(String ip, String response);
}

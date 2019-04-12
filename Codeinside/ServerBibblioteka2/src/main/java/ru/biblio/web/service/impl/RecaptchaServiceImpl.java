package ru.biblio.web.service.impl;/**
 * @author Sergey Zhernovoy
 * create on 25.01.2018.
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.biblio.web.registration.RecaptchaUtil;
import ru.biblio.web.service.RecaptchaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecaptchaServiceImpl implements RecaptchaService {

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecretKey;

    private static final String GOOGLE_RECAPTCHA_VERIFY_URL =  "https://www.google.com/recaptcha/api/siteverify";

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Override
    public String verifyCaptcha(String ip, String response) {

        Map<String, String> body = new HashMap<>();
        body.put("secret", recaptchaSecretKey);
        body.put("response", response);
        body.put("remoteip", ip);

        ResponseEntity<Map> recaptchaResponseEntity =   restTemplateBuilder
                .build()
                .postForEntity(GOOGLE_RECAPTCHA_VERIFY_URL + "?secret={secret}&response={response}&remoteip={remoteip}", body, Map.class, body);
        Map<String, Object> responseBody = recaptchaResponseEntity.getBody();
        boolean success = (Boolean)responseBody.get("success");
        String result  = StringUtils.EMPTY;
        if ( !success) {
            List<String> errorCodes = (List)responseBody.get("error-codes");
            String errorMessage = errorCodes.stream()
                    .map(s -> RecaptchaUtil.RECAPTCHA_ERROR_CODE.get(s))
                    .collect(Collectors.joining(", "));
            result =  errorMessage;
        }
        return result;
    }
}

    
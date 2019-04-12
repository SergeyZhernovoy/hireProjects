package ru.biblio.web.registration;/**
 * @author Sergey Zhernovoy
 * create on 25.01.2018.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RecaptchaUtil {
    private static final Logger logger = LoggerFactory.getLogger(RecaptchaUtil.class);
    public static final Map<String, String> RECAPTCHA_ERROR_CODE = new HashMap<>();
    static {
        RECAPTCHA_ERROR_CODE.put("missing-input-secret",   "The secret parameter is missing");
        RECAPTCHA_ERROR_CODE.put("invalid-input-secret",   "The secret parameter is invalid or malformed");
        RECAPTCHA_ERROR_CODE.put("missing-input-response", "The response parameter is missing");
        RECAPTCHA_ERROR_CODE.put("invalid-input-response", "The response parameter is invalid or malformed");
        RECAPTCHA_ERROR_CODE.put("bad-request",            "The request is invalid or malformed");
    }
}

    
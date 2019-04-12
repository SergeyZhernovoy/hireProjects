package ru.biblio.web.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ru.biblio.web.domain.User;
import ru.biblio.web.domain.VerificationToken;
import ru.biblio.web.registration.EmailExistsException;
import ru.biblio.web.registration.OnRegistrationCompleteEvent;
import ru.biblio.web.service.RecaptchaService;
import ru.biblio.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RestController
//@RequestMapping("/account")
public class UserAccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private RecaptchaService recaptchaService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView){
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registr(ModelAndView modelAndView){
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/crud", method = RequestMethod.GET)
    public ModelAndView crud(ModelAndView modelAndView){
        modelAndView.setViewName("crud");
        return modelAndView;
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password){
        User user = this.userService
                        .findUser(username,password)
                        .get();

        if(user != null && user.getId() != null){
            //crud.html
            this.userService.setCurrentUser(user);
            return  ResponseEntity.ok().body("{\"nextPage\":\"crud\",\"user_id\":\""+user.getId().toString()+"\"}");
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
               .build();

    }



    @RequestMapping(value = "/registration",method = RequestMethod.POST)
    public ResponseEntity<?> registration(@RequestParam String username,
                                          @RequestParam String password,
                                          @RequestParam String email,
                                          @RequestParam(name="recaptcha") String recaptchaResponse,
                                       HttpServletRequest request) {
        String captchaVerifyMessage =     recaptchaService.verifyCaptcha(request.getRemoteAddr(), recaptchaResponse);
        if(StringUtils.isNotEmpty(captchaVerifyMessage)) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", captchaVerifyMessage);
            return ResponseEntity
                    .badRequest()
                    .body(response);
        } else {
            User newUser = new User
                    .Builder()
                    .setEmail(email)
                    .setName(username)
                    .setPassword(password)
                    .build();
            User regUser = userService.registerNewUserAccount(newUser);
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(regUser,appUrl,request.getLocale()));
        }

        return ResponseEntity
                .ok()
                .build();
    }

    @RequestMapping(value = "/account/registration-confirm", method = RequestMethod.GET)
    public ModelAndView confirmRegistration
            (WebRequest request, ModelAndView model, @RequestParam("token") String token) {

        VerificationToken verificationToken = userService.getVerificationToken(token);
        Calendar cal = Calendar.getInstance();
        model.setViewName("result");
        if (verificationToken == null || (verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String message = "Invalid token or Date token is expired";
            model.addObject("message", message);
        } else {
            User user = verificationToken.getUser();
            user.setEnabled(true);
            userService.saveRegisteredUser(user);
            String message = "You are confirmation your account !";
            model.addObject("message", message);
        }
        return model;
    }


}

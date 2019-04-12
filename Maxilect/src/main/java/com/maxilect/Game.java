package com.maxilect;

import com.maxilect.api.Input;
import com.maxilect.api.ScoreRepository;
import com.maxilect.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Game {
    private ScoreRepository scoreRepository;
    private Map<User, List<String>> usersPalindrom;
    private Input input;

    public Game(ScoreRepository repository, Input input) {
        scoreRepository = repository;
        this.input = input;
        usersPalindrom = new HashMap<>();
    }

    public void newGame() {
        while (!input.ask("Press q for exit or any for play :->\t\t").equalsIgnoreCase("q")) {
            User user = new User();
            user.setName(input.ask("Enter user name"));
            String word = input.ask("Enter word for check");
            List<String> userWords = usersPalindrom.get(user);
            if (userWords == null) {
                userWords = new ArrayList<>();
                usersPalindrom.put(user, userWords);
            }

            String test = word.replaceAll(" ", "");
            String reverse = new StringBuilder(test)
                            .reverse()
                            .toString();

            long score = 0;
            if (reverse.equalsIgnoreCase(test)) {
                score = test.length();
                if (!userWords.contains(test)) {
                    scoreRepository.update(user.getName(), score);
                    userWords.add(test);
                    usersPalindrom.put(user, userWords);
                }
            }
        }
    }
}

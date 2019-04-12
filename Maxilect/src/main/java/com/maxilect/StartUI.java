package com.maxilect;

import com.maxilect.api.ScoreRepository;
import com.maxilect.api.impl.ConsoleInputImpl;
import com.maxilect.api.impl.InMemoryScoreRepositoryImpl;
import com.maxilect.domain.User;

import java.util.List;

public class StartUI {
    public static void main(String[] args) {
        ScoreRepository scoreRepository = new InMemoryScoreRepositoryImpl();
        new Game(scoreRepository, new ConsoleInputImpl()).newGame();
        System.out.println("List winners");
        List<User> list = scoreRepository.getWinners(true);
        for (User user : list) {
            System.out.println(user);
        }
    }
}

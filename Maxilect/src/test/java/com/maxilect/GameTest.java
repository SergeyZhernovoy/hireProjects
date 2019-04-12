package com.maxilect;


import com.maxilect.api.ScoreRepository;
import com.maxilect.api.impl.InMemoryScoreRepositoryImpl;
import com.maxilect.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class GameTest {
    private MockInput mockInput;

    @BeforeEach
    void setUp() {
        mockInput = new MockInput();
    }

    @Test
    void newGame() {
        ScoreRepository scoreRepository = new InMemoryScoreRepositoryImpl();
        new Game(scoreRepository, mockInput).newGame();
        List<User> list = scoreRepository.getWinners(true);
        User user = list.get(0);
        Assertions.assertEquals(user.getScore(), 10);
    }
}
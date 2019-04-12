package com.maxilect.api;

import com.maxilect.domain.User;

import java.util.List;

public interface ScoreRepository {
    void update(String player, long score);
    List<User> getWinners(boolean five);
}

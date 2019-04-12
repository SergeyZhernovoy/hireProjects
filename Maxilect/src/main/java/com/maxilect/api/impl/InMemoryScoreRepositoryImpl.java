package com.maxilect.api.impl;

import com.maxilect.api.ScoreRepository;
import com.maxilect.domain.User;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryScoreRepositoryImpl implements ScoreRepository {

    List<User> leader = new ArrayList<>();

    @Override
    public void update(String player, long score) {
        User user = new User();
        user.setName(player);
        if (leader.contains(user)) {
           int index = leader.indexOf(user);
           User inMemory = leader.get(index);
           user.setScore(score + inMemory.getScore());
           leader.set(index,user);
        } else {
            user.setScore(score);
            leader.add(user);
        }
    }

    @Override
    public List<User> getWinners(boolean five) {
        List<User> users = leader.stream()
                    .sorted((o1,o2) -> o1.compareTo(o2))
                    .collect(Collectors.toList());
        if (five) {
            users = users.subList(0, users.size() > 5 ? 4 : users.size());
        }
        return users;
    }
}

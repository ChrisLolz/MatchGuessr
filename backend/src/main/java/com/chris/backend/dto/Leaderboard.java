package com.chris.backend.dto;

public interface Leaderboard {
    String getUsername();
    Integer getPoints();
    Integer getGuesses();
    Integer getCorrect_guesses();
}

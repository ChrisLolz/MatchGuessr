package com.chris.backend.payload.request;

import com.chris.backend.models.Guess.Result;

import jakarta.validation.constraints.NotBlank;

public class GuessRequest {
    @NotBlank
    public Integer matchId;
    @NotBlank
    public Result result;

    public Integer getMatchId() { return matchId; }

    public Result getResult() { return result; }

    public void setMatchId(Integer matchId) { this.matchId = matchId; }

    public void setResult(Result result) { this.result = result; }

    public GuessRequest() {}

    public GuessRequest(Integer matchId, Result result) {
        this.matchId = matchId;
        this.result = result;
    }
}

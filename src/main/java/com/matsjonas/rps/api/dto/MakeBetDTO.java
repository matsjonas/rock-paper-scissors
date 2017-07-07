package com.matsjonas.rps.api.dto;

/**
 * Simple data transfer object representing a single bet in a game.
 * <p>
 * Instances will be serialized to JSON and used to communicate between client and server over the api.
 */
public class MakeBetDTO {

    private String player;
    private String bet;

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getBet() {
        return bet;
    }

    public void setBet(String bet) {
        this.bet = bet;
    }

}

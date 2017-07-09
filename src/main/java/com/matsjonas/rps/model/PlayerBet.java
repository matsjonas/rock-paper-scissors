package com.matsjonas.rps.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Object containing the name of a player and that player's {@link Bet bet}.
 */
public class PlayerBet {

    private String playerName;
    private Bet bet;

    /**
     * Empty constructor needed by Jackson in order to [de]serialize object.
     */
    public PlayerBet() {

    }

    /**
     * Constructs a new pair of player name and player bet.
     *
     * @param playerName the name of the player
     * @param bet the player's bet
     */
    public PlayerBet(String playerName, Bet bet) {
        if (StringUtils.isBlank(playerName)) {
            throw new IllegalArgumentException("Player name must no be blank");
        } else if (bet == null) {
            throw new IllegalArgumentException("Bet must not be null");
        }
        this.playerName = playerName;
        this.bet = bet;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Bet getBet() {
        return bet;
    }

}

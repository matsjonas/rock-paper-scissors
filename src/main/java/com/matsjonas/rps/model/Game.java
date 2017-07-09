package com.matsjonas.rps.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * A Game represents a round of rock-paper-scissors.
 * <p>
 * A Game holds information about it's id (a name of some sort), placed bets in a list of {@link PlayerBet} objects, the
 * status of the game ({@link Status} and the potential winner of the game (unless it was a draw).
 * <p>
 * Sequence of a game goes as follows.
 * <ol>
 * <li> Create a Game instance with {@link Game#Game(String)}.
 * <li> Add a first bet with {@link #addPlayerBet(PlayerBet)}.
 * <li> Add a second bet with {@link #addPlayerBet(PlayerBet)}.
 * <li> Check who won with {@link Game#getWinner()}, or for a tie using {@link #getStatus()}.
 * </ol>
 */
public class Game {

    /**
     * Status enum for possible Game statuses.
     */
    public enum Status {
        PENDING, ONGOING, WIN, DRAW
    }

    private String id;
    private List<PlayerBet> bets;
    private Status status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PlayerBet winner;

    /**
     * Empty constructor needed by Jackson in order to [de]serialize object.
     */
    public Game() {

    }

    /**
     * Constructs a new Game instance and sets it's id to <var>id</var>.
     *
     * @param id the id, i.e. name of the game.
     */
    public Game(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("id must not be blank!");
        }
        this.id = id;
        this.status = Status.PENDING;
        this.bets = new LinkedList<>();
    }

    /**
     * Adds a new {@link PlayerBet} to the game.
     * <p>
     * A game is over when two bets have been placed.
     *
     * @param newPlayerBet the player bet to add.
     * @return <tt>this</tt>, to allow chaining of calls
     * @throws IllegalArgumentException if <var>newPlayerBet</var> is <tt>null</tt>.
     * @throws IllegalStateException    if 2 bets have already been placed.
     */
    public Game addPlayerBet(PlayerBet newPlayerBet) {
        if (newPlayerBet == null) {
            throw new IllegalArgumentException("New PlayerBet must not be null");
        } else if (bets.size() == 2) {
            throw new IllegalStateException("All bets already placed");
        }

        if (bets.isEmpty()) {
            bets.add(newPlayerBet);
            status = Status.ONGOING;
        } else if (bets.size() == 1) {
            bets.add(newPlayerBet);
            completeGame();
        }

        return this;
    }

    /**
     * Performs calculations and field updates necessary for a finished game.
     */
    private void completeGame() {
        Bet bet1 = bets.get(0).getBet();
        Bet bet2 = bets.get(1).getBet();

        if (bet1.beats(bet2)) {
            winner = bets.get(0);
            status = Status.WIN;
        } else if (bet2.beats(bet1)) {
            winner = bets.get(1);
            status = Status.WIN;
        } else {
            winner = null;
            status = Status.DRAW;
        }
    }

    public String getId() {
        return id;
    }

    public List<PlayerBet> getBets() {
        return bets;
    }

    public Status getStatus() {
        return status;
    }

    public PlayerBet getWinner() {
        return winner;
    }

}

package com.matsjonas.rps.model;

/**
 * A Game represents a round of rock-paper-scissors with two players.
 * <p>
 * Each player has a name and a selected bet. Games can end either with a winner och in a draw. To start a new game the
 * constructor {@link Game#Game(String, Bet)} is used with player 1's name and bet. Player 2's name and bet is then
 * applied using the {@link Game#addSecondBet(String, Bet)} method to the Game instance returned by the constructor.
 * Finally the winner is checked in {@link #getWinner()} who returns either the winning player's name or the string
 * {@link Game#RESULT_STRING_DRAW}.
 * <p>
 * Sequence of a game goes as follows.
 * <ol>
 * <li> Create a Game instance with {@link Game#Game(String, Bet)}.
 * <li> Add second player's bet with {@link #addSecondBet(String, Bet)}.
 * <li> Check who won with {@link Game#getWinner()}
 */
public class Game {

    /**
     * The default string representation of a game ending in a draw.
     */
    public static final String RESULT_STRING_DRAW = "RESULT_STRING_DRAW!";

    private String player1Name;
    private String player2Name;
    private Bet player1Bet;
    private Bet player2Bet;

    /**
     * Constructs a new Game instance. Explicitly takes player 1's name and bet.
     *
     * @param player1Name player 1's name.
     * @param player1Bet  player 1's bet.
     * @throws IllegalArgumentException if any argument is null.
     */
    public Game(String player1Name, Bet player1Bet) {
        if (player1Name == null || player1Bet == null) {
            throw new IllegalArgumentException(String.format("All arguments must be non null! player1Name: %s, player1Bet: %s", player1Name, player1Bet));
        }
        this.player1Name = player1Name;
        this.player1Bet = player1Bet;
    }

    /**
     * Applies player 2's name and bet to the game.
     * <p>
     * This method can only be called once, since it wouldn't be fair to let player 2 change it's bet.
     *
     * @param player2Name player 2's name.
     * @param player2Bet  player 2's bet.
     * @return <tt>this</tt> to allow chaining of calls.
     * @throws IllegalArgumentException if any argument is null.
     * @throws IllegalStateException    if a second bet has already been placed.
     */
    public Game addSecondBet(String player2Name, Bet player2Bet) {
        if (player2Name == null || player2Bet == null) {
            throw new IllegalArgumentException(String.format("All arguments must be non null! player2Name: %s, player2Bet: %s", player2Name, player2Bet));
        }
        if (this.player2Name != null || this.player2Bet != null) {
            throw new IllegalStateException(String.format("Second bet already placed! player2Name: %s, player2Bet: %s", player2Name, player2Bet));
        }
        this.player2Name = player2Name;
        this.player2Bet = player2Bet;

        return this;
    }

    /**
     * Checks who won the game.
     * <p>
     * Returns either the winning player name or the string {@link #RESULT_STRING_DRAW}. A player wins the game if the
     * player's bet {@link Bet#beats(Bet) beats} the other player's bet. If no bet beats the other's the game is
     * considered a draw.
     * <p>
     * This method should only be called after {@link #addSecondBet(String, Bet)} and will raise an
     * <tt>IllegalStateException</tt> otherwise.
     *
     * @return the winning player name or the string {@link #RESULT_STRING_DRAW}.
     * @throws IllegalStateException if called before {@link #addSecondBet(String, Bet)}.
     */
    public String getWinner() {
        if (player2Name == null) {
            throw new IllegalStateException("Game is still going on!");
        }

        if (player1Bet.beats(player2Bet)) {
            return player1Name;
        } else if (player2Bet.beats(player1Bet)) {
            return player2Name;
        } else {
            return RESULT_STRING_DRAW;
        }
    }

}

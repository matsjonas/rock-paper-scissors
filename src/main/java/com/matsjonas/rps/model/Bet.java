package com.matsjonas.rps.model;

/**
 * Enum representing the different choices one can make in a game of rock-paper-scissors.
 * <p>
 * Includes the values <tt>ROCK</tt>, <tt>PAPER</tt> and <tt>SCISSORS</tt>.
 */
public enum Bet {

    ROCK, PAPER, SCISSORS;

    /**
     * Checks if the current instance of <tt>Bet</tt> beats <var>otherBet</var>.
     * <p>
     * Returns <tt>true</tt> if <tt>this</tt> beats <var>otherBet</var> according to the rules of rock-paper-scissors,
     * <tt>false</tt> otherwise.
     * The rules are:
     * <ul>
     * <li>ROCK beats SCISSORS
     * <li>PAPER beats ROCK
     * <li>SCISSORS beats PAPER
     *
     * @param otherBet the <tt>Bet</tt> to check against.
     * @return <tt>true</tt> if <tt>this</tt> beats <var>otherBet</var>, <tt>false</tt> otherwise.
     * @throws IllegalArgumentException if <var>otherBet</var> is <tt>null</tt>.
     */
    public boolean beats(Bet otherBet) {

        if (otherBet == null) {
            throw new IllegalArgumentException(String.format("Argument cannot be null when checking if %s beats otherBet", this));
        }

        boolean result = false;

        switch (this) {
            case ROCK:
                result = otherBet == SCISSORS;
                break;
            case PAPER:
                result = otherBet == ROCK;
                break;
            case SCISSORS:
                result = otherBet == PAPER;
                break;
        }

        return result;

    }

}

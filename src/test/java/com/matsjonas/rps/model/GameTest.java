package com.matsjonas.rps.model;

import org.junit.Test;

import static com.matsjonas.rps.model.Bet.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GameTest {

    @Test
    public void constructor() throws Exception {

        try {
            new Game("Adam", ROCK);
        } catch (Exception e) {
            fail("Creating a new game with valid arguments should not fail!");
        }

        try {
            new Game(null, PAPER);
            new Game("Bertil", null);
            new Game(null, null);
            fail("Player name nor bet should be null");
        } catch (IllegalArgumentException ignored) {
            // silent pass
        }

    }

    @Test
    public void addSecondBet() throws Exception {
        Game game = new Game("Cesar", ROCK);

        try {
            game.addSecondBet("David", PAPER);
        } catch (Exception e) {
            fail("Adding the second bet with valid arguments should not fail!");
        }

        try {
            game.addSecondBet("Erik", SCISSORS);
            fail("Adding a second bet twice should throw an exception");
        } catch (IllegalStateException ignored) {
            // silent pass
        }

        game = new Game("Filip", ROCK);

        try {
            game.addSecondBet(null, PAPER);
            game.addSecondBet("Gustav", null);
            game.addSecondBet(null, null);
            fail("Player name nor bet should be null");
        } catch (IllegalArgumentException ignored) {
            // silent pass
        }
    }

    @Test
    public void getWinner() throws Exception {
        Game game = new Game("Helge", ROCK);

        try {
            game.getWinner();
            fail("Game should still be going on since no second bet has been submitted");
        } catch (IllegalStateException ignored) {
            // silent pass
        }

        game.addSecondBet("Ivar", PAPER);
        assertEquals("Winner should be player 2", "Ivar", game.getWinner());

        game = new Game("Johan", ROCK);
        game.addSecondBet("Kalle", SCISSORS);
        assertEquals("Winner should be player 1", "Johan", game.getWinner());

        game = new Game("Ludvig", ROCK);
        game.addSecondBet("Martin", ROCK);
        assertEquals("Game should be a draw", Game.RESULT_STRING_DRAW, game.getWinner());
    }

    @Test
    public void gameLogic() throws Exception {
        // using local variable to increase readability of the code below.
        String drw = Game.RESULT_STRING_DRAW;

        assertEquals(drw, new Game("A", ROCK).addSecondBet("B", ROCK).getWinner());
        assertEquals("B", new Game("A", ROCK).addSecondBet("B", PAPER).getWinner());
        assertEquals("A", new Game("A", ROCK).addSecondBet("B", SCISSORS).getWinner());

        assertEquals(drw, new Game("A", PAPER).addSecondBet("B", PAPER).getWinner());
        assertEquals("A", new Game("A", PAPER).addSecondBet("B", ROCK).getWinner());
        assertEquals("B", new Game("A", PAPER).addSecondBet("B", SCISSORS).getWinner());

        assertEquals(drw, new Game("A", SCISSORS).addSecondBet("B", SCISSORS).getWinner());
        assertEquals("A", new Game("A", SCISSORS).addSecondBet("B", PAPER).getWinner());
        assertEquals("B", new Game("A", SCISSORS).addSecondBet("B", ROCK).getWinner());
    }

}
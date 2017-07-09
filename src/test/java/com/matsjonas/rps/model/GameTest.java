package com.matsjonas.rps.model;

import org.junit.Test;

import static com.matsjonas.rps.model.Bet.PAPER;
import static com.matsjonas.rps.model.Bet.ROCK;
import static com.matsjonas.rps.model.Bet.SCISSORS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

public class GameTest {

    @Test
    public void constructor() throws Exception {

        try {
            new Game();
        } catch (Exception e) {
            fail("Creating a new game with empty constructor should not fail!");
        }

        try {
            new Game("");
            fail("Empty id not allowed!");
        } catch (IllegalArgumentException ignored) {
            // silent pass
        }

        try {
            new Game("   ");
            fail("Blank id not allowed!");
        } catch (IllegalArgumentException ignored) {
            // silent pass
        }

        try {
            new Game(null);
            fail("Null id not allowed!");
        } catch (IllegalArgumentException ignored) {
            // silent pass
        }

        Game game = new Game("abc");

        assertEquals("abc", game.getId());
        assertEquals(Game.Status.PENDING, game.getStatus());
    }

    @Test
    public void addPlayerBet() throws Exception {
        Game game = new Game("xyz");

        assertEquals(Game.Status.PENDING, game.getStatus());

        game.addPlayerBet(new PlayerBet("Adam", ROCK));

        assertEquals(Game.Status.ONGOING, game.getStatus());
        assertEquals("Adam", game.getBets().get(0).getPlayerName());
        assertEquals(ROCK, game.getBets().get(0).getBet());

        game.addPlayerBet(new PlayerBet("Bertil", PAPER));

        assertEquals(Game.Status.WIN, game.getStatus());
        assertEquals("Bertil", game.getBets().get(1).getPlayerName());
        assertEquals(PAPER, game.getBets().get(1).getBet());
        assertEquals(game.getBets().get(1), game.getWinner());

        try {
            game.addPlayerBet(new PlayerBet("Cesar", SCISSORS));
            fail("Too many bets placed");
        } catch (IllegalStateException ignored) {
            // silent pass
        }
    }

    @Test
    public void addPlayerBetInvalidArgs() throws Exception {
        Game game = new Game("qwe");

        try {
            game.addPlayerBet(null);
            fail("PlayerBet cannot be null");
        } catch (IllegalArgumentException ignored) {
            // silent pass
        }

        try {
            game.addPlayerBet(new PlayerBet("", ROCK));
            fail("PlayerBet cannot be null");
        } catch (IllegalArgumentException ignored) {
            // silent pass
        }

        try {
            game.addPlayerBet(new PlayerBet("   ", ROCK));
            fail("PlayerBet cannot be null");
        } catch (IllegalArgumentException ignored) {
            // silent pass
        }

        try {
            game.addPlayerBet(new PlayerBet(null, ROCK));
            fail("PlayerBet cannot be null");
        } catch (IllegalArgumentException ignored) {
            // silent pass
        }

        try {
            game.addPlayerBet(new PlayerBet("Adam", null));
            fail("PlayerBet cannot be null");
        } catch (IllegalArgumentException ignored) {
            // silent pass
        }
    }

    @Test
    public void gameLogic() throws Exception {
        testBetForDrawLooseWin(ROCK, PAPER, SCISSORS);
        testBetForDrawLooseWin(PAPER, SCISSORS, ROCK);
        testBetForDrawLooseWin(SCISSORS, ROCK, PAPER);
    }

    private void testBetForDrawLooseWin(Bet bet, Bet betToLooseAgainst, Bet betToWinAgainst) {
        Game game = new Game("x")
                .addPlayerBet(new PlayerBet("A", bet))
                .addPlayerBet(new PlayerBet("B", bet));

        assertEquals(Game.Status.DRAW, game.getStatus());
        assertNull(game.getWinner());

        game = new Game("x")
                .addPlayerBet(new PlayerBet("A", bet))
                .addPlayerBet(new PlayerBet("B", betToLooseAgainst));

        assertEquals(Game.Status.WIN, game.getStatus());
        assertNotNull(game.getWinner());
        assertEquals("B", game.getWinner().getPlayerName());
        assertEquals(betToLooseAgainst, game.getWinner().getBet());

        game = new Game("x")
                .addPlayerBet(new PlayerBet("A", bet))
                .addPlayerBet(new PlayerBet("B", betToWinAgainst));

        assertEquals(Game.Status.WIN, game.getStatus());
        assertNotNull(game.getWinner());
        assertEquals("A", game.getWinner().getPlayerName());
        assertEquals(bet, game.getWinner().getBet());
    }

}
package com.matsjonas.rps.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PlayerBetTest {

    private PlayerBet playerBetAda;
    private PlayerBet playerBetBertha;
    private PlayerBet playerBetCathy;

    @Before
    public void setUp() {
        playerBetAda = new PlayerBet("Ada", Bet.ROCK);
        playerBetBertha = new PlayerBet("Bertha", Bet.PAPER);
        playerBetCathy = new PlayerBet("Cathy", Bet.SCISSORS);
    }

    @Test
    public void constructor() throws Exception {

        try {
            new PlayerBet();
        } catch (Exception e) {
            fail("Creating a new player bet with empty constructor should not fail!");
        }

        try {
            new PlayerBet("", Bet.ROCK);
            fail("Empty player name not allowed!");
        } catch (IllegalArgumentException ignored) {
            // silent pass
        }

        try {
            new PlayerBet("   ", Bet.ROCK);
            fail("Blank player name not allowed!");
        } catch (IllegalArgumentException ignored) {
            // silent pass
        }

        try {
            new PlayerBet(null, Bet.ROCK);
            fail("Null player name not allowed!");
        } catch (IllegalArgumentException ignored) {
            // silent pass
        }

        try {
            new PlayerBet("Adam", null);
            fail("Null bet not allowed!");
        } catch (IllegalArgumentException ignored) {
            // silent pass
        }

    }

    @Test
    public void getPlayerName() throws Exception {
        assertEquals("Ada", playerBetAda.getPlayerName());
        assertEquals("Bertha", playerBetBertha.getPlayerName());
        assertEquals("Cathy", playerBetCathy.getPlayerName());
    }

    @Test
    public void getBet() throws Exception {
        assertEquals(Bet.ROCK, playerBetAda.getBet());
        assertEquals(Bet.PAPER, playerBetBertha.getBet());
        assertEquals(Bet.SCISSORS, playerBetCathy.getBet());
    }

}
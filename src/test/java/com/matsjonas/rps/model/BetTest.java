package com.matsjonas.rps.model;

import org.junit.Test;

import static com.matsjonas.rps.model.Bet.ROCK;
import static com.matsjonas.rps.model.Bet.PAPER;
import static com.matsjonas.rps.model.Bet.SCISSORS;
import static org.junit.Assert.*;

public class BetTest {

    @Test
    public void beats() throws Exception {

        assertTrue("Rock crushes scissors", ROCK.beats(SCISSORS));
        assertTrue("Paper covers rock", PAPER.beats(ROCK));
        assertTrue("Scissors cut paper", SCISSORS.beats(PAPER));

        assertFalse("Rock only crushes scissors", ROCK.beats(PAPER));
        assertFalse("Paper only covers rock", PAPER.beats(SCISSORS));
        assertFalse("Scissors only cut paper", SCISSORS.beats(ROCK));

        try {
            ROCK.beats(null);
            fail("Argument otherBet should not be allowed to be null");
        } catch (IllegalArgumentException ignored) {
            // silent pass
        }

    }

}

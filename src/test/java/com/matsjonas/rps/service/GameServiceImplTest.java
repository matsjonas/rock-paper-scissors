package com.matsjonas.rps.service;

import com.matsjonas.rps.model.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameServiceImplTest {

    private GameService gameService;

    @Before
    public void setUp() throws Exception {
        gameService = new GameServiceImpl();
    }

    @Test
    public void addGame() throws Exception {
        Game game1 = new Game("game1");
        Game game2 = new Game("game2");

        gameService.addGame(game1);

        assertEquals(1, gameService.getAllGames().size());
        assertEquals(game1, gameService.getAllGames().get(0));

        gameService.addGame(game2);

        assertEquals(2, gameService.getAllGames().size());
        assertEquals(game2, gameService.getAllGames().get(1));
    }

    @Test
    public void removeGame() throws Exception {
        Game game1 = new Game("game1");
        Game game2 = new Game("game2");

        gameService.addGame(game1);
        gameService.addGame(game2);

        assertEquals(2, gameService.getAllGames().size());

        gameService.removeGame("game1");

        assertEquals(1, gameService.getAllGames().size());
        assertEquals(game2, gameService.getAllGames().get(0));

        gameService.removeGame("gameX");

        assertEquals(1, gameService.getAllGames().size());

        gameService.removeGame("game2");

        assertTrue(gameService.getAllGames().isEmpty());
    }

    @Test
    public void getGame() throws Exception {
        Game game1 = new Game("game1");
        Game game2 = new Game("game2");

        gameService.addGame(game1);
        gameService.addGame(game2);

        Game retrievedGame1 = gameService.getGame("game1");
        assertEquals(game1, retrievedGame1);

        Game retrievedGame2 = gameService.getGame("game2");
        assertEquals(game2, retrievedGame2);

        Game retrievedGameX = gameService.getGame("gameX");
        assertNull(retrievedGameX);
    }

    @Test
    public void getAllGames() throws Exception {
        Game game1 = new Game("game1");
        Game game2 = new Game("game2");

        assertTrue(gameService.getAllGames().isEmpty());

        gameService.addGame(game1);
        gameService.addGame(game2);

        assertEquals(2, gameService.getAllGames().size());
        assertTrue(gameService.getAllGames().contains(game1));
        assertTrue(gameService.getAllGames().contains(game2));
    }

}
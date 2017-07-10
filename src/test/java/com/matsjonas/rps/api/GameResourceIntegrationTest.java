package com.matsjonas.rps.api;

import com.matsjonas.rps.model.Bet;
import com.matsjonas.rps.model.Game;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class GameResourceIntegrationTest {

    private static final String URI_GAME_LIST = "http://localhost:8081/rps/api/games";
    private static final String URI_TEMPLATE_GAME = "http://localhost:8081/rps/api/games/%s";
    private static final String URI_TEMPLATE_GAME_BETS = "http://localhost:8081/rps/api/games/%s/bets";

    private static Client client;

    @BeforeClass
    public static void beforeClass() {
        client = ClientBuilder.newClient();
    }

    @Test
    public void testSimpleGameProcedure() throws Exception {
        String gameName = "game1";

        List<Game> allGames = getAllGames();
        int origSize = allGames.size();

        allGames = createGame(gameName);
        assertEquals(origSize + 1, allGames.size());
        Game game = allGames.get(0);
        assertEquals(gameName, game.getId());
        assertEquals(Game.Status.PENDING, game.getStatus());
        assertTrue(game.getBets().isEmpty());

        game = getGame(gameName);
        assertEquals(gameName, game.getId());
        assertEquals(Game.Status.PENDING, game.getStatus());
        assertTrue(game.getBets().isEmpty());

        game = placeBet(gameName, "Adam", "ROCK");
        assertEquals(gameName, game.getId());
        assertEquals(Game.Status.ONGOING, game.getStatus());
        assertEquals(1, game.getBets().size());
        assertNotNull(game.getBets().get(0));
        assertEquals("Adam", game.getBets().get(0).getPlayerName());
        assertEquals(Bet.valueOf("ROCK"), game.getBets().get(0).getBet());

        game = placeBet(gameName, "Bertil", "PAPER");
        assertEquals(gameName, game.getId());
        assertEquals(Game.Status.WIN, game.getStatus());
        assertEquals(2, game.getBets().size());
        assertNotNull(game.getBets().get(0));
        assertEquals("Adam", game.getBets().get(0).getPlayerName());
        assertEquals(Bet.valueOf("ROCK"), game.getBets().get(0).getBet());
        assertNotNull(game.getBets().get(1));
        assertEquals("Bertil", game.getBets().get(1).getPlayerName());
        assertEquals(Bet.valueOf("PAPER"), game.getBets().get(1).getBet());
        assertEquals("Bertil", game.getWinner().getPlayerName());
        assertEquals(Bet.valueOf("PAPER"), game.getWinner().getBet());

    }

    @Test
    public void testTooManyBets() throws Exception {
        String gameName = "game2";

        createGame(gameName);

        Game game = getGame(gameName);
        assertEquals(gameName, game.getId());
        assertEquals(Game.Status.PENDING, game.getStatus());
        assertTrue(game.getBets().isEmpty());

        placeBet(gameName, "Adam", "ROCK");
        placeBet(gameName, "Bertil", "PAPER");

        HashMap<String, String> data;
        data = new HashMap<>();
        data.put("playerName", "Cesar");
        data.put("bet", "SCISSORS");

        Response response = client.target(String.format(URI_TEMPLATE_GAME_BETS, gameName))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(data));

        assertEquals(400, response.getStatus());

        game = getGame(gameName);
        assertEquals(gameName, game.getId());
        assertEquals(Game.Status.WIN, game.getStatus());
        assertEquals(2, game.getBets().size());
    }

    @Test
    public void testDeleteGame() throws Exception {
        String gameName = "game3";

        List<Game> allGames = getAllGames();
        int origSize = allGames.size();

        allGames = createGame(gameName);
        assertEquals(origSize + 1, allGames.size());

        Game game = placeBet(gameName, "Cesar", "ROCK");
        assertEquals(Game.Status.ONGOING, game.getStatus());

        game = placeBet(gameName, "David", "SCISSORS");
        assertEquals(gameName, game.getId());
        assertEquals(Game.Status.WIN, game.getStatus());

        Response response1 = client.target(String.format(URI_TEMPLATE_GAME, gameName))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .delete();

        assertEquals(200, response1.getStatus());
        allGames = getAllGames();
        assertEquals(origSize, allGames.size());

        Response response = client.target(String.format(URI_TEMPLATE_GAME, gameName))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertEquals(404, response.getStatus());
    }

    private List<Game> getAllGames() {
        Response response = client.target(URI_GAME_LIST)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertEquals(200, response.getStatus());

        return response.readEntity(new GenericType<List<Game>>() {});
    }

    private List<Game> createGame(String gameName) {
        HashMap<String, String> data = new HashMap<>();
        data.put("id", gameName);

        Response response = client.target(URI_GAME_LIST)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(data));

        assertEquals(200, response.getStatus());

        return response.readEntity(new GenericType<List<Game>>() {});
    }

    private Game getGame(String gameName) {
        Response response = client.target(String.format(URI_TEMPLATE_GAME, gameName))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertEquals(200, response.getStatus());

        return response.readEntity(Game.class);
    }

    private Game placeBet(String gameName, String playerName, String bet) {
        HashMap<String, String> data;
        data = new HashMap<>();
        data.put("playerName", playerName);
        data.put("bet", bet);

        Response response = client.target(String.format(URI_TEMPLATE_GAME_BETS, gameName))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(data));

        assertEquals(200, response.getStatus());
        
        return response.readEntity(Game.class);
    }

}
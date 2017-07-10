package com.matsjonas.rps.api;

import com.matsjonas.rps.model.Game;
import com.matsjonas.rps.model.PlayerBet;
import com.matsjonas.rps.service.GameService;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Contains all API endpoints for game related management.
 * <p>
 * Sequence of a game goes as follows.
 * <ol>
 * <li> Create a game with {@link #createGame(Game)}.
 * <br> e.g. <tt>POST {"id": "awesomeGame"} /games</tt>
 * <li> (Optional) Check game state with {@link #getGame(String)}.
 * <br> e.g. <tt>GET /games/{id}</tt>
 * <li> Add first bet with {@link #placeBet(String, PlayerBet)}.
 * <br> e.g. <tt>POST {"playerName": "Adam", "bet": "ROCK"} /games/{id}/bets</tt>
 * <li> Add second bet (and end the game) with {@link #placeBet(String, PlayerBet)}.
 * <br> e.g. <tt>POST {"playerName": "Bertil", "bet": "PAPER"} /games/{id}/bets</tt>
 * <br> This will complete the game with either a winner or a draw.
 * </ol>
 * <p>
 * Faulty responses will be served with Jersey's default error response.
 */
@Path("/games")
@Consumes("application/json")
@Produces("application/json")
public class GameResource {

    @Inject
    private GameService gameService;

    /**
     * Retrieve a list of all Games.
     * <p>
     * Method: <tt>GET</tt>
     *
     * @return a list of all Games.
     */
    @GET
    public List<Game> getGames() {
        return gameService.getAllGames();
    }

    /**
     * Create a new game with a specified id.
     * <p>
     * Method: <tt>POST</tt>
     * <p>
     * Required parameters: <tt>id</tt>
     * <p>
     * The argument <var>game</var> is here just a deserialized JSON blob with at least an id specified. The only thing
     * that the needs to be present on <var>game</var> is an id. The value of that id will be used as the id for the
     * actual game to create.
     *
     * @param game a game instance with at least its id set.
     * @return a list of all games.
     * @throws BadRequestException     (400, Bad Request) if <var>game</var> is <tt>null</tt>.
     * @throws BadRequestException     (400, Bad Request) is <var>game.getId()</var> is blank.
     * @throws WebApplicationException (409, Conflict) if a game with id <var>game.getId()</var> already exists.
     */
    @POST
    public List<Game> createGame(Game game) {
        if (game == null) {
            throw new BadRequestException("Invalid request data");
        }

        String gameId = game.getId();
        if (StringUtils.isBlank(gameId)) {
            throw new BadRequestException("Invalid game id");
        }

        Game existingGame = gameService.getGame(gameId);
        if (existingGame != null) {
            throw new WebApplicationException("Game already exists", Response.Status.CONFLICT);
        }

        gameService.addGame(new Game(gameId));
        return gameService.getAllGames();
    }

    /**
     * Retrieves a game with a specified id.
     * <p>
     * Method: <tt>GET</tt>
     *
     * @param id the id of the game.
     * @return the retrieved game.
     * @throws NotFoundException (404, Not Found) if no existing game with id <var>id</var> exists.
     */
    @GET
    @Path("/{id}")
    public Game getGame(@PathParam("id") String id) {
        Game game = gameService.getGame(id);
        if (game == null) {
            throw new NotFoundException();
        }

        return game;
    }

    /**
     * Deletes a game with a specified id.
     * <p>
     * Method: <tt>DELETE</tt>
     *
     * @param id the id of the game.
     * @return an empty HTTP response with status 200, OK.
     * @throws NotFoundException (404, Not Found) if no existing game with id <var>id</var> exists.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteGame(@PathParam("id") String id) {
        Game game = gameService.getGame(id);
        if (game == null) {
            throw new NotFoundException();
        }

        gameService.removeGame(id);
        return Response.ok().build();
    }

    /**
     * Places a new bet on a specified game.
     * <p>
     * Method: <tt>POST</tt>
     * <p>
     * Required parameters: <tt>playerName, bet</tt>
     *
     * @param id        the id of the game.
     * @param playerBet the player name and bet to place.
     * @return the newly created game.
     * @throws BadRequestException (400, Bad Request) if <var>playerBet.getPlayerName()</var> is blank.
     * @throws BadRequestException (400, Bad Request) is <var>playerBet.getBet()</var> is <tt>null</tt>.
     * @throws NotFoundException   (404, Not Found) if no existing game with id <var>id</var> exists.
     */
    @POST
    @Path("/{id}/bets")
    public Game placeBet(@PathParam("id") String id, PlayerBet playerBet) {
        Game game = gameService.getGame(id);
        if (game == null) {
            throw new NotFoundException();
        }

        if (StringUtils.isBlank(playerBet.getPlayerName())) {
            throw new BadRequestException("Invalid playerName");
        } else if (playerBet.getBet() == null) {
            throw new BadRequestException("Invalid bet");
        }

        try {
            game.addPlayerBet(playerBet);
        } catch (IllegalStateException e) {
            throw new BadRequestException(e.getMessage());
        }

        return game;
    }

}

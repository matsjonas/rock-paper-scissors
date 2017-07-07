package com.matsjonas.rps.api;

import com.matsjonas.rps.api.dto.MakeBetDTO;
import com.matsjonas.rps.api.dto.ResponseDTO;
import com.matsjonas.rps.model.Bet;
import com.matsjonas.rps.model.Game;
import com.matsjonas.rps.service.GameService;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Main resource form game management.
 * <p>
 * Has a single endpoint at the path <tt>/game</tt>. Calls to this endpoint using the <tt>POST</tt> method and a JSON
 * matching a {@link MakeBetDTO} will result in a successful request.
 * <p>
 * Currently, only a single game can be active at any time. The first request will create a new game and use the player
 * name and bet as player 1's values. Any subsequent request will be used as player 2's values. When both players have
 * submitted their bets (i.e. after 2 subsequent successful requests) the game will reset and allow for a new game to
 * begin.
 * <p>
 * Each successful request will receive a JSON response represented by a {@link ResponseDTO}. Faulty responses will be
 * served with Jersey's default error response.
 */
@Path("/game")
public class GameResource {

    @Inject
    private GameService gameService;

    /**
     * Endpoint implementation.
     * <p>
     * Performs input validation and handles requests.
     * <p>
     * See {@link GameResource this class'} documentation for full description.
     *
     * @param dto incoming DTO object with information
     * @return a {@link ResponseDTO} with an informative message
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public ResponseDTO makeBet(MakeBetDTO dto) {

        String player = StringUtils.trim(dto.getPlayer());
        String betString = StringUtils.trim(dto.getBet());

        if (StringUtils.isBlank(player)) {
            throw new BadRequestException(String.format("Value of player must be non blank! Was '%s'", player));
        } else if (StringUtils.isBlank(betString)) {
            throw new BadRequestException(String.format("Value of bet must be non blank! Was '%s'", player));
        }

        Bet bet;
        try {
            bet = Bet.valueOf(betString);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(String.format("Invalid value of bet! Was '%s'", betString));
        }

        Game game = gameService.getGame();
        ResponseDTO responseDTO = new ResponseDTO();

        if (game == null) {
            game = new Game(player, bet);
            gameService.setGame(game);
            responseDTO.setMessage("Game started");
        } else {
            game.addSecondBet(player, bet);
            gameService.removeGame();

            if (game.isDraw()) {
                responseDTO.setMessage("Game was a draw");
            } else {
                responseDTO.setMessage(String.format("Winner is %s", game.getWinner()));
            }
        }

        return responseDTO;
    }

}

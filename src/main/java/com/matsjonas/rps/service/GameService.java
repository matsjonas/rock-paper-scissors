package com.matsjonas.rps.service;

import com.matsjonas.rps.model.Game;

import java.util.List;

/**
 * Service layer for managing {@link Game} related actions.
 */
public interface GameService {

    /**
     * Persist a {@link Game} in the underlying persistence layer.
     *
     * @param game the game to add.
     */
    void addGame(Game game);

    /**
     * @param id the id of the game.
     */
    void removeGame(String id);

    /**
     * @param id the id of the game.
     * @return the game identified by <var>id</var>, or <tt>null</tt> if none is found.
     */
    Game getGame(String id);

    /**
     * @return a list of all games currently persisted.
     */
    List<Game> getAllGames();

}

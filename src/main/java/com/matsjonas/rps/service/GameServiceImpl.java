package com.matsjonas.rps.service;

import com.matsjonas.rps.model.Game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Default implementation of {@link GameService}. Manages {@link Game} related actions.
 * <p>
 * This implementation uses a simple {@link HashMap} as its persistence layer. This means that no information is stored
 * between application restarts.
 */
public class GameServiceImpl implements GameService {

    private Map<String, Game> database;

    /**
     * {@inheritDoc}
     */
    public GameServiceImpl() {
        database = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addGame(Game game) {
        database.put(game.getId(), game);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeGame(String id) {
        database.remove(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game getGame(String id) {
        return database.get(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Game> getAllGames() {
        return new LinkedList<>(database.values());
    }

}

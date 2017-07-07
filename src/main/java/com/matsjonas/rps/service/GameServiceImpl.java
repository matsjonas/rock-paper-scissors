package com.matsjonas.rps.service;

import com.matsjonas.rps.model.Game;

public class GameServiceImpl implements GameService {

    private Game game;

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void removeGame() {
        game = null;
    }

    @Override
    public Game getGame() {
        return game;
    }

}

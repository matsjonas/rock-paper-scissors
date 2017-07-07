package com.matsjonas.rps.service;

import com.matsjonas.rps.model.Game;

public interface GameService {

    void setGame(Game game);

    void removeGame();

    Game getGame();

}

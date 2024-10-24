package org.yassine.service.Interface;

import org.yassine.model.Game;

import java.util.List;

public interface IGameService {
    void runSampleOperations();
    boolean createGame(Game game);
    boolean updateGame(Game game);
    boolean deleteGame(Game game);
    Game getGame(int id);
    List<Game> getAllGames();
}
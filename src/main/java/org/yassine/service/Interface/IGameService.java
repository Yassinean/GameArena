package org.yassine.service.Interface;

import org.yassine.model.Game;

import java.util.List;

public interface IGameService {
    boolean createGame(Game game);
    boolean updateGame(Game game);
    boolean deleteGame(int id);
    Game getGame(int id);
    List<Game> getAllGames();
}

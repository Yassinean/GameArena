package org.yassine.dao.Interface;

import org.yassine.model.Game;

import java.util.List;

public interface IGameDao {
    boolean createGame(Game game);
    boolean updateGame(Game game);
    boolean deleteGame(int id);
    Game getGame(int id);
    List<Game> getAllGames();
}

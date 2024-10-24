package org.yassine.service.impl;

import org.yassine.dao.Interface.IGameDao;
import org.yassine.model.Game;
import org.yassine.service.Interface.IGameService;

import java.util.List;

public class GameServiceImp implements IGameService {

    private final IGameDao gameDao;

    // Constructor injection for dependency
    public GameServiceImp(IGameDao gameDao) {
        this.gameDao = gameDao;
    }


    @Override
    public void runSampleOperations() {

    }

    @Override
    public boolean createGame(Game game) {
        return gameDao.createGame(game);
    }

    @Override
    public boolean updateGame(Game game) {
        return gameDao.updateGame(game);
    }

    @Override
    public boolean deleteGame(Game game) {
        return gameDao.deleteGame(game);
    }

    @Override
    public Game getGame(int id) {
        return gameDao.getGame(id);
    }

    @Override
    public List<Game> getAllGames() {
        return gameDao.getAllGames();
    }
}

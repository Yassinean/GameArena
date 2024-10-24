package org.yassine.service.impl;

import org.yassine.dao.Interface.IPlayerDao;
import org.yassine.model.Player;
import org.yassine.service.Interface.IPlayerService;

import java.util.List;

public class PlayerServiceImp implements IPlayerService {

    private final IPlayerDao playerDao;

    // Constructor injection for dependency
    public PlayerServiceImp(IPlayerDao playerDao) {
        this.playerDao = playerDao;
    }


    @Override
    public void runSampleOperations() {

    }

    @Override
    public boolean createPlayer(Player player) {
        return playerDao.createPlayer(player);
    }

    @Override
    public boolean updatePlayer(Player player) {
        return playerDao.updatePlayer(player);
    }

    @Override
    public boolean deletePlayer(int id) {
        return playerDao.deletePlayer(id);
    }

    @Override
    public Player getPlayer(int id) {
        return playerDao.getPlayer(id);
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerDao.getAllPlayers();
    }
}

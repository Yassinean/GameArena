package org.yassine.dao.Interface;

import org.yassine.model.Player;

import java.util.List;

public interface IPlayerDao {
    boolean createPlayer(Player player);
    boolean updatePlayer(Player player);
    boolean deletePlayer(int id);
    Player getPlayer(int id);
    List<Player> getAllPlayers();
}

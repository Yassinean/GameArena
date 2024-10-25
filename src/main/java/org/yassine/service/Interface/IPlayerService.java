package org.yassine.service.Interface;

import org.yassine.model.Player;

import java.util.List;

public interface IPlayerService {
    boolean createPlayer(Player player);
    boolean updatePlayer(Player player);
    boolean deletePlayer(int id);
    Player getPlayer(int id);
    List<Player> getAllPlayers();
}

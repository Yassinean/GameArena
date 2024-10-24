package org.yassine;

import org.yassine.service.impl.GameServiceImp;
import org.yassine.service.impl.PlayerServiceImp;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        PlayerServiceImp playerService = new PlayerService();  // Assuming PlayerService implements IPlayerService
        GameServiceImp gameService = new GameService();  // Assuming GameService implements IGameService
//        TeamServiceImp teamService = new TeamService();  // Assuming TeamService implements ITeamService

        // Create an instance of ConsoleUI with the service objects
//        ConsoleUI consoleUI = new ConsoleUI(playerService, gameService, teamService);

        // Start the main menu
//        consoleUI.showMainMenu();
    }
}
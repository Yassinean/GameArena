package org.yassine.view;

import org.yassine.service.Interface.IGameService;
import org.yassine.service.Interface.IPlayerService;
import org.yassine.service.Interface.ITeamService;
import org.yassine.service.Interface.ITournamentService;
import org.yassine.utils.ValidationUtils;

import java.util.Scanner;

public class ConsoleUI {
    private final PlayerUI playerUI;
    private final GameUI gameUI;
    private final TeamUI teamUI;
    private final TournamentUI tournamentUI;
    private final Scanner scanner = new Scanner(System.in);

    // Constructor to inject services
    public ConsoleUI(IPlayerService playerService, IGameService gameService, ITeamService teamService, ITournamentService tournamentService) {
        this.playerUI = new PlayerUI();
        this.playerUI.setPlayerService(playerService, teamService);

        this.gameUI = new GameUI();
        this.gameUI.setGameService(gameService);

        this.teamUI = new TeamUI();
        this.teamUI.setTeamService(teamService);
        this.teamUI.setPlayerService(playerService);
        this.teamUI.setTournamentService(tournamentService);

        this.tournamentUI = new TournamentUI();
        this.tournamentUI.setTournamentService(tournamentService);
        this.tournamentUI.setGameService(gameService);
    }

    public void showMainMenu() {
        int choice;
        do {
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║          MENU PRINCIPALE           ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║ 1. Gestion des Joueurs             ║");
            System.out.println("║ 2. Gestion des Jeux                ║");
            System.out.println("║ 3. Gestion des Équipes             ║");
            System.out.println("║ 4. Gestion des Tournois            ║");
            System.out.println("║ 5. Exit                            ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.print("Veuillez choisir une option : ");

            choice = ValidationUtils.readInt(); // Use validation method

            switch (choice) {
                case 1:
                    playerUI.showMenu();
                    break;
                case 2:
                    gameUI.showMenu();
                    break;
                case 3:
                    teamUI.showMenu();
                    break;
                case 4:
                    tournamentUI.showMenu();
                    break;
                case 5:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix non valide. Veuillez réessayer.");
            }
        } while (choice != 5);
    }
}

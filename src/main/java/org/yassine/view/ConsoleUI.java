package org.yassine.view;

import org.yassine.service.Interface.IGameService;
import org.yassine.service.Interface.IPlayerService;
import org.yassine.service.Interface.ITeamService;
import org.yassine.service.Interface.ITournamentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ConsoleUI {

    private final Logger logger = LoggerFactory.getLogger(ConsoleUI.class);
    private final PlayerUI playerUI;
    private final GameUI gameUI;
    private final TeamUI teamUI;
    private final TournamentUI tournamentUI;
    private final Scanner scanner = new Scanner(System.in);

    // Constructor to inject services
    public ConsoleUI(IPlayerService playerService, IGameService gameService, ITeamService teamService, ITournamentService tournamentService) {
        this.playerUI = new PlayerUI();
        this.playerUI.setPlayerService(playerService);  // Inject PlayerService

        this.gameUI = new GameUI();
        this.gameUI.setGameService(gameService);  // Inject GameService

        this.teamUI = new TeamUI();
        this.teamUI.setTeamService(teamService);  // Inject TeamService
        this.teamUI.setPlayerService(playerService);  // Inject PlayerService
        this.teamUI.setTournamentService(tournamentService);  // Inject TournamentService

        this.tournamentUI = new TournamentUI();
        this.tournamentUI.setTournamentService(tournamentService);  // Inject TournamentService
        this.tournamentUI.setGameService(gameService);  // Inject GameService
    }

    public void showMainMenu() {
        int choice;
        do {
            logger.info("\n=== Menu Principal ===");
            logger.info("1. Gestion des Joueurs");
            logger.info("2. Gestion des Jeux");
            logger.info("3. Gestion des Équipes");
            logger.info("4. Quitter");
            System.out.print("Veuillez choisir une option : ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    playerUI.showMenu();  // Call Player UI
                    break;
                case 2:
                    gameUI.showMenu();  // Call Game UI
                    break;
                case 3:
                    teamUI.showMenu();  // Call Team UI
                    break;
                case 4:
                    logger.info("Au revoir !");
                    break;
                default:
                    logger.info("Choix non valide. Veuillez réessayer.");
            }
        } while (choice != 4);
    }
}

package org.yassine.view;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yassine.model.Game;
import org.yassine.model.Tournament;
import org.yassine.service.Interface.IGameService;
import org.yassine.service.Interface.ITournamentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TournamentUI {
    private static ITournamentService tournamentService;
    private static IGameService gameService;
    private static Logger logger = LoggerFactory.getLogger(TournamentUI.class);

    // Setter injection
    public void setTournamentService(ITournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    public void setGameService(IGameService gameService) {
        this.gameService = gameService;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            logger.info("\n=== Tournament Management Menu ===");
            logger.info("1. Add a new tournament");
            logger.info("2. Update an existing tournament");
            logger.info("3. View a tournament by ID");
            logger.info("4. View all tournaments");
            logger.info("5. Delete a tournament");
            logger.info("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addTournament(scanner);
                    break;
                case 2:
                    updateTournament(scanner);
                    break;
                case 3:
                    viewTournamentById(scanner);
                    break;
                case 4:
                    viewAllTournaments();
                    break;
                case 5:
                    deleteTournament(scanner);
                    break;
                case 0:
                    logger.info("Exiting...");
                    break;
                default:
                    logger.info("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void addTournament(Scanner scanner) {
        logger.info("\n=== Add New Tournament ===");

        // Step 1: Display existing games from the database
        logger.info("Choose a game from the list or add a new one:");
        List<Game> games = gameService.getAllGames();

        if (games.isEmpty()) {
            logger.info("No games available.");
        } else {
            for (int i = 0; i < games.size(); i++) {
                logger.info((i + 1) + ". " + games.get(i).getNom());
            }
        }

        logger.info((games.size() + 1) + ". Add a new game");
        System.out.print("Enter your choice: ");
        int gameChoice = scanner.nextInt();
        scanner.nextLine();

        Game selectedGame;


        if (gameChoice == games.size() + 1) {
            // Add a new game
            System.out.print("Enter new game name: ");
            String newGameName = scanner.nextLine();

            System.out.print("Enter difficulty (Easy/Medium/Hard): ");
            String newGameDifficulty = scanner.nextLine();

            System.out.print("Enter average match duration (in minutes): ");
            double newGameDuration = scanner.nextDouble();
            scanner.nextLine(); // Consume newline left-over

            selectedGame = new Game();
            selectedGame.setNom(newGameName);
            selectedGame.setDifficulte(newGameDifficulty);
            selectedGame.setDureeMoyenneMatch(newGameDuration);

            // Save the new game to the database
            gameService.createGame(selectedGame);
            logger.info("New game added: " + selectedGame.getNom());
        } else if (gameChoice > 0 && gameChoice <= games.size()) {
            // Select an existing game
            selectedGame = games.get(gameChoice - 1);
            logger.info("Selected game: " + selectedGame.getNom());
        } else {
            logger.info("Invalid choice. Returning to main menu.");
            return;
        }

        // Step 3: Collect the rest of the tournament details
        System.out.print("Enter tournament title: ");
        String title = scanner.nextLine();

        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();

        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();

        // Step 4: Enum validation for Tournament Status
        logger.info("Enter tournament status: ");
        for (Tournament.Statut statut : Tournament.Statut.values()) {
            System.out.println(statut);
        }

        Tournament.Statut status = null;
        while (status == null) {
            System.out.print("Enter tournament status (PLANIFIE/EN_COURS/TERMINE/ANNULE): ");
            String statusInput = scanner.nextLine();
            try {
                status = Tournament.Statut.valueOf(statusInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                logger.info("Invalid status. Please enter a valid status.");
            }
        }

        // Step 5: Create the tournament and save it to the database
        Tournament tournament = new Tournament();
        tournament.setTitre(title);
        tournament.setGame(selectedGame); // Set the selected game
        tournament.setDateDebut(LocalDate.parse(startDate));
        tournament.setDateFin(LocalDate.parse(endDate));
        tournament.setStatut(status); // Use the validated status

        tournamentService.createTournament(tournament);
        logger.info("Tournament added successfully!");
    }


    private static void updateTournament(Scanner scanner) {
        logger.info("\n=== Update Tournament ===");

        System.out.print("Enter the tournament ID to update: ");
        int id = scanner.nextInt();

        Tournament existingTournament = tournamentService.getTournament(id);
        if (existingTournament == null) {
            logger.info("Tournament with ID " + id + " not found.");
            return;
        }
        logger.info("Choose a game from the list or add a new one:");
        List<Game> games = gameService.getAllGames();

        if (games.isEmpty()) {
            logger.info("No games available.");
        } else {
            for (int i = 0; i < games.size(); i++) {
                logger.info((i + 1) + ". " + games.get(i).getNom());
            }
        }

        logger.info((games.size() + 1) + ". Add a new game");
        System.out.print("Enter your choice: ");
        int gameChoice = scanner.nextInt();
        scanner.nextLine();

        Game selectedGame;


        if (gameChoice == games.size() + 1) {
            // Add a new game
            System.out.print("Enter new game name: ");
            String newGameName = scanner.nextLine();

            System.out.print("Enter difficulty (Easy/Medium/Hard): ");
            String newGameDifficulty = scanner.nextLine();

            System.out.print("Enter average match duration (in minutes): ");
            double newGameDuration = scanner.nextDouble();
            scanner.nextLine(); // Consume newline left-over

            selectedGame = new Game();
            selectedGame.setNom(newGameName);
            selectedGame.setDifficulte(newGameDifficulty);
            selectedGame.setDureeMoyenneMatch(newGameDuration);

            gameService.createGame(selectedGame);
            logger.info("New game added: " + selectedGame.getNom());
        } else if (gameChoice > 0 && gameChoice <= games.size()) {
            // Select an existing game
            selectedGame = games.get(gameChoice - 1);
            logger.info("Selected game: " + selectedGame.getNom());
        } else {
            logger.info("Invalid choice. Returning to main menu.");
            return;
        }


        System.out.print("Enter new tournament title: ");
        String newTitle = scanner.nextLine();

        System.out.print("Enter new tournament game: ");
        String newGame = scanner.nextLine();

        System.out.print("Enter new start date (YYYY-MM-DD): ");
        String newStartDate = scanner.nextLine();

        System.out.print("Enter new end date (YYYY-MM-DD): ");
        String newEndDate = scanner.nextLine();

        System.out.print("Enter new tournament status (PLANIFIE / EN_COURS / TERMINE / ANNULE): ");
        String newStatus = scanner.next();

        Tournament updatedTournament = new Tournament();
        updatedTournament.setId(id);
        updatedTournament.setTitre(newTitle);
        updatedTournament.setGame(selectedGame);
        updatedTournament.setDateDebut(LocalDate.parse(newStartDate));
        updatedTournament.setDateFin(LocalDate.parse(newEndDate));
        updatedTournament.setStatut(Tournament.Statut.valueOf(newStatus));

        tournamentService.updateTournament( updatedTournament);
        logger.info("Tournament updated successfully!");
    }

    private static void viewTournamentById(Scanner scanner) {
        logger.info("\n=== View Tournament by ID ===");

        System.out.print("Enter the tournament ID: ");
        int id = scanner.nextInt();

        Tournament tournament = tournamentService.getTournament(id);
        if (tournament != null) {
            logger.info("Tournament ID: " + tournament.getId());
            logger.info("Title: " + tournament.getTitre());
            logger.info("Game: " + tournament.getGame().getNom());
            logger.info("Start Date: " + tournament.getDateDebut());
            logger.info("End Date: " + tournament.getDateFin());
            logger.info("Status: " + tournament.getStatut());
        } else {
            logger.info("Tournament with ID " + id + " not found.");
        }
    }

    private static void viewAllTournaments() {
        logger.info("\n=== View All Tournaments ===");

        List<Tournament> tournaments = tournamentService.getAllTournaments();
        if (tournaments.isEmpty()) {
            logger.info("No tournaments found.");
        } else {
            for (Tournament tournament : tournaments) {
                logger.info("Tournament ID: " + tournament.getId() +
                        ", Title: " + tournament.getTitre() +
                        ", Game: " + tournament.getGame().getNom() +
                        ", Start Date: " + tournament.getDateDebut() +
                        ", End Date: " + tournament.getDateFin() +
                        ", Status: " + tournament.getStatut());
            }
        }
    }

    private static void deleteTournament(Scanner scanner) {
        logger.info("\n=== Delete Tournament ===");

        System.out.print("Enter the tournament ID to delete: ");
        int id = scanner.nextInt();
        Tournament tournament = tournamentService.getTournament(id);

        tournamentService.deleteTournament(id);
        logger.info("Tournament deleted successfully!");
    }
}

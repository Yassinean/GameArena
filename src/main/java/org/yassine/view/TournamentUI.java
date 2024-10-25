package org.yassine.view;

import org.yassine.model.Game;
import org.yassine.model.Tournament;
import org.yassine.service.Interface.IGameService;
import org.yassine.service.Interface.ITournamentService;
import org.yassine.utils.ValidationUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TournamentUI {
    private static ITournamentService tournamentService;
    private static IGameService gameService;

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
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║       TOURNAMENT MANAGEMENT MENU   ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║ 1. Add a new tournament            ║");
            System.out.println("║ 2. Update an existing tournament   ║");
            System.out.println("║ 3. View a tournament by ID         ║");
            System.out.println("║ 4. View all tournaments            ║");
            System.out.println("║ 5. Delete a tournament             ║");
            System.out.println("║ 6. Exit                            ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.print("Enter your choice: ");
            choice = ValidationUtils.readInt();

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
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);

        scanner.close();
    }

    private static void addTournament(Scanner scanner) {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║       ADD NEW TOURNAMENT       ║");
        System.out.println("╚════════════════════════════════╝");

        // Step 1: Display existing games from the database
        System.out.println("Choose a game from the list or add a new one:");
        List<Game> games = gameService.getAllGames();

        if (games.isEmpty()) {
            System.out.println("*********************");
            System.out.println("No games available :(");
            System.out.println("*********************");
        } else {
            for (int i = 0; i < games.size(); i++) {
                System.out.println((i + 1) + ". " + games.get(i).getNom());
            }
        }

        System.out.println((games.size() + 1) + ". Add a new game");
        System.out.print("Enter your choice: ");
        int gameChoice = ValidationUtils.readInt();
        Game selectedGame;

        if (gameChoice == games.size() + 1) {
            System.out.print("Enter new game name => ");
            String newGameName = ValidationUtils.readValidGameName();

            System.out.print("Enter difficulty (Easy/Medium/Hard) => ");
            String newGameDifficulty = scanner.nextLine();

            System.out.print("Enter average match duration (in minutes) => ");
            double newGameDuration = ValidationUtils.readValidDuration();

            selectedGame = new Game();
            selectedGame.setNom(newGameName);
            selectedGame.setDifficulte(newGameDifficulty);
            selectedGame.setDureeMoyenneMatch(newGameDuration);

            // Save the new game to the database
            gameService.createGame(selectedGame);
            System.out.println("\n===========================");
            System.out.println(selectedGame.getNom() + " Cree avec succes");
            System.out.println("=============================");
        } else if (gameChoice > 0 && gameChoice <= games.size()) {
            // Select an existing game
            selectedGame = games.get(gameChoice - 1);
            System.out.println("\n===============================");
            System.out.println("║ Selected game =>" + selectedGame.getNom());
            System.out.println("=================================");
        } else {
            System.out.println("Invalid choice. Returning to main menu.");
            return;
        }

        // Step 3: Collect the rest of the tournament details

        String title = ValidationUtils.readValidTournamentTitle();
        LocalDate startDate = ValidationUtils.readValidDate("Enter start date (YYYY-MM-DD): ");
        LocalDate endDate = ValidationUtils.readValidDate("Enter end date (YYYY-MM-DD): ");

        // Step 4: Enum validation for Tournament Status
        Tournament.Statut status = ValidationUtils.readValidTournamentStatus();

        // Step 5: Create the tournament and save it to the database
        Tournament tournament = new Tournament();
        tournament.setTitre(title);
        tournament.setGame(selectedGame);
        tournament.setDateDebut(startDate);
        tournament.setDateFin(endDate);
        tournament.setStatut(status);

        tournamentService.createTournament(tournament);
        System.out.println("==============================");
        System.out.println("|Tournament added successfully |");
        System.out.println("==============================");
    }

    private static void updateTournament(Scanner scanner) {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║       UPDATE TOURNAMENT        ║");
        System.out.println("╚════════════════════════════════╝");

        System.out.print("Enter the tournament ID to update: ");
        int id = ValidationUtils.readInt(); // Use validation method

        Tournament existingTournament = tournamentService.getTournament(id);
        if (existingTournament == null) {
            System.out.println("******************************");
            System.out.println("Tournament with ID " + id + " not found :(");
            System.out.println("******************************");
            return;
        }

        System.out.print("Enter new tournament title: ");
        String newTitle = ValidationUtils.readValidTournamentTitle();

        LocalDate newStartDate = ValidationUtils.readValidDate("Enter new start date (YYYY-MM-DD): ");
        LocalDate newEndDate = ValidationUtils.readValidDate("Enter new end date (YYYY-MM-DD): ");

        Tournament.Statut newStatus = ValidationUtils.readValidTournamentStatus();

        Tournament updatedTournament = new Tournament();
        updatedTournament.setId(id);
        updatedTournament.setTitre(newTitle);
        updatedTournament.setGame(existingTournament.getGame());
        updatedTournament.setDateDebut(newStartDate);
        updatedTournament.setDateFin(newEndDate);
        updatedTournament.setStatut(newStatus);

        tournamentService.updateTournament(updatedTournament);
        System.out.println("===============================");
        System.out.println("| Tournament updated successfully |");
        System.out.println("===============================");
    }

    private static void viewTournamentById(Scanner scanner) {
        System.out.println("\n╔═════════════════════════════════╗");
        System.out.println("║       VIEW TOURNAMENT BY ID     ║");
        System.out.println("╚═════════════════════════════════╝");
        System.out.print("Enter the tournament ID: ");
        int id = ValidationUtils.readInt(); // Use validation method

        Tournament tournament = tournamentService.getTournament(id);
        if (tournament != null) {
            System.out.println("Tournament ID => " + tournament.getId());
            System.out.println("Title => " + tournament.getTitre());
            System.out.println("Game => " + tournament.getGame().getNom());
            System.out.println("Start Date => " + tournament.getDateDebut());
            System.out.println("End Date => " + tournament.getDateFin());
            System.out.println("Status => " + tournament.getStatut());
        } else {
            System.out.println("*****************************");
            System.out.println("Tournament with ID " + id + " not found :(");
            System.out.println("*****************************");
        }
    }

    private static void viewAllTournaments() {

        System.out.println("\n╔═════════════════════════════════╗");
        System.out.println("║       VIEW ALL TOURNAMENT       ║");
        System.out.println("╚═════════════════════════════════╝");
        List<Tournament> tournaments = tournamentService.getAllTournaments();
        if (tournaments.isEmpty()) {
            System.out.println("*****************");
            System.out.println("No tournaments found :(");
            System.out.println("*****************");
        } else {
            for (Tournament tournament : tournaments) {
                System.out.println("Tournament ID => " + tournament.getId() +
                        ", Title => " + tournament.getTitre() +
                        ", Game => " + tournament.getGame().getNom() +
                        ", Start Date => " + tournament.getDateDebut() +
                        ", End Date => " + tournament.getDateFin() +
                        ", Status => " + tournament.getStatut());
            }
        }
    }

    private static void deleteTournament(Scanner scanner) {
        System.out.println("/n╔═════════════════════════════════╗");
        System.out.println("║        DELETE TOURNAMENT        ║");
        System.out.println("╚═════════════════════════════════╝");
        System.out.print("Enter the tournament ID to delete: ");
        int id = ValidationUtils.readInt(); // Use validation method
        Tournament tournament = tournamentService.getTournament(id);

        if (tournament != null) {
            tournamentService.deleteTournament(id);
            System.out.println("===============================");
            System.out.println("| Tournament deleted successfully |");
            System.out.println("===============================");
        } else {
            System.out.println("*****************************");
            System.out.println("Tournament with ID " + id + " not found :(");
            System.out.println("*****************************");
        }
    }
}

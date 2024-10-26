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
            System.out.println("║ 0. Exit                            ║");
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
        } while (choice != 0);
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
        LocalDate endDate;

        while (true) {
            endDate = ValidationUtils.readValidDate("Enter end date (YYYY-MM-DD): ");
            if (endDate.isAfter(startDate)) {
                break; // Valid date range
            } else {
                System.out.println("Erreur : La date de fin doit être postérieure à la date de début.");
            }
        }
        Tournament.Statut status = ValidationUtils.readValidTournamentStatus();
        System.out.print("Enter estimated duration of the tournament (in hours): ");
        double dureeEstimee = ValidationUtils.readValidDuration();

        System.out.print("Enter break time between matches (in minutes): ");
        double tempsPauseEntreMatchs =  ValidationUtils.readValidDuration();

        System.out.print("Enter number of spectators: ");
        int nbSpec = ValidationUtils.readInt();
        scanner.nextLine();

        System.out.print("Enter ceremony time (in minutes): ");
        double tempsCeremonie = scanner.nextDouble();
        scanner.nextLine();


        // Step 5: Create the tournament and save it to the database
        Tournament tournament = new Tournament();
        tournament.setTitre(title);
        tournament.setGame(selectedGame);
        tournament.setDateDebut(startDate);
        tournament.setDateFin(endDate);
        tournament.setStatut(status);
        tournament.setDureeEstimee(dureeEstimee);
        tournament.setTempsPauseEntreMatchs(tempsPauseEntreMatchs);
        tournament.setSpectators(nbSpec);
        tournament.setTempsCeremonie(tempsCeremonie);
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
        int id = ValidationUtils.readInt();

        Tournament existingTournament = tournamentService.getTournament(id);
        if (existingTournament == null) {
            System.out.println("******************************");
            System.out.println("Tournament with ID " + id + " not found :(");
            System.out.println("******************************");
            return;
        }

        while (true) {
            System.out.println("\nWhat would you like to update?");
            System.out.println("╔════════════════════════════════════════╗");
            System.out.printf("║ %s : %-35s ║%n", "1", "Title (Current: " + existingTournament.getTitre() + ")");
            System.out.printf("║ %s : %-35s ║%n", "2", "Start Date (Current: " + existingTournament.getDateDebut() + ")");
            System.out.printf("║ %s : %-35s ║%n", "3", "End Date (Current: " + existingTournament.getDateFin() + ")");
            System.out.printf("║ %s : %-35s ║%n", "4", "Status (Current: " + existingTournament.getStatut() + ")");
            System.out.printf("║ %s : %-35s ║%n", "5", "Estimated Duration (Current: " + existingTournament.getDureeEstimee() + " hours)");
            System.out.printf("║ %s : %-35s ║%n", "6", "Break Time (Current: " + existingTournament.getTempsPauseEntreMatchs() + " minutes)");
            System.out.printf("║ %s : %-35s ║%n", "7", "Number of Spectators (Current: " + existingTournament.getSpectators() + ")");
            System.out.printf("║ %s : %-35s ║%n", "8", "Ceremony Time (Current: " + existingTournament.getTempsCeremonie() + " minutes)");
            System.out.printf("║ %s : %-35s ║%n", "9", "Save Changes and Exit");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Your choice => ");
            int choice = ValidationUtils.readInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter new tournament title: ");
                    String newTitle = ValidationUtils.readValidTournamentTitle();
                    existingTournament.setTitre(newTitle);
                    break;
                case 2:
                    LocalDate newStartDate = ValidationUtils.readValidDate("Enter new start date (YYYY-MM-DD): ");
                    existingTournament.setDateDebut(newStartDate);
                    break;
                case 3:
                    LocalDate newEndDate = ValidationUtils.readValidDate("Enter new end date (YYYY-MM-DD): ");
                    existingTournament.setDateFin(newEndDate);
                    break;
                case 4:
                    Tournament.Statut newStatus = ValidationUtils.readValidTournamentStatus();
                    existingTournament.setStatut(newStatus);
                    break;
                case 5:
                    System.out.print("Enter estimated duration of the tournament (in hours): ");
                    double newDureeEstimee = ValidationUtils.readValidDuration();
                    existingTournament.setDureeEstimee(newDureeEstimee);
                    break;
                case 6:
                    System.out.print("Enter break time between matches (in minutes): ");
                    double newTempsPauseEntreMatchs = ValidationUtils.readValidDuration();
                    existingTournament.setTempsPauseEntreMatchs(newTempsPauseEntreMatchs);
                    break;
                case 7:
                    System.out.print("Enter number of spectators: ");
                    int newNbSpec = ValidationUtils.readInt();
                    existingTournament.setSpectators(newNbSpec);
                    break;
                case 8:
                    System.out.print("Enter ceremony time (in minutes): ");
                    double newTempsCeremonie = scanner.nextDouble();
                    scanner.nextLine();
                    existingTournament.setTempsCeremonie(newTempsCeremonie);
                    break;
                case 9:
                    tournamentService.updateTournament(existingTournament);
                    System.out.println("===============================");
                    System.out.println("| Tournament updated successfully |");
                    System.out.println("===============================");
                    return; // Exit the update loop
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }


    private static void viewTournamentById(Scanner scanner) {
        System.out.println("\n╔═════════════════════════════════╗");
        System.out.println("║       VIEW TOURNAMENT BY ID     ║");
        System.out.println("╚═════════════════════════════════╝");

        System.out.print("Enter the tournament ID: ");
        int id = ValidationUtils.readInt(); // Use validation method

        Tournament tournament = tournamentService.getTournament(id);
        if (tournament != null) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.printf("║ Tournament ID      : %d%n", tournament.getId());
            System.out.printf("║ Title             : %s%n", tournament.getTitre());
            System.out.printf("║ Game              : %s%n", tournament.getGame().getNom());
            System.out.printf("║ Start Date        : %s%n", tournament.getDateDebut());
            System.out.printf("║ End Date          : %s%n", tournament.getDateFin());
            System.out.printf("║ Status            : %s%n", tournament.getStatut());
            System.out.printf("║ Estimated Duration : %.2f hours%n", tournament.getDureeEstimee());
            System.out.printf("║ Ceremony Time     : %.2f minutes%n", tournament.getTempsCeremonie());
            System.out.printf("║ Break Time        : %.2f minutes%n", tournament.getTempsPauseEntreMatchs());
            System.out.printf("║ Spectators        : %d%n", tournament.getSpectators());
            System.out.println("╚══════════════════════════════════════════╝");
        } else {
            System.out.println("*****************************");
            System.out.println("Tournament with ID " + id + " not found :(");
            System.out.println("*****************************");
        }
    }

    private static void viewAllTournaments() {
        System.out.println("\n╔═════════════════════════════════╗");
        System.out.println("║       VIEW ALL TOURNAMENTS      ║");
        System.out.println("╚═════════════════════════════════╝");

        List<Tournament> tournaments = tournamentService.getAllTournaments();
        if (tournaments.isEmpty()) {
            System.out.println("*****************");
            System.out.println("No tournaments found :(");
            System.out.println("*****************");
        } else {
            System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
            for (Tournament tournament : tournaments) {
                System.out.printf("║ Tournament ID      : %d%n", tournament.getId());
                System.out.printf("║ Title             : %s%n", tournament.getTitre());
                System.out.printf("║ Game              : %s%n", tournament.getGame().getNom());
                System.out.printf("║ Start Date        : %s%n", tournament.getDateDebut());
                System.out.printf("║ End Date          : %s%n", tournament.getDateFin());
                System.out.printf("║ Status            : %s%n", tournament.getStatut());
                System.out.printf("║ Estimated Duration : %.2f hours%n", tournament.getDureeEstimee());
                System.out.printf("║ Ceremony Time     : %.2f minutes%n", tournament.getTempsCeremonie());
                System.out.printf("║ Break Time        : %.2f minutes%n", tournament.getTempsPauseEntreMatchs());
                System.out.printf("║ Spectators        : %d%n", tournament.getSpectators());
                System.out.println("╠══════════════════════════════════════════════════════════════════════╣");
            }
            System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
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

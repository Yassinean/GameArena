package org.yassine.view;

import org.springframework.context.ApplicationContext;
import org.yassine.model.Game;
import org.yassine.provider.ApplicationContextProvider;
import org.yassine.service.Interface.IGameService;
import org.yassine.utils.ValidationUtils;

import java.util.List;
import java.util.Scanner;

public class GameUI {
    private static IGameService gameService;

    public void setGameService(IGameService gameService) {
        GameUI.gameService = gameService;
    }

    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║       GAME MANAGAMENT MENU         ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║ 1. Creer un nouvel    jeu          ║");
            System.out.println("║ 2. Modifier un jeu                 ║");
            System.out.println("║ 3. View jeu by ID                  ║");
            System.out.println("║ 4. View all games                  ║");
            System.out.println("║ 5. Supprimer un jeu                ║");
            System.out.println("║ 0. EXIT                            ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.print("Enter your choice: ");
            choice = ValidationUtils.readInt(); // Use validation method

            switch (choice) {
                case 1 -> addGame(scanner);
                case 2 -> updateGame(scanner);
                case 3 -> viewGameById(scanner);
                case 4 -> viewAllGames();
                case 5 -> deleteGame(scanner);
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void addGame(Scanner scanner) {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║          ADD NEW GAME          ║");
        System.out.println("╚════════════════════════════════╝");

        System.out.print("Enter game name => ");
        String name = ValidationUtils.readValidGameName();

        System.out.print("Enter difficulty (Easy/Medium/Hard) => ");
        String difficulty = ValidationUtils.readValidDifficulty();

        System.out.print("Enter average match duration (in minutes) => ");
        double duration = ValidationUtils.readValidDuration();

        Game newGame = new Game();
        newGame.setNom(name);
        newGame.setDifficulte(difficulty);
        newGame.setDureeMoyenneMatch(duration);

        gameService.createGame(newGame);
        System.out.println("===========================");
        System.out.println("| Game added successfully |");
        System.out.println("===========================");
    }

    private static void updateGame(Scanner scanner) {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║           UPDATE GAME          ║");
        System.out.println("╚════════════════════════════════╝");

        System.out.print("Enter the game ID to update => ");
        int id = ValidationUtils.readInt();

        Game existingGame = gameService.getGame(id);
        if (existingGame == null) {
            System.out.println("***********************");
            System.out.println("Game with ID " + id + " not found :(");
            System.out.println("***********************");
            return;
        }

        while (true) {
            System.out.println("\nWhat would you like to update?");
            System.out.println("╔════════════════════════════════════════╗");
            System.out.printf("║ %s : %-35s ║%n", "1", "Name (Current: " + existingGame.getNom() + ")");
            System.out.printf("║ %s : %-35s ║%n", "2", "Difficulty (Current: " + existingGame.getDifficulte() + ")");
            System.out.printf("║ %s : %-35s ║%n", "3", "Average Match Duration (Current: " + existingGame.getDureeMoyenneMatch() + " minutes)");
            System.out.printf("║ %s : %-35s ║%n", "4", "Save Changes and Exit");
            System.out.println("╚════════════════════════════════════════╝");

            System.out.print("Your choice => ");
            int choice = ValidationUtils.readInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter new game name (leave blank to keep current): ");
                    String newName = scanner.nextLine();
                    if (!newName.trim().isEmpty()) {
                        existingGame.setNom(newName);
                    }
                    break;
                case 2:
                    System.out.print("Enter new difficulty (Easy/Medium/Hard): ");
                    String newDifficulty = ValidationUtils.readValidDifficulty();
                    existingGame.setDifficulte(newDifficulty);
                    break;
                case 3:
                    System.out.print("Enter new average match duration (in minutes): ");
                    double newDuration = ValidationUtils.readValidDuration();
                    existingGame.setDureeMoyenneMatch(newDuration);
                    break;
                case 4:
                    gameService.updateGame(existingGame);
                    System.out.println("===========================");
                    System.out.println("| Game updated successfully |");
                    System.out.println("===========================");
                    return; // Exit the update loop
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void viewGameById(Scanner scanner) {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║            VIEW GAME           ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.print("Enter the game ID => ");
        int id = ValidationUtils.readInt();

        Game game = gameService.getGame(id);
        if (game != null) {
            System.out.println("+-------------------------------+--------------------------+");
            System.out.println("|           Game Info          |                          |");
            System.out.println("+-------------------------------+--------------------------+");
            System.out.printf("| Game ID          | %-24s |\n", game.getId());
            System.out.printf("| Name             | %-24s |\n", game.getNom());
            System.out.printf("| Difficulty       | %-24s |\n", game.getDifficulte());
            System.out.printf("| Avg Match Duration| %-21s |\n", game.getDureeMoyenneMatch() + " minutes");
            System.out.println("+-------------------------------+--------------------------+");

        } else {
            System.out.println("**************************");
            System.out.println("Game with ID " + id + " not found :(");
            System.out.println("**************************");
        }
    }

    private static void deleteGame(Scanner scanner) {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║           DELETE GAME          ║");
        System.out.println("╚════════════════════════════════╝");

        System.out.print("Enter the game ID to delete => ");
        int id = ValidationUtils.readInt();
        Game game = gameService.getGame(id);

        if (game != null) {
            gameService.deleteGame(id);
            System.out.println("===========================");
            System.out.println("| Game deleted successfully |");
            System.out.println("===========================");
        } else {
            System.out.println("*************************");
            System.out.println("Game with ID " + id + " not found :(");
            System.out.println("*************************");
        }
    }

    private static void viewAllGames() {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║          VIEW ALL GAMES        ║");
        System.out.println("╚════════════════════════════════╝");

        List<Game> games = gameService.getAllGames();
        if (games.isEmpty()) {
            System.out.println("===========================");
            System.out.println("| No games found :(");
            System.out.println("===========================");
        } else {
            System.out.println("+-------------------------------+--------------------------+");
            System.out.println("|           Game List          |                          |");
            System.out.println("+-------------------------------+--------------------------+");
            for (Game game : games) {
                System.out.printf("| Game ID          | %-24s |\n", game.getId());
                System.out.printf("| Name             | %-24s |\n", game.getNom());
                System.out.printf("| Difficulty       | %-24s |\n", game.getDifficulte());
                System.out.printf("| Avg Match Duration| %-21s |\n", game.getDureeMoyenneMatch() + " minutes");
                System.out.println("+-------------------------------+--------------------------+");
            }
            System.out.println("+-------------------------------+--------------------------+");

        }
    }
}


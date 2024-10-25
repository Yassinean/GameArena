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
        this.gameService = gameService;
    }

    public static void showMenu() {
        ApplicationContext context = ApplicationContextProvider.getContext();
        gameService = (IGameService) context.getBean("gameService");
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Game Management Menu ===");
            System.out.println("1. Add a new game");
            System.out.println("2. Update an existing game");
            System.out.println("3. View a game by ID");
            System.out.println("4. View all games");
            System.out.println("5. Delete a game");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = ValidationUtils.readInt(); // Use validation method

            switch (choice) {
                case 1:
                    addGame(scanner);
                    break;
                case 2:
                    updateGame(scanner);
                    break;
                case 3:
                    viewGameById(scanner);
                    break;
                case 4:
                    viewAllGames();
                    break;
                case 5:
                    deleteGame(scanner);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void addGame(Scanner scanner) {
        System.out.println("\n=== Add New Game ===");

        System.out.print("Enter game name: ");
        String name = ValidationUtils.readValidGameName(); // Use the new validation method

        System.out.print("Enter difficulty (Easy/Medium/Hard): ");
        String difficulty = ValidationUtils.readValidDifficulty(); // Use the new validation method

        System.out.print("Enter average match duration (in minutes): ");
        double duration = ValidationUtils.readValidDuration(); // Use the new validation method

        Game newGame = new Game();
        newGame.setNom(name);
        newGame.setDifficulte(difficulty);
        newGame.setDureeMoyenneMatch(duration);

        gameService.createGame(newGame);
        System.out.println("Game added successfully!");
    }

    private static void updateGame(Scanner scanner) {
        System.out.println("\n=== Update Game ===");

        System.out.print("Enter the game ID to update: ");
        int id = ValidationUtils.readInt(); // Use validation method

        Game existingGame = gameService.getGame(id);
        if (existingGame == null) {
            System.out.println("Game with ID " + id + " not found.");
            return;
        }

        System.out.print("Enter new game name: ");
        String newName = ValidationUtils.readValidGameName(); // Use validation method

        System.out.print("Enter new difficulty (Easy/Medium/Hard): ");
        String newDifficulty = ValidationUtils.readValidDifficulty(); // Use validation method

        System.out.print("Enter new average match duration (in minutes): ");
        double newDuration = ValidationUtils.readValidDuration(); // Use validation method

        existingGame.setNom(newName);
        existingGame.setDifficulte(newDifficulty);
        existingGame.setDureeMoyenneMatch(newDuration);

        gameService.updateGame(existingGame);
        System.out.println("Game updated successfully!");
    }

    private static void viewGameById(Scanner scanner) {
        System.out.println("\n=== View Game by ID ===");

        System.out.print("Enter the game ID: ");
        int id = ValidationUtils.readInt(); // Use validation method

        Game game = gameService.getGame(id);
        if (game != null) {
            System.out.println("Game ID: " + game.getId());
            System.out.println("Name: " + game.getNom());
            System.out.println("Difficulty: " + game.getDifficulte());
            System.out.println("Average Match Duration: " + game.getDureeMoyenneMatch() + " minutes");
        } else {
            System.out.println("Game with ID " + id + " not found.");
        }
    }

    private static void deleteGame(Scanner scanner) {
        System.out.println("\n=== Delete Game ===");

        System.out.print("Enter the game ID to delete: ");
        int id = ValidationUtils.readInt(); // Use validation method
        Game game = gameService.getGame(id);

        if (game != null) {
            gameService.deleteGame(game.getId());
            System.out.println("Game deleted successfully!");
        } else {
            System.out.println("Game with ID " + id + " not found.");
        }
    }

    private static void viewAllGames() {
        System.out.println("\n=== View All Games ===");

        List<Game> games = gameService.getAllGames();
        if (games.isEmpty()) {
            System.out.println("No games found.");
        } else {
            for (Game game : games) {
                System.out.println("Game ID: " + game.getId() +
                        ", Name: " + game.getNom() +
                        ", Difficulty: " + game.getDifficulte() +
                        ", Average Match Duration: " + game.getDureeMoyenneMatch() + " minutes");
            }
        }
    }
}

package org.yassine.view;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yassine.model.Game;
import org.yassine.service.Interface.IGameService;

import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameUI {
    private static IGameService gameService;
    private static Logger logger = LoggerFactory.getLogger(GameUI.class);

    public void setGameService(IGameService gameService) {
        this.gameService = gameService;
    }

    public static void showMenu() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        gameService = (IGameService) context.getBean("gameService");
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            logger.info("\n=== Game Management Menu ===");
            logger.info("1. Add a new game");
            logger.info("2. Update an existing game");
            logger.info("3. View a game by ID");
            logger.info("4. View all games");
            logger.info("5. Delete a game");
            logger.info("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

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
                    logger.info("Exiting...");
                    break;
                default:
                    logger.info("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void addGame(Scanner scanner) {
        logger.info("\n=== Add New Game ===");

        System.out.print("Enter game name: ");
        scanner.nextLine();  // Consume newline
        String name = scanner.nextLine();

        System.out.print("Enter difficulty (Easy/Medium/Hard): ");
        String difficulty = scanner.nextLine();

        System.out.print("Enter average match duration (in minutes): ");
        double duration = scanner.nextDouble();
        scanner.nextLine();

        Game newGame = new Game();
        newGame.setNom(name);
        newGame.setDifficulte(difficulty);
        newGame.setDureeMoyenneMatch(duration);

        gameService.createGame(newGame);
        logger.info("Game added successfully!");
    }
    private static void updateGame(Scanner scanner) {
        logger.info("\n=== Update Game ===");

        System.out.print("Enter the game ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Game existingGame = gameService.getGame(id);
        if (existingGame == null) {
            logger.info("Game with ID " + id + " not found.");
            return;
        }

        System.out.print("Enter new game name: ");
        String newName = scanner.nextLine();

        System.out.print("Enter new difficulty (Easy/Medium/Hard): ");
        String newDifficulty = scanner.nextLine();

        System.out.print("Enter new average match duration (in minutes): ");
        double newDuration = scanner.nextDouble();

        existingGame.setNom(newName);
        existingGame.setDifficulte(newDifficulty);
        existingGame.setDureeMoyenneMatch(newDuration);

        gameService.updateGame(existingGame);
        logger.info("Game updated successfully!");
    }
    private static void viewGameById(Scanner scanner) {
        logger.info("\n=== View Game by ID ===");

        System.out.print("Enter the game ID: ");
        int id = scanner.nextInt();

        Game game = gameService.getGame(id);
        if (game != null) {
            logger.info("Game ID: " + game.getId());
            logger.info("Name: " + game.getNom());
            logger.info("Difficulty: " + game.getDifficulte());
            logger.info("Average Match Duration: " + game.getDureeMoyenneMatch() + " minutes");
        } else {
            logger.info("Game with ID " + id + " not found.");
        }
    }
    private static void deleteGame(Scanner scanner) {
        logger.info("\n=== Delete Game ===");

        System.out.print("Enter the game ID to delete: ");
        int id = scanner.nextInt();
        Game game = gameService.getGame(id);

        if (game != null) {
            gameService.deleteGame(game.getId());
            logger.info("Game deleted successfully!");
        } else {
            logger.info("Game with ID " + id + " not found.");
        }
    }
    private static void viewAllGames() {
        logger.info("\n=== View All Games ===");

        List<Game> games = gameService.getAllGames();
        if (games.isEmpty()) {
            logger.info("No games found.");
        } else {
            for (Game game : games) {
                logger.info("Game ID: " + game.getId() +
                        ", Name: " + game.getNom() +
                        ", Difficulty: " + game.getDifficulte() +
                        ", Average Match Duration: " + game.getDureeMoyenneMatch() + " minutes");
            }
        }
    }


}


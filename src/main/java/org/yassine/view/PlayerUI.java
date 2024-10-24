package org.yassine.view;


import org.yassine.model.Player;
import org.yassine.service.Interface.IPlayerService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerUI {
    private static final Logger logger = LoggerFactory.getLogger(PlayerUI.class);
    private IPlayerService playerService;
    Scanner scanner = new Scanner(System.in);

    public void setPlayerService(IPlayerService playerService) {
        this.playerService = playerService;
    }

    public void showMenu() {
        int choice;
        do {
            logger.info("\n=== Menu de Gestion des Players ===");
            logger.info("1. Créer un joueur");
            logger.info("2. Modifier un joueur");
            logger.info("3. Supprimer un joueur");
            logger.info("4. Afficher un joueur");
            logger.info("5. Afficher tous les joueurs");
            logger.info("6. Quitter");
            logger.info("Veuillez choisir une option : ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    createPlayer();
                    break;
                case 2:
                    updatePlayer();
                    break;
                case 3:
                    deletePlayer();
                    break;
                case 4:
                    displayPlayer();
                    break;
                case 5:
                    displayAllPlayers();
                    break;
                case 6:
                    logger.info("Au revoir!");
                    break;
                default:
                    logger.info("Choix non valide.");
            }
        } while (choice != 6);
    }

    private void createPlayer() {
        logger.info("\n=== Créer un Player ===");
        logger.info("Entrez le pseudo du joueur : ");
        String pseudo = scanner.nextLine();
        logger.info("Entrez l'âge du joueur : ");
        int age = Integer.parseInt(scanner.nextLine());

        Player player = new Player();
        player.setPseudo(pseudo);
        player.setAge(age);

        playerService.createPlayer(player);
        logger.info("Player créé avec succès !");
    }

    private void updatePlayer() {
        logger.info("\n=== Modifier un Player ===");
        logger.info("Entrez l'ID du joueur à modifier : ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Player> playerOpt = Optional.ofNullable(playerService.getPlayer(id));
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            logger.info("Nouveau pseudo : ");
            player.setPseudo(scanner.nextLine());
            logger.info("Nouvel âge : ");
            player.setAge(Integer.parseInt(scanner.nextLine()));

            playerService.updatePlayer(player);
            logger.info("Joueur modifié avec succès !");
        } else {
            logger.info("Player introuvable.");
        }
    }

    private void deletePlayer() {
        logger.info("\n=== Supprimer un Player ===");
        logger.info("Entrez l'ID du joueur à supprimer : ");
        int id = Integer.parseInt(scanner.nextLine());

        if (playerService.deletePlayer(id)) {
            logger.info("Joueur supprimé avec succès !");
        } else {
            logger.info("Erreur lors de la suppression du joueur.");
        }
    }

    private void displayPlayer() {
        logger.info("\n=== Afficher un Player ===");
        logger.info("Entrez l'ID du joueur : ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Player> playerOpt = Optional.ofNullable(playerService.getPlayer(id));
        playerOpt.ifPresentOrElse(player -> {
            logger.info("Pseudo : " + player.getPseudo());
            logger.info("Âge : " + player.getAge());
        }, () -> logger.info("Player introuvable."));
    }

    private void displayAllPlayers() {
        logger.info("\n=== Afficher tous les Players ===");
        List<Player> players = playerService.getAllPlayers();
        players.forEach(player -> {
            logger.info("ID : " + player.getId());
            logger.info("Pseudo : " + player.getPseudo());
            logger.info("Âge : " + player.getAge());
            logger.info("--------------");
        });
    }
}

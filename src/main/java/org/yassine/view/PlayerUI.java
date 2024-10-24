package org.yassine.view;


import org.yassine.model.Player;
import org.yassine.service.Interface.IPlayerService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PlayerUI {
    private final Scanner scanner = new Scanner(System.in);
    private IPlayerService playerService;

    public void setPlayerService(IPlayerService playerService) {
        this.playerService = playerService;
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n=== Menu de Gestion des Players ===");
            System.out.println("1. Créer un joueur");
            System.out.println("2. Modifier un joueur");
            System.out.println("3. Supprimer un joueur");
            System.out.println("4. Afficher un joueur");
            System.out.println("5. Afficher tous les joueurs");
            System.out.println("6. Quitter");
            System.out.print("Veuillez choisir une option : ");

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
                    System.out.println("Au revoir!");
                    break;
                default:
                    System.out.println("Choix non valide.");
            }
        } while (choice != 6);
    }

    private void createPlayer() {
        System.out.println("\n=== Créer un Player ===");
        System.out.print("Entrez le pseudo du joueur : ");
        String pseudo = scanner.nextLine();
        System.out.print("Entrez l'âge du joueur : ");
        int age = Integer.parseInt(scanner.nextLine());

        Player player = new Player();
        player.setPseudo(pseudo);
        player.setAge(age);

        playerService.createPlayer(player);
        System.out.println("Player créé avec succès !");
    }

    private void updatePlayer() {
        System.out.println("\n=== Modifier un Player ===");
        System.out.print("Entrez l'ID du joueur à modifier : ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Player> playerOpt = Optional.ofNullable(playerService.getPlayer(id));
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            System.out.print("Nouveau pseudo : ");
            player.setPseudo(scanner.nextLine());
            System.out.print("Nouvel âge : ");
            player.setAge(Integer.parseInt(scanner.nextLine()));

            playerService.updatePlayer(player);
            System.out.println("Joueur modifié avec succès !");
        } else {
            System.out.println("Player introuvable.");
        }
    }

    private void deletePlayer() {
        System.out.println("\n=== Supprimer un Player ===");
        System.out.print("Entrez l'ID du joueur à supprimer : ");
        int id = Integer.parseInt(scanner.nextLine());

        if (playerService.deletePlayer(id)) {
            System.out.println("Joueur supprimé avec succès !");
        } else {
            System.out.println("Erreur lors de la suppression du joueur.");
        }
    }

    private void displayPlayer() {
        System.out.println("\n=== Afficher un Player ===");
        System.out.print("Entrez l'ID du joueur : ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Player> playerOpt = Optional.ofNullable(playerService.getPlayer(id));
        playerOpt.ifPresentOrElse(player -> {
            System.out.println("Pseudo : " + player.getPseudo());
            System.out.println("Âge : " + player.getAge());
        }, () -> System.out.println("Player introuvable."));
    }

    private void displayAllPlayers() {
        System.out.println("\n=== Afficher tous les Players ===");
        List<Player> players = playerService.getAllPlayers();
        players.forEach(player -> {
            System.out.println("ID : " + player.getId());
            System.out.println("Pseudo : " + player.getPseudo());
            System.out.println("Âge : " + player.getAge());
            System.out.println("--------------");
        });
    }
}

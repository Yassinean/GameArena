package org.yassine.view;

import org.yassine.model.Player;
import org.yassine.model.Team;
import org.yassine.service.Interface.IPlayerService;
import org.yassine.service.Interface.ITeamService;
import org.yassine.utils.ValidationUtils;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PlayerUI {
    private IPlayerService playerService;
    private ITeamService teamService;
    Scanner scanner = new Scanner(System.in);

    public void setPlayerService(IPlayerService playerService, ITeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║      PLAYERS MANAGAMENT MENU       ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║ 1. Creer un joueur                 ║");
            System.out.println("║ 2. Modifier un joueur              ║");
            System.out.println("║ 3. Supprimer une equipe            ║");
            System.out.println("║ 4. Afficher un joueur              ║");
            System.out.println("║ 5. Afficher tous les joueurs       ║");
            System.out.println("║ 6. Assigner un joueur a une equipe ║");
            System.out.println("║ 0. EXIT                            ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.print("Veuillez choisir une option : ");

            choice = ValidationUtils.readInt();

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
                    assignPlayerToTeam();
                    break;
                case 0:
                    System.out.println("Au revoir!");
                    return;
                default:
                    System.out.println("Choix non valide.");
            }
        } while (choice != 0);
    }

    private void createPlayer() {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║         ADD NEW PLAYER         ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.print("Entrez le pseudo du joueur => ");
        String pseudo = ValidationUtils.readValidPseudo();
        System.out.print("Entrez l'âge du joueur => ");
        int age = ValidationUtils.readValidAge();

        Player player = new Player();
        player.setPseudo(pseudo);
        player.setAge(age);

        playerService.createPlayer(player);
        System.out.println("===========================");
        System.out.println("| Player créé avec succès |");
        System.out.println("===========================");
    }

    private void updatePlayer() {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║          UPDATE PLAYER         ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.print("Entrez l'ID du joueur à modifier => ");
        int id = ValidationUtils.readInt();

        Optional<Player> playerOpt = Optional.ofNullable(playerService.getPlayer(id));
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();

            while (true) {
                System.out.println("Que voulez-vous modifier ?");
                System.out.println("1. Pseudo");
                System.out.println("2. Âge");
                System.out.println("3. Quitter");
                System.out.print("Votre choix => ");
                int choice = ValidationUtils.readInt();

                switch (choice) {
                    case 1:
                        System.out.print("Nouveau pseudo => ");
                        player.setPseudo(ValidationUtils.readValidPseudo());
                        break;
                    case 2:
                        System.out.print("Nouvel âge => ");
                        player.setAge(ValidationUtils.readValidAge());
                        break;
                    case 3:
                        playerService.updatePlayer(player);
                        System.out.println("===========================");
                        System.out.println("| Joueur modifié avec succès |");
                        System.out.println("===========================");
                        return; // Exit the update loop
                    default:
                        System.out.println("Choix invalide, veuillez réessayer.");
                }
            }
        } else {
            System.out.println("*******************");
            System.out.println("Player introuvable :(");
            System.out.println("*******************");
        }
    }

    private void deletePlayer() {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║          DELETE PLAYER         ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.print("Entrez l'ID du joueur à supprimer => ");
        int id = ValidationUtils.readInt();

        if (playerService.deletePlayer(id)) {
            System.out.println("===========================");
            System.out.println("| Joueur supprimé avec succès |");
            System.out.println("===========================");
        } else {
            System.out.println("****************************************");
            System.out.println("Erreur lors de la suppression du joueur :(");
            System.out.println("****************************************");
        }
    }

    private void displayPlayer() {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║           VIEW PLAYER          ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.print("Entrez l'ID du joueur => ");
        int id = ValidationUtils.readInt();

        Optional<Player> playerOpt = Optional.ofNullable(playerService.getPlayer(id));
        playerOpt.ifPresentOrElse(player -> {
            System.out.println("=> Pseudo : " + player.getPseudo());
            System.out.println("=> Âge : " + player.getAge());
        }, () -> System.out.println("***** Player introuvable :( *****"));
    }

    private void displayAllPlayers() {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║        VIEW ALL PLAYERS        ║");
        System.out.println("╚════════════════════════════════╝");
        List<Player> players = playerService.getAllPlayers();
        players.forEach(player -> {
            System.out.println("=============================");
            System.out.println("ID ==>" + player.getId());
            System.out.println("Pseudo => " + player.getPseudo());
            System.out.println("Âge => " + player.getAge());
            System.out.println("=============================");
        });
    }

    private void assignPlayerToTeam() {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║     ASSIGN PLAYER TO TEAM      ║");
        System.out.println("╚════════════════════════════════╝");
        List<Player> players = playerService.getAllPlayers();
        if (players.isEmpty()) {
            System.out.println("*********************************************");
            System.out.println("No players found. Please add a player first :( ");
            System.out.println("*********************************************");
            return;
        }

        System.out.println("\n++++++++++++++++++++++++++++++ Available players ++++++++++++++++++++++++++++++");
        for (Player player : players) {
            System.out.println("| Player ID => " + player.getId() + "| Name => " + player.getPseudo() + "| Age => " + player.getAge() + " |");
        }
        System.out.print("Enter the player ID to assign => ");
        int playerId = ValidationUtils.readInt();
        Player player = playerService.getPlayer(playerId);

        if (player == null) {
            System.out.println("*********************************************");
            System.out.println("Player with ID " + playerId + " not found :(");
            System.out.println("*********************************************");
            return;
        }

        List<Team> teams = teamService.getAllTeams();
        if (teams.isEmpty()) {
            System.out.println("*******************************************");
            System.out.println("No teams found. Please add a team first :(");
            System.out.println("*******************************************");
            return;
        }

        System.out.println("\n++++++++++++++++++++++++++++++ Available Teams ++++++++++++++++++++++++++++++");
        for (Team team : teams) {
            System.out.println("| Team ID => " + team.getId() + "| Name: " + team.getNom() + " |");
        }

        System.out.print("--- Enter the team ID to assign the player to: ---");
        int teamId = ValidationUtils.readInt();
        Team team = teamService.getTeam(teamId);

        if (team == null) {
            System.out.println("*******************************************");
            System.out.println("Team with ID " + teamId + " not found :(");
            System.out.println("*******************************************");
            return;
        }

        player.setTeam(team);
        playerService.updatePlayer(player);
        System.out.println("============================================================================================");
        System.out.println("| Player " + player.getPseudo() + " has been assigned to team " + team.getNom() + " successfully!");
        System.out.println("============================================================================================");
    }
}

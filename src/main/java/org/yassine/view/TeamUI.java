package org.yassine.view;

import org.springframework.context.ApplicationContext;
import org.yassine.model.Player;
import org.yassine.model.Team;
import org.yassine.model.Tournament;
import org.yassine.provider.ApplicationContextProvider;
import org.yassine.service.Interface.IPlayerService;
import org.yassine.service.Interface.ITeamService;
import org.yassine.service.Interface.ITournamentService;
import org.yassine.utils.ValidationUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class TeamUI {
    private static ITeamService teamService;
    private static ITournamentService tournamentService;
    private static IPlayerService playerService;
    private final static Logger logger = LoggerFactory.getLogger(TeamUI.class);

    public void setTeamService(ITeamService teamService) {
        TeamUI.teamService = teamService;
    }

    public void setPlayerService(IPlayerService playerService) {
        TeamUI.playerService = playerService;
    }

    public void setTournamentService(ITournamentService tournamentService) {
        TeamUI.tournamentService = tournamentService;
    }

    public static void showMenu() {
//        ApplicationContext context = ApplicationContextProvider.getContext();
//        teamService = context.getBean(ITeamService.class);
//        tournamentService = context.getBean(ITournamentService.class);
//        playerService = context.getBean(IPlayerService.class);
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║       TEAM MANAGEMENT MENU         ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║ 1. Create a new team              ║");
            System.out.println("║ 2. Update a team                   ║");
            System.out.println("║ 3. View a team by ID               ║");
            System.out.println("║ 4. View all teams                  ║");
            System.out.println("║ 5. Delete a team                   ║");
            System.out.println("║ 6. Assign team to tournament        ║");
            System.out.println("║ 7. Assign player to a team         ║");
            System.out.println("║ 8. Remove player from team         ║");
            System.out.println("║ 9. Remove team from tournament     ║");
            System.out.println("║ 0. EXIT                            ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.print("Enter your choice: ");
            choice = ValidationUtils.readInt();

            switch (choice) {
                case 1 -> addTeam(scanner);
                case 2 -> updateTeam(scanner);
                case 3 -> viewTeamById(scanner);
                case 4 -> viewAllTeams();
                case 5 -> deleteTeam(scanner);
                case 6 -> {
                    System.out.print("--- Enter the team ID to assign: ==> ");
                    int id = ValidationUtils.readInt();
                    assignTournamentToTeam(scanner, teamService.getTeam(id));
                }
                case 7 -> assignPlayerToTeam(scanner);
                case 8 -> removePlayerFromTeam(scanner);
                case 9 -> removeTeamFromTournament(scanner);
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close(); // Close the scanner at the end
    }


    private static void addTeam(Scanner scanner) {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║           ADD NEW TEAM         ║");
        System.out.println("╚════════════════════════════════╝");

        System.out.print("Entre le nom d'equipe => ");
        String name = ValidationUtils.readValidTeamName();

        System.out.print("Entre sa classement => ");
        int ranking = ValidationUtils.readValidRanking();

        Team newTeam = new Team();
        newTeam.setNom(name);
        newTeam.setClassement(ranking);

        System.out.print("Assigner une tournoi à cette equipe ? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            assignTournamentToTeam(scanner, newTeam);
        }

        teamService.createTeam(newTeam);
        System.out.println("===========================");
        System.out.println("| Team added successfully |");
        System.out.println("===========================");
    }

    private static void updateTeam(Scanner scanner) {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║          UPDATE TEAM           ║");
        System.out.println("╚════════════════════════════════╝");

        System.out.print("Enter the team ID to update => ");
        int id = ValidationUtils.readInt();

        Team existingTeam = teamService.getTeam(id);
        if (existingTeam == null) {
            System.out.println("***********************");
            System.out.println("Team with ID " + id + " not found :(");
            System.out.println("***********************");
            return;
        }

        while (true) {
            System.out.println("\nWhat would you like to update?");
            System.out.println("╔════════════════════════════════════════╗");
            System.out.printf("║ %s : %-35s ║%n", "1", "Name (Current: " + existingTeam.getNom() + ")");
            System.out.printf("║ %s : %-35s ║%n", "2", "Tournament Assignment");
            System.out.printf("║ %s : %-35s ║%n", "3", "Save Changes and Exit");
            System.out.println("╚════════════════════════════════════════╝");

            System.out.print("Your choice => ");
            int choice = ValidationUtils.readInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter new team name (leave blank to keep current): ");
                    String newName = scanner.nextLine();
                    if (!newName.trim().isEmpty()) {
                        existingTeam.setNom(newName);
                    }
                    break;
                case 2:
                    System.out.print("Update tournament assignment? (y/n): ");
                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                        assignTournamentToTeam(scanner, existingTeam);
                    }
                    break;
                case 3:
                    teamService.updateTeam(existingTeam);
                    System.out.println("===========================");
                    System.out.println("| Team updated successfully |");
                    System.out.println("===========================");
                    return; // Exit the update loop
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void viewTeamById(Scanner scanner) {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║            VIEW TEAM           ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.print("Enter the team ID => ");
        int id = ValidationUtils.readInt();

        Team team = teamService.getTeam(id);
        if (team != null) {
            System.out.println("ID => " + team.getId());
            System.out.println("Name => " + team.getNom());

            List<Player> players = team.getPlayers();
            if (players != null && !players.isEmpty()) {
                System.out.println("Players List => ");
                for (Player player : players) {
                    System.out.println("===========================");
                    System.out.println(" - Pseudo => " + player.getPseudo());
                }
            } else {
                System.out.println("No players assigned.");
            }

            if (team.getTournament() != null) {
                System.out.println("Tournament => " + team.getTournament().getTitre());
            } else {
                System.out.println("**********************");
                System.out.println("No tournament assigned.");
                System.out.println("**********************");
            }

            System.out.println("Ranking => " + (team.getClassement() != null ? team.getClassement() : "Not ranked"));
        } else {
            System.out.println("**************************");
            System.out.println("Team with ID " + id + " not found :(");
            System.out.println("**************************");
        }
    }

    private static void deleteTeam(Scanner scanner) {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║           DELETE TEAM          ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.print("Enter the team ID to delete => ");
        int id = ValidationUtils.readInt();

        Team team = teamService.getTeam(id);
        if (team != null) {
            teamService.deleteTeam(id);
            System.out.println("===========================");
            System.out.println("| Team deleted successfully |");
            System.out.println("===========================");
        } else {
            System.out.println("*************************");
            System.out.println("Team with ID " + id + " not found :(");
            System.out.println("*************************");
        }
    }

    private static void viewAllTeams() {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║         VIEW ALL TEAMS         ║");
        System.out.println("╚════════════════════════════════╝");

        List<Team> teams = teamService.getAllTeams();
        if (teams.isEmpty()) {
            System.out.println("===========================");
            System.out.println("No teams found :(");
            System.out.println("===========================");
        } else {
            for (Team team : teams) {
                System.out.println("===========================");
                System.out.println("Team ID => " + team.getId() + "\n Name => " + team.getNom());
                System.out.println("===========================");
            }
        }
    }

    private static void assignTournamentToTeam(Scanner scanner, Team team) {
        if (team == null) {
            System.out.println("******************************");
            System.out.println("Error: The team object is null.");
            System.out.println("******************************");
            return;
        }

        List<Tournament> tournaments = tournamentService.getAllTournaments();
        if (tournaments.isEmpty()) {
            System.out.println("****************************************");
            System.out.println("No tournaments found. Please add a tournament first.");
            System.out.println("****************************************");
            return;
        }

        System.out.println("\n+++++++++++++ Available Tournaments +++++++++++++++");
        for (Tournament tournament : tournaments) {
            System.out.println("===========================");
            System.out.printf("Tournament ID => %d%nName => %s%n", tournament.getId(), tournament.getTitre());
            System.out.println("===========================");
        }

        System.out.print("Enter the tournament ID to assign => ");
        int tournamentId = ValidationUtils.readInt();
        Tournament selectedTournament = tournamentService.getTournament(tournamentId);

        if (selectedTournament != null) {
            team.setTournament(selectedTournament);
            System.out.println("=============================================");
            System.out.printf("Success! %s has been assigned to %s.%n", team.getNom(), selectedTournament.getTitre());
            System.out.println("=============================================");
            teamService.updateTeam(team);
        } else {
            System.out.println("********************************************");
            System.out.println("Error: Invalid tournament ID. Assignment failed.");
            System.out.println("********************************************");
        }
    }

    private static void assignPlayerToTeam(Scanner scanner) {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║     ASSIGN PLAYER TO TEAM      ║");
        System.out.println("╚════════════════════════════════╝");

        List<Player> players = playerService.getAllPlayers();
        if (players.isEmpty()) {
            System.out.println("*****************************");
            System.out.println("No players found. Please add players first.");
            System.out.println("*****************************");
            return;
        }

        System.out.println("\nAvailable Players:");
        for (Player player : players) {
            System.out.println("===========================");
            System.out.printf("Player ID => %d%nName => %s%n", player.getId(), player.getPseudo());
            System.out.println("===========================");
        }

        System.out.print("Enter the player ID to assign => ");
        int playerId = ValidationUtils.readInt();
        Player player = playerService.getPlayer(playerId);

        if (player == null) {
            System.out.println("*****************************");
            System.out.println("Player with ID " + playerId + " not found :(");
            System.out.println("*****************************");
            return;
        }

        List<Team> teams = teamService.getAllTeams();
        if (teams.isEmpty()) {
            System.out.println("******************************");
            System.out.println("No teams found. Please add a team first.");
            System.out.println("******************************");
            return;
        }

        System.out.println("\n++++++++++++ Available Teams ++++++++++++");
        for (Team team : teams) {
            System.out.println("===========================");
            System.out.printf("Team ID => %d%nName => %s%n", team.getId(), team.getNom());
            System.out.println("===========================");
        }

        System.out.print("Enter the team ID to assign the player to => ");
        int teamId = ValidationUtils.readInt();
        Team team = teamService.getTeam(teamId);

        if (team == null) {
            System.out.println("*************************");
            System.out.println("Team with ID " + teamId + " not found.");
            System.out.println("*************************");
            return;
        }

        player.setTeam(team);
        playerService.updatePlayer(player);
        System.out.println("=============================================");
        System.out.printf("Player %s has been assigned to team %s successfully!%n", player.getPseudo(), team.getNom());
        System.out.println("=============================================");
    }

    private static void removePlayerFromTeam(Scanner scanner) {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║    REMOVE PLAYER FROM TEAM     ║");
        System.out.println("╚════════════════════════════════╝");

        List<Player> players = playerService.getAllPlayers();
        if (players.isEmpty()) {
            System.out.println("*****************************");
            System.out.println("No players found. Please add players first.");
            System.out.println("*****************************");
            return;
        }

        System.out.println("\nAvailable Players:");
        for (Player player : players) {
            System.out.println("===========================");
            System.out.printf("ID => %d, Name => %s%n", player.getId(), player.getPseudo());
            System.out.println("===========================");
        }

        System.out.print("Enter the player ID to remove => ");
        int playerId = ValidationUtils.readInt();
        Player player = playerService.getPlayer(playerId);

        if (player == null) {
            System.out.println("*****************************");
            System.out.println("Player with ID " + playerId + " not found :(");
            System.out.println("*****************************");
            return;
        }

        if (player.getTeam() == null) {
            System.out.println("The player " + player.getPseudo() + " is not assigned to any team.");
            return;
        }

        player.setTeam(null);
        playerService.updatePlayer(player);
        System.out.println("=============================================");
        System.out.printf("Player %s has been successfully removed from their team!%n", player.getPseudo());
        System.out.println("=============================================");
    }

    private static void removeTeamFromTournament(Scanner scanner) {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║  REMOVE TEAM FROM TOURNAMENT   ║");
        System.out.println("╚════════════════════════════════╝");
        List<Tournament> tournaments = tournamentService.getAllTournaments();
        if (tournaments.isEmpty()) {
            System.out.println("*****************************");
            System.out.println("No tournaments found. Please add tournaments first.");
            System.out.println("*****************************");
            return;
        }

        System.out.println("\nAvailable Tournaments:");
        for (Tournament tournament : tournaments) {
            System.out.println("===========================");
            System.out.printf("Tournament ID => %d%nTitle => %s%n", tournament.getId(), tournament.getTitre());
            System.out.println("===========================");
        }

        System.out.print("Enter the tournament ID to remove a team from => ");
        int tournamentId = ValidationUtils.readInt();
        Tournament tournament = tournamentService.getTournament(tournamentId);

        if (tournament == null) {
            System.out.println("*****************************");
            System.out.println("Tournament with ID " + tournamentId + " not found.");
            System.out.println("*****************************");
            return;
        }

        List<Team> teams = tournament.getTeams();
        if (teams.isEmpty()) {
            System.out.println("No teams are currently associated with the tournament " + tournament.getTitre());
            return;
        }

        System.out.println("\nAvailable Teams:");
        for (Team team : teams) {
            System.out.println("===========================");
            System.out.printf("Team ID => %d%nName => %s%n", team.getId(), team.getNom());
            System.out.println("===========================");
        }

        System.out.print("Enter the team ID to remove from the tournament => ");
        int teamId = ValidationUtils.readInt();
        Team team = teamService.getTeam(teamId);

        if (team == null) {
            System.out.println("*****************************");
            System.out.println("Team with ID " + teamId + " not found.");
            System.out.println("*****************************");
            return;
        }

        team.setTournament(null);
        teamService.updateTeam(team);
        System.out.println("=============================================");
        System.out.printf("Team %s has been successfully removed from the tournament %s!%n", team.getNom(), tournament.getTitre());
        System.out.println("=============================================");
    }

}

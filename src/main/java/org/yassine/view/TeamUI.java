package org.yassine.view;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yassine.model.Game;
import org.yassine.model.Player;
import org.yassine.model.Team;
import org.yassine.model.Tournament;
import org.yassine.service.Interface.IGameService;
import org.yassine.service.Interface.IPlayerService;
import org.yassine.service.Interface.ITeamService;
import org.yassine.service.Interface.ITournamentService;

import java.util.List;
import java.util.Scanner;

public class TeamUI {
    private static ITeamService teamService;
    private static ITournamentService tournamentService;
    private static IPlayerService playerService;

    public void setTeamService(ITeamService teamService) {
        this.teamService = teamService;
    }

    public void setPlayerService(IPlayerService playerService) {
        this.playerService = playerService;
    }

    public void setTournamentService(ITournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    public static void showMenu() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        teamService = (ITeamService) context.getBean("teamService");
        tournamentService = (ITournamentService) context.getBean("tournamentService");
        playerService = (IPlayerService) context.getBean("playerService");

        Scanner scanner = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\n=== Team Management Menu ===");
            System.out.println("1. Add a new team");
            System.out.println("2. Update an existing team");
            System.out.println("3. View a team by ID");
            System.out.println("4. View all teams");
            System.out.println("5. Delete a team");
            System.out.println("6. assign a Team to Tournament ");
            System.out.println("7. assign a Player to a Team ");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addTeam(scanner);
                    break;
                case 2:
                    updateTeam(scanner);
                    break;
                case 3:
                    viewTeamById(scanner);
                    break;
                case 4:
                    viewAllTeams();
                    break;
                case 5:
                    deleteTeam(scanner);
                    break;
                case 6:
                    System.out.print("Enter the team ID to assigne : ");
                    int id = scanner.nextInt();
                    assignTournamentToTeam(scanner, teamService.getTeam(id));
                    break;
                case 7:  // Assign player to team
                    assignPlayerToTeam(scanner);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }while(choice != 0);

        scanner.close();
    }

    private static void addTeam(Scanner scanner) {
        System.out.println("\n=== Add New Team ===");

        System.out.print("Enter team name: ");
        scanner.nextLine();
        String name = scanner.nextLine();


        System.out.print("Enter the Rank: ");
        int classement = scanner.nextInt();
        scanner.nextLine();

        Team newTeam = new Team();
        newTeam.setNom(name);
        newTeam.setClassement(classement);
        System.out.print("Assign a tournament to this team? (y/n): ");
        String assignTournament = scanner.nextLine();
        if (assignTournament.equalsIgnoreCase("y")) {
            assignTournamentToTeam(scanner, newTeam);
        }
        teamService.createTeam(newTeam);
        System.out.println("Team added successfully!");
    }

    private static void updateTeam(Scanner scanner) {
        System.out.println("\n=== Update Team ===");

        System.out.print("Enter the team ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Team existingTeam = teamService.getTeam(id);
        if (existingTeam == null) {
            System.out.println("Team with ID " + id + " not found.");
            return;
        }

        System.out.print("Enter new team name (leave blank to keep current): ");
        String newName = scanner.nextLine();
        if (!newName.trim().isEmpty()) {
            existingTeam.setNom(newName);
        }

        // Optional: update tournament assignment if needed
        System.out.print("Update tournament assignment? (y/n): ");
        String updateTournament = scanner.nextLine();
        if (updateTournament.equalsIgnoreCase("y")) {
            assignTournamentToTeam(scanner, existingTeam);
        }

        teamService.updateTeam(existingTeam);
        System.out.println("Team updated successfully!");
    }
    private static void viewTeamById(Scanner scanner) {
        System.out.println("\n=== View Team by ID ===");

        System.out.print("Enter the team ID: ");
        int id = scanner.nextInt();

        Team team = teamService.getTeam(id);
        if (team != null) {
            System.out.println("Team ID: " + team.getId());
            System.out.println("Name: " + team.getNom());

            // Display players
            List<Player> players = team.getPlayers();
            if (players != null && !players.isEmpty()) {
                System.out.println("Players:");
                for (Player player : players) {
                    System.out.println(" - " + player.getPseudo());
                }
            } else {
                System.out.println("No players assigned.");
            }

            if (team.getTournament() != null) {
                System.out.println("Tournament: " + team.getTournament().getTitre());
            } else {
                System.out.println("No tournament assigned.");
            }

            System.out.println("Classement: " + (team.getClassement() != null ? team.getClassement() : "Not ranked"));
        } else {
            System.out.println("Team with ID " + id + " not found.");
        }
    }
    private static void deleteTeam(Scanner scanner) {
        System.out.println("\n=== Delete Team ===");

        System.out.print("Enter the team ID to delete: ");
        int id = scanner.nextInt();

        Team team = teamService.getTeam(id);
        if (team != null) {
            teamService.deleteTeam(id);
            System.out.println("Team deleted successfully!");
        } else {
            System.out.println("Team with ID " + id + " not found.");
        }
    }
    private static void viewAllTeams() {
        System.out.println("\n=== View All Teams ===");

        List<Team> teams = teamService.getAllTeams();
        if (teams.isEmpty()) {
            System.out.println("No teams found.");
        } else {
            for (Team team : teams) {
                System.out.println("Team ID: " + team.getId() + ", Name: " + team.getNom());
            }
        }
    }
    private static void assignTournamentToTeam(Scanner scanner, Team team) {
        List<Tournament> tournaments = tournamentService.getAllTournaments();
        if (tournaments.isEmpty()) {
            System.out.println("No tournaments found. Please add a tournaments first.");
            return;
        }

        System.out.println("\nAvailable tournaments:");
        for (Tournament tournament : tournaments) {
            System.out.println("Team ID: " + tournament.getId() + ", Name: " + tournament.getTitre());
        }
        System.out.print("Enter the tournament ID to assign: ");
        int tournamentId = scanner.nextInt();
        scanner.nextLine();

        team.setTournament(tournamentService.getTournament(tournamentId));
        if (team.getTournament() != null) {
            System.out.println("Tournament assigned: " + team.getTournament().getTitre());
        } else {
            System.out.println("Invalid tournament ID.");
        }
    }
    private static void assignPlayerToTeam(Scanner scanner) {
        System.out.println("\n=== Assign Player to Team ===");

        List<Player> players = playerService.getAllPlayers();
        if (players.isEmpty()) {
            System.out.println("No players found. Please add a players first.");
            return;
        }

        System.out.println("\nAvailable players:");
        for (Player player : players) {
            System.out.println("Team ID: " + player.getId() + ", Name: " + player.getPseudo());
        }
        System.out.print("Enter the player ID to assign: ");
        int playerId = scanner.nextInt();
        Player player = playerService.getPlayer(playerId);

        if (player == null) {
            System.out.println("Player with ID " + playerId + " not found.");
            return;
        }

        List<Team> teams = teamService.getAllTeams();  // Assuming this method exists in your TeamService
        if (teams.isEmpty()) {
            System.out.println("No teams found. Please add a team first.");
            return;
        }

        System.out.println("\nAvailable Teams:");
        for (Team team : teams) {
            System.out.println("Team ID: " + team.getId() + ", Name: " + team.getNom());
        }


        System.out.print("Enter the team ID to assign the player to: ");
        int teamId = scanner.nextInt();
        Team team = teamService.getTeam(teamId);

        if (team == null) {
            System.out.println("Team with ID " + teamId + " not found.");
            return;
        }

        player.setTeam(team);
        playerService.updatePlayer(player);
        System.out.println("Player " + player.getPseudo() + " has been assigned to team " + team.getNom() + " successfully!");
    }

}


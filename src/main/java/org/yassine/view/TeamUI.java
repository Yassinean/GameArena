package org.yassine.view;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yassine.model.Player;
import org.yassine.model.Team;
import org.yassine.model.Tournament;
import org.yassine.service.Interface.IPlayerService;
import org.yassine.service.Interface.ITeamService;
import org.yassine.service.Interface.ITournamentService;

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
            logger.info("\n=== Team Management Menu ===");
            logger.info("1. Add a new team");
            logger.info("2. Update an existing team");
            logger.info("3. View a team by ID");
            logger.info("4. View all teams");
            logger.info("5. Delete a team");
            logger.info("6. assign a Team to Tournament ");
            logger.info("7. assign a Player to a Team ");
            logger.info("0. Exit");
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
                    logger.info("Exiting...");
                    break;
                default:
                    logger.info("Invalid choice. Please try again.");
            }
        }while(choice != 0);

        scanner.close();
    }

    private static void addTeam(Scanner scanner) {
        logger.info("\n=== Add New Team ===");

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
        logger.info("Team added successfully!");
    }

    private static void updateTeam(Scanner scanner) {
        logger.info("\n=== Update Team ===");

        System.out.print("Enter the team ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Team existingTeam = teamService.getTeam(id);
        if (existingTeam == null) {
            logger.info("Team with ID " + id + " not found.");
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
        logger.info("Team updated successfully!");
    }
    private static void viewTeamById(Scanner scanner) {
        logger.info("\n=== View Team by ID ===");

        System.out.print("Enter the team ID: ");
        int id = scanner.nextInt();

        Team team = teamService.getTeam(id);
        if (team != null) {
            logger.info("Team ID: " + team.getId());
            logger.info("Name: " + team.getNom());

            // Display players
            List<Player> players = team.getPlayers();
            if (players != null && !players.isEmpty()) {
                logger.info("Players:");
                for (Player player : players) {
                    logger.info(" - " + player.getPseudo());
                }
            } else {
                logger.info("No players assigned.");
            }

            if (team.getTournament() != null) {
                logger.info("Tournament: " + team.getTournament().getTitre());
            } else {
                logger.info("No tournament assigned.");
            }

            logger.info("Classement: " + (team.getClassement() != null ? team.getClassement() : "Not ranked"));
        } else {
            logger.info("Team with ID " + id + " not found.");
        }
    }
    private static void deleteTeam(Scanner scanner) {
        logger.info("\n=== Delete Team ===");

        System.out.print("Enter the team ID to delete: ");
        int id = scanner.nextInt();

        Team team = teamService.getTeam(id);
        if (team != null) {
            teamService.deleteTeam(id);
            logger.info("Team deleted successfully!");
        } else {
            logger.info("Team with ID " + id + " not found.");
        }
    }
    private static void viewAllTeams() {
        logger.info("\n=== View All Teams ===");

        List<Team> teams = teamService.getAllTeams();
        if (teams.isEmpty()) {
            logger.info("No teams found.");
        } else {
            for (Team team : teams) {
                logger.info("Team ID: " + team.getId() + ", Name: " + team.getNom());
            }
        }
    }
    private static void assignTournamentToTeam(Scanner scanner, Team team) {
        List<Tournament> tournaments = tournamentService.getAllTournaments();
        if (tournaments.isEmpty()) {
            logger.info("No tournaments found. Please add a tournaments first.");
            return;
        }

        logger.info("\nAvailable tournaments:");
        for (Tournament tournament : tournaments) {
            logger.info("Team ID: " + tournament.getId() + ", Name: " + tournament.getTitre());
        }
        System.out.print("Enter the tournament ID to assign: ");
        int tournamentId = scanner.nextInt();
        scanner.nextLine();

        team.setTournament(tournamentService.getTournament(tournamentId));
        if (team.getTournament() != null) {
            logger.info("Tournament assigned: " + team.getTournament().getTitre());
        } else {
            logger.info("Invalid tournament ID.");
        }
    }
    private static void assignPlayerToTeam(Scanner scanner) {
        logger.info("\n=== Assign Player to Team ===");

        List<Player> players = playerService.getAllPlayers();
        if (players.isEmpty()) {
            logger.info("No players found. Please add a players first.");
            return;
        }

        logger.info("\nAvailable players:");
        for (Player player : players) {
            logger.info("Team ID: " + player.getId() + ", Name: " + player.getPseudo());
        }
        System.out.print("Enter the player ID to assign: ");
        int playerId = scanner.nextInt();
        Player player = playerService.getPlayer(playerId);

        if (player == null) {
            logger.info("Player with ID " + playerId + " not found.");
            return;
        }

        List<Team> teams = teamService.getAllTeams();  // Assuming this method exists in your TeamService
        if (teams.isEmpty()) {
            logger.info("No teams found. Please add a team first.");
            return;
        }

        logger.info("\nAvailable Teams:");
        for (Team team : teams) {
            logger.info("Team ID: " + team.getId() + ", Name: " + team.getNom());
        }


        System.out.print("Enter the team ID to assign the player to: ");
        int teamId = scanner.nextInt();
        Team team = teamService.getTeam(teamId);

        if (team == null) {
            logger.info("Team with ID " + teamId + " not found.");
            return;
        }

        player.setTeam(team);
        playerService.updatePlayer(player);
        logger.info("Player " + player.getPseudo() + " has been assigned to team " + team.getNom() + " successfully!");
    }

}


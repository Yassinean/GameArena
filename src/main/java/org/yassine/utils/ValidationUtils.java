package org.yassine.utils;

import org.yassine.model.Tournament;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ValidationUtils {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Lit une chaîne de caractères depuis l'entrée utilisateur.
     *
     * @return La chaîne de caractères entrée par l'utilisateur.
     */
    public static String readString() {
        String input = scanner.nextLine();
        while (input == null || input.trim().isEmpty()) {
            System.out.println("Erreur : l'entrée ne doit pas être vide. Veuillez entrer une valeur.");
            input = scanner.nextLine();
        }
        return input;
    }

    /**
     * Lit un entier depuis l'entrée utilisateur. Répète la demande jusqu'à ce qu'une valeur entière valide soit entrée.
     *
     * @return L'entier entré par l'utilisateur.
     */
    public static int readInt() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value <= 0) {
                    System.out.println("Veuillez entrer un nombre entier supérieur à zéro.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre entier valide.");
            }
        }
    }

    /**
     * Vérifie si un pseudo est valide (non vide et contient uniquement des lettres et des espaces).
     *
     * @param pseudo Le pseudo à vérifier.
     * @return True si le pseudo est valide, false sinon.
     */
    private static boolean isValidPseudo(String pseudo) {
        return pseudo != null && !pseudo.trim().isEmpty() && pseudo.matches("[a-zA-Z\\s]+");
    }

    public static String readValidPseudo() {
        String pseudo = readString();
        while (!isValidPseudo(pseudo)) {
            System.out.println("Erreur : le pseudo ne doit contenir que des lettres. Veuillez réessayer.");
            pseudo = readString();
        }
        return pseudo;
    }

    /**
     * Vérifie si un âge est valide (un entier positif).
     *
     * @param age L'âge à vérifier.
     * @return True si l'âge est valide, false sinon.
     */
    private static boolean isValidAge(int age) {
        return age > 0 && age < 120; // Example range for age
    }

    public static int readValidAge() {
        int age;
        while (true) {
            age = readInt();
            if (isValidAge(age)) {
                return age;
            }
            System.out.println("Erreur : l'âge doit être un entier positif et réaliste (ex: 0-120). Veuillez réessayer.");
        }
    }

    // Game Validation
    public static String readValidGameName() {
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input;
            }
            System.out.println("Erreur : le nom du jeu ne doit pas être vide. Veuillez entrer une valeur.");
        }
    }

    public static String readValidDifficulty() {
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("Easy") || input.equalsIgnoreCase("Medium") || input.equalsIgnoreCase("Hard")) {
                return input;
            }
            System.out.println("Erreur : la difficulté doit être 'Easy', 'Medium' ou 'Hard'. Veuillez réessayer.");
        }
    }

    public static double readValidDuration() {
        double duration;
        while (true) {
            try {
                duration = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                if (duration > 0) {
                    return duration;
                }
                System.out.println("Erreur : la durée moyenne doit être supérieure à zéro.");
            } catch (InputMismatchException e) {
                System.out.println("Erreur : veuillez entrer un nombre valide.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    // Team validation
    public static String readValidTeamName() {
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input;
            }
            System.out.println("Erreur : le nom de l'équipe ne doit pas être vide. Veuillez entrer une valeur.");
        }
    }

    public static int readValidRanking() {
        int ranking;
        while (true) {
            try {
                ranking = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (ranking >= 0) {
                    return ranking;
                }
                System.out.println("Erreur : le classement doit être un nombre positif.");
            } catch (InputMismatchException e) {
                System.out.println("Erreur : veuillez entrer un nombre valide.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    // tournament validation
    public static String readValidTournamentTitle() {
        String title;
        while (true) {
            title = scanner.nextLine();
            if (title != null && !title.trim().isEmpty()) {
                return title;
            }
            System.out.println("Erreur : le titre du tournoi ne doit pas être vide. Veuillez entrer une valeur.");
        }
    }

    public static Tournament.Statut readValidTournamentStatus() {
        Tournament.Statut status = null;
        while (status == null) {
            System.out.print("Entrez le statut du tournoi (PLANIFIE/EN_COURS/TERMINE/ANNULE): ");
            String statusInput = scanner.nextLine();
            try {
                status = Tournament.Statut.valueOf(statusInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Statut invalide. Veuillez entrer un statut valide.");
            }
        }
        return status;
    }

    public static LocalDate readValidDate(String prompt) {
        LocalDate date;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                date = LocalDate.parse(input);
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Erreur : Format de date invalide. Utilisez le format YYYY-MM-DD.");
            }
        }

    }
}
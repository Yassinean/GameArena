package View;

import DAO.Interface.IJoueurDao;
import Model.Joueur;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class JoueurUI {

    private static final Scanner scanner = new Scanner(System.in);
    private IJoueurDao joueurDao; // Dependency Injection for JoueurDao

    // Setter for Dependency Injection
    public void setJoueurDao(IJoueurDao joueurDao) {
        this.joueurDao = joueurDao;
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n=== Menu de Gestion des Joueurs ===");
            System.out.println("1. Créer un joueur");
            System.out.println("2. Mettre à jour un joueur");
            System.out.println("3. Supprimer un joueur");
            System.out.println("4. Rechercher un joueur");
            System.out.println("5. Afficher tous les joueurs");
            System.out.println("6. Quitter");
            System.out.print("Veuillez choisir une option : ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    createJoueur();
                    break;
                case 2:
                    updateJoueur();
                    break;
                case 3:
                    deleteJoueur();
                    break;
                case 4:
                    findJoueur();
                    break;
                case 5:
                    listAllJoueurs();
                    break;
                case 6:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        } while (choice != 6);
    }

    private void createJoueur() {
        System.out.println("\n=== Créer un Joueur ===");
        System.out.print("Entrez le pseudo du joueur : ");
        String pseudo = scanner.nextLine();
        System.out.print("Entrez l'âge du joueur : ");
        int age = Integer.parseInt(scanner.nextLine());

        Joueur joueur = new Joueur();
        joueur.setPseudo(pseudo);
        joueur.setAge(age);

        joueurDao.createJoueur(joueur);
        System.out.println("Joueur créé avec succès !");
    }
    private void updateJoueur() {
        System.out.println("\n=== Mettre à Jour un Joueur ===");
        System.out.print("Entrez l'ID du joueur à mettre à jour : ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Joueur> optionalJoueur = joueurDao.findJoueur(id);
        if (optionalJoueur.isPresent()) {
            Joueur joueur = optionalJoueur.get();

            System.out.print("Nouveau pseudo (" + joueur.getPseudo() + ") : ");
            String pseudo = scanner.nextLine();
            if (!pseudo.isEmpty()) joueur.setPseudo(pseudo);

            System.out.print("Nouvel âge (" + joueur.getAge() + ") : ");
            int age = Integer.parseInt(scanner.nextLine());
            joueur.setAge(age);

            joueurDao.updateJoueur(joueur);
            System.out.println("Joueur mis à jour avec succès !");
        } else {
            System.out.println("Joueur non trouvé !");
        }
    }

    private void deleteJoueur() {
        System.out.println("\n=== Supprimer un Joueur ===");
        System.out.print("Entrez l'ID du joueur à supprimer : ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Joueur> joueur = joueurDao.findJoueur(id);
        if (joueur.isPresent()) {
            joueurDao.deleteJoueur(id);
            System.out.println("Joueur supprimé avec succès !");
        } else {
            System.out.println("Joueur non trouvé !");
        }
    }

    private void findJoueur() {
        System.out.println("\n=== Rechercher un Joueur ===");
        System.out.print("Entrez l'ID du joueur à rechercher : ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Joueur> joueur = joueurDao.findJoueur(id);
        if (joueur.isPresent()) {
            System.out.println("Joueur trouvé : " + joueur.get());
        } else {
            System.out.println("Joueur non trouvé !");
        }
    }

    private void listAllJoueurs() {
        System.out.println("\n=== Liste de Tous les Joueurs ===");
        List<Joueur> joueurs = joueurDao.getAllJoueur();
        if (joueurs.isEmpty()) {
            System.out.println("Aucun joueur trouvé.");
        } else {
            joueurs.forEach(System.out::println);
        }
    }


    public static void main(String[] args) {
        JoueurUI joueurUI = new JoueurUI();
        joueurUI.showMenu();
    }

}

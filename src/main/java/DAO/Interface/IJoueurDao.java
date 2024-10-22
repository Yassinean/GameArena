package DAO.Interface;

import Model.Joueur;

import java.util.List;
import java.util.Optional;

public interface IJoueurDao {
    Joueur createJoueur(Joueur joueur);
    void updateJoueur(Joueur joueur);
    void deleteJoueur(int id);
    Optional<Joueur> findJoueur(int id);
    List<Joueur>getAllJoueur();
}

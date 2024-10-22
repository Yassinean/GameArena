package Service.implementation;

import DAO.implementation.JoueurImpl;
import Model.Joueur;
import Service.Interface.IJoueurService;

import java.util.List;
import java.util.Optional;

public class JoueurServiceImpl implements IJoueurService {
    private JoueurImpl joueurDao;

    public JoueurServiceImpl(JoueurImpl joueurDao){
        this.joueurDao = joueurDao;
    }

    @Override
    public Joueur createJoueur(Joueur joueur) {
        return joueurDao.createJoueur(joueur);
    }

    @Override
    public void updateJoueur(Joueur joueur) {
        joueurDao.updateJoueur(joueur);
    }

    @Override
    public void deleteJoueur(int id) {
        joueurDao.deleteJoueur(id);
    }

    @Override
    public Optional<Joueur> findJoueur(int id) {
        return joueurDao.findJoueur(id);
    }

    @Override
    public List<Joueur> getAllJoueur() {
        return joueurDao.getAllJoueur();
    }
}

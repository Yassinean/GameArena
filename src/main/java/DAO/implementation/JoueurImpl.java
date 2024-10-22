package DAO.implementation;

import DAO.Interface.IJoueurDao;
import Model.Joueur;
import Utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JoueurImpl implements IJoueurDao {
    private static final Logger LOGGER = Logger.getLogger(JoueurImpl.class.getName());
    EntityManager em = JPAUtil.getInstance().getEntityManager();
    EntityTransaction et = em.getTransaction();
    
    @Override
    public Joueur createJoueur(Joueur joueur) {
        try{
            et.begin();
            em.persist(joueur);
            et.commit();
        }catch (Exception e) {
            if(et.isActive()) {
                et.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error saving Joueur", e);
            return null;
        }finally {
            em.close();
        }
        return joueur;
    }

    @Override
    public void updateJoueur(Joueur joueur) {
        EntityManager em = JPAUtil.getInstance().getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            Joueur existJoueur = em.find(Joueur.class, joueur.getId());
            if(existJoueur != null) {
                existJoueur.setPseudo(joueur.getPseudo());
                existJoueur.setAge(joueur.getAge());
                existJoueur.setEquipe(joueur.getEquipe());
                em.merge(existJoueur);
            }
            et.commit();

        }catch (Exception e) {
            if(et.isActive()) {
                et.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error updating joueur", e);
        }finally {
            em.close();
        }
    }

    @Override
    public void deleteJoueur(int id) {
        EntityManager em = JPAUtil.getInstance().getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            Joueur joueur = em.find(Joueur.class,id);
            if (joueur != null ) {
                em.remove(joueur);
            }
            et.commit();
        } catch (Exception e) {
            if(et.isActive()) {
                et.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting joueur", e);
        }finally {
            em.close();
        }
    }

    @Override
    public Optional<Joueur> findJoueur(int id) {
        EntityManager em = JPAUtil.getInstance().getEntityManager();
        Joueur joueur = null;
        try {
            joueur = em.find(Joueur.class, id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding joueur", e);
        }
        finally {
            em.close();
        }
        return Optional.ofNullable(joueur);
    }

    @Override
    public List<Joueur> getAllJoueur() {
        EntityManager em = JPAUtil.getInstance().getEntityManager();
        List<Joueur> joueurs = null;
        try {
            TypedQuery<Joueur> query = em.createQuery("SELECT p FROM Joueurs p ORDER BY p.id DESC", Joueur.class);
            joueurs = query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error listing joueurs", e);
        } finally {
            em.close();
        }
        return joueurs;
    }
}

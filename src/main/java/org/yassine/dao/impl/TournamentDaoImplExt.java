package org.yassine.dao.impl;

import org.yassine.dao.Interface.ITournamentDao;
import org.yassine.model.Game;
import org.yassine.model.Team;
import org.yassine.model.Tournament;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class TournamentDaoImplExt implements ITournamentDao {
    private EntityManagerFactory entityManagerFactory;

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public boolean createTournament(Tournament tournament) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(tournament);
            em.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean updateTournament(Tournament tournament) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(tournament);
            em.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteTournament(int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Tournament tour = em.find(Tournament.class, id);
            if (tour != null) {
                em.remove(tour);
            }
            em.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Tournament getTournament(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tournament.class, id);
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
        }
        return null;
    }

    @Override
    public List<Tournament> getAllTournaments() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("FROM Tournament", Tournament.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<Team> findTeamsByTournoiId(int tournoiId) {
        EntityManager em = getEntityManager();
        try {
            // Use a typed query to fetch the teams associated with the tournament ID
            TypedQuery<Team> query = em.createQuery("SELECT t FROM Team t WHERE t.tournament.id = :tournoiId", Team.class);
            query.setParameter("tournoiId", tournoiId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Game findGameByTournoiId(int tournoiId) {
        EntityManager em = getEntityManager();
        try {
            Tournament tournament = em.find(Tournament.class, tournoiId);
            if (tournament != null) {
                return tournament.getGame();
            } else {
                throw new IllegalArgumentException("Tournament not found");
            }
        } finally {
            em.close();
        }
    }

    @Override
    public double calculerdureeEstimeeTournoi(int id) {
        Tournament tournoi = this.getTournament(id);
        if (tournoi == null) {
            throw new IllegalArgumentException("Tournament not found");
        }
        List<Team> teamsParticipating = this.findTeamsByTournoiId(id);
        int nbEquipes = teamsParticipating.size();
        int nbMatchs = nbEquipes - 1;
        Game game = this.findGameByTournoiId(id);
        double durre_estimate = (nbMatchs * game.getDureeMoyenneMatch()) + ((nbMatchs - 1) * tournoi.getTempsPauseEntreMatchs()) + (tournoi.getTempsCeremonie());
        tournoi.setDureeEstimee(durre_estimate);
        this.updateTournament(tournoi);
        return (nbMatchs * game.getDureeMoyenneMatch()) + ((nbMatchs - 1) * tournoi.getTempsPauseEntreMatchs()) + (tournoi.getTempsCeremonie());

    }
}

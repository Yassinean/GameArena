package org.yassine.dao.impl;

import org.yassine.dao.Interface.ITournamentDao;
import org.yassine.model.Tournament;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class TournamentDaoImpl implements ITournamentDao {
    private EntityManagerFactory entityManagerFactory;

    // Setter for dependency injection
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
        }catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
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
        }catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    @Override
    public boolean deleteTournament(Tournament tournament) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Tournament tour = em.find(Tournament.class, tournament.getId());
            if (tour != null) {
                em.remove(tour);
            }
            em.getTransaction().commit();
            return true;
        }catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    @Override
    public Tournament getTournament(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tournament.class, id);
        }catch (RuntimeException e) {
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
}

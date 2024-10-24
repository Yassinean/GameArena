package org.yassine.dao.impl;

import org.yassine.dao.Interface.ITeamDao;
import org.yassine.model.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class TeamDaoImpl implements ITeamDao {
    private EntityManagerFactory entityManagerFactory;

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public boolean createTeam(Team player) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(player);
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
    public boolean updateTeam(Team player) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(player);
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
    public boolean deleteTeam(int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Team tour = em.find(Team.class, id);
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
    public Team getTeam(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Team.class, id);
        }catch (RuntimeException e) {
            em.getTransaction().rollback();
        }
        return null;
    }

    @Override
    public List<Team> getAllTeams() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("FROM Team", Team.class).getResultList();
        } finally {
            entityManager.close();
        }
    }
}

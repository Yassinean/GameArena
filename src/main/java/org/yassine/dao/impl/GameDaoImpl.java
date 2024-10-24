package org.yassine.dao.impl;

import org.yassine.dao.Interface.IGameDao;
import org.yassine.dao.Interface.IGameDao;
import org.yassine.model.Game;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class GameDaoImpl implements IGameDao {
    private EntityManagerFactory entityManagerFactory;

    // Setter for dependency injection
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public boolean createGame(Game game) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(game);
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
    public boolean updateGame(Game game) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(game);
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
    public boolean deleteGame(Game game) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Game tour = em.find(Game.class, game.getId());
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
    public Game getGame(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Game.class, id);
        }catch (RuntimeException e) {
            em.getTransaction().rollback();
        }
        return null;
    }

    @Override
    public List<Game> getAllGames() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("FROM Game", Game.class).getResultList();
        } finally {
            entityManager.close();
        }
    }
}

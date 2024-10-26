package org.yassine.dao.impl;

import org.yassine.dao.Interface.IPlayerDao;
import org.yassine.model.Game;
import org.yassine.model.Player;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class PlayerDaoImpl implements IPlayerDao {
    private EntityManagerFactory entityManagerFactory;

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public boolean createPlayer(Player player) {
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
    public boolean updatePlayer(Player player) {
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
    public boolean deletePlayer(int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Player tour = em.find(Player.class, id);
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
    public Player getPlayer(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Player.class, id);
        }catch (RuntimeException e) {
            em.getTransaction().rollback();
        }
        return null;
    }

    @Override
    public List<Player> getAllPlayers() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("FROM Player", Player.class).getResultList();
        } finally {
            entityManager.close();
        }
    }
}

package org.yassine.service.impl;

import org.yassine.dao.Interface.ITeamDao;
import org.yassine.model.Team;
import org.yassine.service.Interface.ITeamService;

import java.util.List;

public class TeamServiceImp implements ITeamService {

    private final ITeamDao teamDao;

    // Constructor injection for dependency
    public TeamServiceImp(ITeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @Override
    public boolean createTeam(Team team) {
        return teamDao.createTeam(team);
    }

    @Override
    public boolean updateTeam(Team team) {
        return teamDao.updateTeam(team);
    }

    @Override
    public boolean deleteTeam(int id) {
        return teamDao.deleteTeam(id);
    }

    @Override
    public Team getTeam(int id) {
        return teamDao.getTeam(id);
    }

    @Override
    public List<Team> getAllTeams() {
        return teamDao.getAllTeams();
    }

    @Override
    public Team getTeamByName(String name) {
        return teamDao.getTeamByName(name);
    }
}

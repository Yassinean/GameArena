package org.yassine.dao.Interface;

import org.yassine.model.Team;

import java.util.List;

public interface ITeamDao {
    boolean createTeam(Team game);
    boolean updateTeam(Team game);
    boolean deleteTeam(int id);
    Team getTeam(int id);
    List<Team> getAllTeams();
    Team getTeamByName(String name);
}

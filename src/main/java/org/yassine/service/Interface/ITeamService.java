package org.yassine.service.Interface;

import org.yassine.model.Team;

import java.util.List;

public interface ITeamService {
    boolean createTeam(Team team);
    boolean updateTeam(Team team);
    boolean deleteTeam(int id);
    Team getTeam(int id);
    List<Team> getAllTeams();
}

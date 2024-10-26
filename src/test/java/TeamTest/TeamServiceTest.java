package TeamTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.yassine.dao.Interface.ITeamDao;
import org.yassine.model.Team;
import org.yassine.service.impl.TeamServiceImp;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TeamServiceTest {

    @Mock
    private ITeamDao teamDao;


    @InjectMocks
    private TeamServiceImp teamService;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testAddTeam() {
        Team team = new Team();
        team.setNom("Java");
        team.setClassement(5);
        doNothing().when(teamDao).createTeam(team);
        teamService.createTeam(team);
        verify(teamDao,times(1)).createTeam(team);
    }
    @Test
    public void testUpdateTeam() {
        Team team = new Team();
        team.setId(1);
        team.setNom("JavaUpdate");
        team.setClassement(6);
        doNothing().when(teamDao).updateTeam(team);
        teamService.updateTeam(team);
        verify(teamDao,times(1)).updateTeam(team);
    }

    @Test
    public void testDeleteTeam() {
        Team team = new Team();
        team.setId(1);
        when(teamDao.getTeam(1)).thenReturn(team);
        Team team1 = teamService.getTeam(1);
        doNothing().when(teamDao).deleteTeam(team1.getId());
        teamService.deleteTeam(team.getId());

        verify(teamDao,times(1)).getTeam(1);
        verify(teamDao,times(1)).deleteTeam(team1.getId());

    }

    @Test
    public void testGetTeamById() {
        Team team = new Team();
        team.setId(1);
        team.setNom("New Team");
        when(teamDao.getTeam(1)).thenReturn(team);
        Team team1 = teamService.getTeam(1);
        assert team1 != null;
        assert team1.getNom().equals("New Team");

        verify(teamDao,times(1)).getTeam(1);
    }

    @Test
    public void testGetAllTeams() {
        List<Team> teams = new ArrayList<>();
        teams.add(new Team());
        teams.add(new Team());

        when(teamDao.getAllTeams()).thenReturn(teams);
        List<Team> result = teamService.getAllTeams();

        assert result.size() == teams.size();
        verify(teamDao,times(1)).getAllTeams();
    }
}
package TeamTest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.context.ApplicationContext;
import org.yassine.model.Team;
import org.yassine.provider.ApplicationContextProvider;
import org.yassine.service.Interface.ITeamService;

import javax.transaction.Transactional;

public class TeamServiceIntegrationTest {
    private ITeamService teamService;
    private Team fetchedTeam;


    @BeforeEach
    public void setUp() throws Exception {
        ApplicationContext context = ApplicationContextProvider.getContext();
        teamService = (ITeamService) context.getBean("teamService");
    }

    @Test
    @Transactional
    public void testCreateAndFindTournament() {
        Team team = new Team();
        team.setNom("Team_1");
        team.setClassement(2);

        teamService.createTeam(team);
        fetchedTeam = teamService.getTeamByName("Team_1");
        assert fetchedTeam != null;
        assert fetchedTeam.getClassement() == 2;
    }


    @AfterEach
    public void cleanup() {
        // Step 4: Clean up by deleting the tournament after the test
        if (fetchedTeam != null && fetchedTeam.getId() > 0) {
            teamService.deleteTeam(fetchedTeam.getId());
        }
    }
}
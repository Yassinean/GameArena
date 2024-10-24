package org.yassine.service.impl;

import org.yassine.dao.Interface.ITournamentDao;
import org.yassine.model.Tournament;
import org.yassine.service.Interface.ITournamentService;

import java.util.List;

public class TournamentServiceImp implements ITournamentService {

    private final ITournamentDao tournamentDao;

    // Constructor injection for dependency
    public TournamentServiceImp(ITournamentDao tournamentDao) {
        this.tournamentDao = tournamentDao;
    }


    @Override
    public void runSampleOperations() {

    }

    @Override
    public boolean createTournament(Tournament tournament) {
        return tournamentDao.createTournament(tournament);
    }

    @Override
    public boolean updateTournament(Tournament tournament) {
        return tournamentDao.updateTournament(tournament);
    }

    @Override
    public boolean deleteTournament(Tournament tournament) {
        return tournamentDao.deleteTournament(tournament);
    }

    @Override
    public Tournament getTournament(int id) {
        return tournamentDao.getTournament(id);
    }

    @Override
    public List<Tournament> getAllTournaments() {
        return tournamentDao.getAllTournaments();
    }
}

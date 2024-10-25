package org.yassine.service.impl;

import org.yassine.dao.Interface.ITournamentDao;
import org.yassine.model.Tournament;
import org.yassine.service.Interface.ITournamentService;

import java.util.List;

public class TournamentServiceImp implements ITournamentService {

    private final ITournamentDao tournamentDao;

    public TournamentServiceImp(ITournamentDao tournamentDao) {
        this.tournamentDao = tournamentDao;
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
    public boolean deleteTournament(int id) {
        return tournamentDao.deleteTournament(id);
    }

    @Override
    public Tournament getTournament(int id) {
        return tournamentDao.getTournament(id);
    }

    @Override
    public List<Tournament> getAllTournaments() {
        return tournamentDao.getAllTournaments();
    }

    @Override
    public double calculerDureeEstimeeTournoi(int id) {
        return tournamentDao.calculerdureeEstimeeTournoi(id);
    }
}

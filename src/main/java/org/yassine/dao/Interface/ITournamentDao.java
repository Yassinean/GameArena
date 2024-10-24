package org.yassine.dao.Interface;

import org.yassine.model.Tournament;

import java.util.List;

public interface ITournamentDao {
    boolean createTournament(Tournament tournament);
    boolean updateTournament(Tournament tournament);
    boolean deleteTournament(Tournament tournament);
    Tournament getTournament(int id);
    List<Tournament> getAllTournaments();
}

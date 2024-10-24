package org.yassine.service.Interface;

import org.yassine.model.Tournament;

import java.util.List;

public interface ITournamentService {
    void runSampleOperations();
    boolean createTournament(Tournament tournament);
    boolean updateTournament(Tournament tournament);
    boolean deleteTournament(int id);
    Tournament getTournament(int id);
    List<Tournament> getAllTournaments();
}

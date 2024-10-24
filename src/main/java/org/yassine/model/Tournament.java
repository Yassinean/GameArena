package org.yassine.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tournament")
public class Tournament implements Serializable {

    public enum Statut {
        PLANIFIE, EN_COURS, TERMINE, ANNULE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String titre;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;


    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;


    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Team> teams = new ArrayList<>();


    @Column(name = "duree_estimee")
    private Double dureeEstimee;

    @Column(name = "temps_pause_matchs")
    private Double tempsPauseEntreMatchs;

    @Column(name = "temps_ceremonie")
    private Double tempsCeremonie;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Statut statut;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }
    public LocalDate getDateDebut() {
        return dateDebut;
    }
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
    public LocalDate getDateFin() {
        return dateFin;
    }
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
    public List<Team> getTeams() {
        return teams;
    }
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
    public Double getDureeEstimee() {
        return dureeEstimee;
    }
    public void setDureeEstimee(Double dureeEstimee) {
        this.dureeEstimee = dureeEstimee;
    }
    public Double getTempsPauseEntreMatchs() {
        return tempsPauseEntreMatchs;
    }
    public void setTempsPauseEntreMatchs(Double tempsPauseEntreMatchs) {
        this.tempsPauseEntreMatchs = tempsPauseEntreMatchs;
    }
    public Double getTempsCeremonie() {
        return tempsCeremonie;
    }
    public void setTempsCeremone(Double tempsCeremonie) {
        this.tempsCeremonie = tempsCeremonie;
    }
    public Statut getStatut() {
        return statut;
    }
    public void setStatut(Statut statut) {
        this.statut = statut;
    }

}

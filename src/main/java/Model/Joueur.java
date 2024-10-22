package Model;

import jakarta.persistence.*;
import javax.validation.constraints.*;


@Entity
@Table(name = "joueurs")
public class Joueur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Le pseudo ne peut pas être nul.")
    @Size(min = 1, max = 50, message = "Le pseudo doit contenir entre 1 et 50 caractères.")
    private String pseudo;

    @Min(value = 15, message = "L'âge minimum est de 10 ans.")
    @Max(value = 100, message = "L'âge maximum est de 100 ans.")
    private int age;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;

    public Joueur() {
    }

    public Joueur(String pseudo, int age, Equipe equipe) {
        this.pseudo = pseudo;
        this.age = age;
        this.equipe = equipe;
    }

    public Joueur(int id, String pseudo, int age, Equipe equipe) {
        this.id = id;
        this.pseudo = pseudo;
        this.age = age;
        this.equipe = equipe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    @Override
    public String toString() {
        return "Joueur{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", age=" + age +
                ", equipe=" + equipe +
                '}';
    }
}

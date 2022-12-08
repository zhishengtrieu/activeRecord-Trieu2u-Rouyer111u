package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest {
    private Personne p1;
    private Personne p2;
    private Film f1;
    private Film f2;
    private Film f3;
    private Film f4;
    private Film f5;
    private Film f6;
    private Film f7;


    @BeforeEach
    void setUp() {
        //on cree la table
        Film.createTable();
        Personne.createTable();

        //on cree les realisateurs
        p1 = new Personne("Spielberg", "Steven");
        p2 = new Personne("Scott", "Ridley");
        p1.save();
        p2.save();
        //on cree les films
        f1 = new Film("Arche perdue", p1);
        f2 = new Film("Alien", p1);
        f3 = new Film("Temple Maudit", p1);
        f4 = new Film("Blade Runner", p1);
        f5 = new Film("Alien3", p2);
        f6 = new Film("Fight Club", p2);
        f7 = new Film("Orange Mecanique", p2);

        //on les sauvegarde
        try {
            f1.save();
            f2.save();
            f3.save();
            f4.save();
            f5.save();
            f6.save();
            f7.save();
        } catch (RealisateurAbsentException e) {
            System.out.println("Realisateur absent");
        }
    }

    @AfterEach
    void tearDown() {
        Film.deleteTable();
        Personne.deleteTable();
    }

    @Test
    void findById() {
        //cas ou l'id existe
        assertEquals(f1.getTitre(), Film.findById(1).getTitre());
        //cas ou l'id n'existe pas
        assertNull(Film.findById(8));
        //cas ou l'id est negatif
        assertNull(Film.findById(-1));
        //cas ou l'id est nul
        assertNull(Film.findById(0));
        //cas ou la table est vide
        Film.deleteTable();
        Film.createTable();
        assertNull(Film.findById(1));
    }

    @Test
    void getRealisateur() {
        //cas ou le film existe
        assertEquals(p1.getNom(), f1.getRealisateur().getNom());
        //cas ou le film n'existe pas
        assertThrows(NullPointerException.class, () -> Film.findById(8).getRealisateur().getNom());
        //cas ou la table est vide
        Film.deleteTable();
        Film.createTable();
        assertThrows(NullPointerException.class, () -> Film.findById(1).getRealisateur().getNom());
    }

    @Test
    void findByRealisateur() {
        //cas ou le realisateur existe
        assertEquals(4, Film.findByRealisateur(p1).size());
        //cas ou le realisateur n'existe pas
        assertEquals(0, Film.findByRealisateur(Personne.findById(3)).size());
        //cas ou la table est vide
        Film.deleteTable();
        Film.createTable();
        assertEquals(0, Film.findByRealisateur(Personne.findById(1)).size());
    }
}
package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest {

    @BeforeEach
    void setUp() {
        //on cree la table
        Film.createTable();

        //on cree les realisateurs
        Personne p1 = new Personne("Spielberg", "Steven");
        Personne p2 = new Personne("Scott", "Ridley");
        //on cree les films
        Film f1 = new Film("Arche perdue", p1);
        Film f2 = new Film("Alien", p1);
        Film f3 = new Film("Temple Maudit", p1);
        Film f4 = new Film("Blade Runner", p1);
        Film f5 = new Film("Alien3", p2);
        Film f6 = new Film("Fight Club", p2);
        Film f7 = new Film("Orange Mecanique", p2);

        //on les sauvegarde
        p1.save();
        p2.save();
        f1.save();
        f2.save();
        f3.save();
        f4.save();
        f5.save();
        f6.save();
        f7.save();
    }

    @AfterEach
    void tearDown() {
        Film.deleteTable();
    }

    @Test
    void findById() {
        //cas ou l'id existe
        assertEquals("Arche perdue", Film.findById(1).getTitre());
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
        assertEquals("Spielberg", Film.findById(1).getRealisateur().getNom());
        //cas ou le film n'existe pas
        assertNull(Film.findById(8));
        //cas ou la table est vide
        Film.deleteTable();
        Film.createTable();
        assertNull(Film.findById(1));
    }

    @Test
    void findByRealisateur() {
        //cas ou le realisateur existe
        assertEquals(4, Film.findByRealisateur(Personne.findById(1)).size());
        //cas ou le realisateur n'existe pas
        assertEquals(0, Film.findByRealisateur(Personne.findById(3)).size());
        //cas ou la table est vide
        Film.deleteTable();
        Film.createTable();
        assertEquals(0, Film.findByRealisateur(Personne.findById(1)).size());
    }
}
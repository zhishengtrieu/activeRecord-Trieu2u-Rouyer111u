package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonneTest {
    private Personne p1;
    private Personne p2;

    @BeforeEach
    void setUp() {
        //on cree la table
        Personne.createTable();
        p1 = new Personne("Spielberg", "Steven");
        p1.save();
        p2 = new Personne("Scott", "Ridley");
        p2.save();
    }

    @AfterEach
    void tearDown() {
        //on supprime la table
        Personne.deleteTable();
    }

    @Test
    void findAll() {
        //cas ou la table contient des elements
        assertEquals(2, Personne.findAll().size());
        //cas ou la table est vide
        Personne.deleteTable();
        Personne.createTable();
        assertEquals(0, Personne.findAll().size());
    }

    @Test
    void findById() {
        //cas ou l'id existe
        assertEquals(p1.getNom(), Personne.findById(1).getNom());
        //cas ou l'id n'existe pas
        assertNull(Personne.findById(3));
        //cas ou l'id est negatif
        assertNull(Personne.findById(-1));
        //cas ou l'id est nul
        assertNull(Personne.findById(0));
        //cas ou la table est vide
        Personne.deleteTable();
        Personne.createTable();
        assertNull(Personne.findById(1));
    }

    @Test
    void findByName() {
        //cas ou le nom existe
        assertEquals(1, Personne.findByName("Ridley").size());
        //cas ou le nom n'existe pas
        assertEquals(0, Personne.findByName("crashtest").size());
        //cas ou la table est vide
        Personne.deleteTable();
        Personne.createTable();
        assertEquals(0, Personne.findByName("Ridley").size());
    }

    @Test
    void delete() {
        //cas ou l'id existe
        Personne.findById(1).delete();
        assertEquals(1, Personne.findAll().size());
        //cas ou l'id n'existe pas
        assertThrows(NullPointerException.class, () -> Personne.findById(3).delete());
        //cas ou la table est vide
        Personne.deleteTable();
        Personne.createTable();
        assertThrows(NullPointerException.class, () -> Personne.findById(1).delete());
    }

}
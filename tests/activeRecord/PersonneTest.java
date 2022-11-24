package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonneTest {

    @BeforeEach
    void setUp() {
        //on cree la table
        Personne.createTable();
    }

    @AfterEach
    void tearDown() {
        //on supprime la table
        Personne.deleteTable();
    }

    @Test
    void createTable() {
        //cas ou la table existe deja
        //la methode ne doit pas lever d'exception
        Personne.createTable();
        //cas ou la table n'existe pas
        Personne.deleteTable();
        Personne.createTable();
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByName() {
    }

    @Test
    void deleteTable() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void setNom() {
    }

    @Test
    void setPrenom() {
    }

}
package activeRecord;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    @Test
    void getConnection() {
        //on verifie que le singleton est bien instancie
        //cas ou testpersonne existe
        Connection connection = DBConnection.getConnection();
        assertNotNull(connection);
        //on verifie que le singleton est bien unique
        Connection connection2 = DBConnection.getConnection();
        assertEquals(connection, connection2);
        //on teste avec une base qui n'existe pas
        DBConnection.setNomDB("crashtest");
        Connection connection3 = DBConnection.getConnection();
        assertNull(connection3);
    }

    @Test
    void setNomDB() {
        //on verifie que les connexions sont bien differentes
        //cas ou les base testpersonne et infodb existent
        Connection connection = DBConnection.getConnection();
        DBConnection.setNomDB("infodb");
        Connection connection2 = DBConnection.getConnection();
        assertNotEquals(connection, connection2);
    }
}
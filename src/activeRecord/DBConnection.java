package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Cette classe permet d'avoir un singleton pour la connexion a la base de donnees
 */
public class DBConnection {

    private String nomDB;
    private String userName;
    private String password;
    private String serverName;
    private String portNumber;
    private Connection co;
    private static DBConnection dbc;

    /**
     * Constructeur prive pour le singleton
     */
    private DBConnection() {
        userName = "root";
        password = "";
        serverName = "localhost";
        portNumber = "3306";

        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + nomDB;

        try {
            co = DriverManager.getConnection(urlDB, connectionProps);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode pour recuperer l'instance unique de la connexion
     * @return l'instance de la connexion
     *
     */
    public static synchronized Connection getConnection() {
        if (dbc == null) {
            dbc = new DBConnection();
        }
        return dbc.co;
    }

    /**
     * Methode pour changer le nom de la base de donnees
     * @param nom
     */
    public synchronized void setNomDB(String nom) {
        nomDB = nom;
        //on supprime l'ancienne connexion
        dbc = null;
    }


}

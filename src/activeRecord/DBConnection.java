package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Cette classe permet d'avoir un singleton pour la connexion a la base de donnees
 */
public class DBConnection {

    private static String nomDB = "testpersonne";
    private  static String userName = "root";
    private  static String password = "";
    private  static String serverName = "localhost";
    private  static String portNumber = "3306";
    private Connection co;
    private static DBConnection dbc;

    /**
     * Constructeur prive pour le singleton
     */
    private DBConnection() {
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + nomDB;

        try {
            co = DriverManager.getConnection(urlDB, connectionProps);
        }catch (SQLException e) {
            System.out.println("Erreur de connexion a la base de donnees");
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
    public static synchronized void setNomDB(String nom) {
        nomDB = nom;
        //on supprime l'ancienne connexion
        dbc = null;
    }


}

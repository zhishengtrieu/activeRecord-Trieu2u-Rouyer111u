package activeRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Personne {

    private int id;
    private String nom;
    private String prenom;

    public Personne(String nom, String prenom) {
        this.id = -1;
        this.nom = nom;
        this.prenom = prenom;
    }

    /**
     * Methode qui permet de creer la table Personne
     */
    public static void createTable() {
        String createString = "CREATE TABLE Personne ( ID INTEGER  AUTO_INCREMENT, "
                + "NOM varchar(40) NOT NULL, PRENOM varchar(40) NOT NULL, PRIMARY KEY (ID))";
        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(createString);
            stmt.executeUpdate();
            System.out.println("Creation de la table Personne");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la creation de la table Personne");
        }
    }

    /**
     * Methode qui permet de recuperer toutes les personnes de la table Personne
     *
     * @return ArrayList<Personne>, liste des personnes de la db
     */
    public static ArrayList<Personne> findAll() {
        ArrayList<Personne> liste = new ArrayList<Personne>();
        try {
            String sql = "SELECT * FROM personne";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Personne p = new Personne(rs.getString("nom"), rs.getString("prenom"));
                p.id = rs.getInt("id");
                liste.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL");
        }
        return liste;
    }

    /**
     * Methode qui permet de trouver une personne par son id
     *
     * @param i, id de la personne
     * @return Personne, objet correspondant a la personne
     */
    public static Personne findById(int i) {
        Personne p = null;
        try {
            String sql = "SELECT * FROM personne WHERE id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, i);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p = new Personne(rs.getString("nom"), rs.getString("prenom"));
                p.id = i;
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL");
        }
        return p;
    }

    /**
     * Methode qui permet de trouver une liste de personnes par nom
     *
     * @param name, nom des personnes recherchees
     * @return ArrayList<Personne>, liste des personnes correspondant au nom
     */
    public static ArrayList<Personne> findByName(String name) {
        ArrayList<Personne> liste = new ArrayList<Personne>();
        try {
            String sql = "SELECT * FROM personne where prenom = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                liste.add(new Personne(rs.getString("nom"), rs.getString("prenom")));
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL");
        }
        return liste;
    }

    /**
     * Methode qui supprime la table Personne
     */
    public static void deleteTable() {
        String deleteString = "DROP TABLE Personne";
        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(deleteString);
            stmt.executeUpdate();
            System.out.println("Suppression de la table Personne");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la table Personne");
        }
    }

    /**
     * Methode qui permet d'ajouter une personne a la table Personne
     */
    public void save() {
        //si l'id est -1, alors la personne n'existe pas dans la db
        if (id == -1) {
            saveNew();
        } else {
            update();
        }
    }

    /**
     * Methode privee qui permet d'ajouter une nouvelle personne a la table Personne
     */
    private void saveNew() {
        try {
            String sql = "INSERT INTO personne (nom, prenom) VALUES (?, ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.executeUpdate();
            //on recupere l'id de la personne
            sql = "SELECT id FROM personne WHERE nom = ? AND prenom = ?";
            ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL");
        }
    }

    /**
     * Methode privee qui permet de mettre a jour une personne dans la table Personne
     */
    private void update() {
        try {
            //si elle existe deja, on la met a jour
            String sql = "UPDATE personne SET nom = ?, prenom = ? WHERE id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setInt(3, id);
            ps.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Erreur SQL");
        }
    }

    /**
     * Methode qui permet de supprimer une personne de la table Personne
     */
    public void delete() {
        //on verifie que la personne existe bien dans la db
        if (id != -1) {
            try {
                String sql = "DELETE FROM personne WHERE id = ?";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erreur SQL");
            }
        }
    }

    /**
     * Methode qui permet de modifier le nom d'une personne
     *
     * @param name, le nouveau nom
     */
    public void setNom(String name) {
        if (name!=null){
            nom = name;
        }
    }

    /**
     * Methode qui permet de modifier le prenom d'une personne
     *
     * @param str, nouveau prenom
     */
    public void setPrenom(String str) {
        if (str!=null){
            prenom = str;
        }
    }

    /**
     * Getter de l'id
     *
     * @return int, id de la personne
     */
    public int getId() {
        return id;
    }

    /**
     * Getter de l'attribut nom
     *
     * @return String, nom de la personne
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter de l'attribut prenom
     *
     * @return String, prenom de la personne
     */
    public String getPrenom() {
        return prenom;
    }
}

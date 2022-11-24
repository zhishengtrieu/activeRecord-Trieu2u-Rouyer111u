package activeRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Film {

    private String titre;
    private int id;
    private int id_real;

    /**
     * Constructeur public
     * @param t le titre
     * @param p la Personne
     */
    public Film(String t, Personne p) {
        this.titre = t;
        this.id_real = p.getId();
        this.id = -1;
    }

    /**
     * Constructeur privee
     * @param t le titre
     * @param id l'id du film
     * @param idr l'id du realisateur
     */
    private Film(String t, int id, int idr){
        this.titre = t;
        this.id = id;
        this.id_real = idr;
    }

    /**
     * Methode de creation de la table Film
     */
    public static void createTable() {
        String createString = "CREATE TABLE Film ( TITRE varchar(40) NOT NULL, "
                + "ID INTEGER, ID_REAL INTEGER, PRIMARY KEY (ID))";
        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(createString);
            stmt.executeUpdate();
            System.out.println("Creation de la table Film");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la creation de la table Film");
        }
    }

    /**
     * Methode permettant de retrouver un film grace a son id
     * @param i l'id
     * @return le film correspondant a l'id
     */
    public static Film findById(int i) {
        Film f = null;
        try{
            String sql = "SELECT * FROM film WHERE id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, i);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                f = new Film(rs.getString("titre"), rs.getInt("id"), rs.getInt("id_real"));
            }
        } catch (SQLException e){
            System.out.println("Erreur SQL");
        }
        return f;
    }

    /**
     * ¨Permet de trouver un realisateur a l'aide de son id
     * @return le realisateur correspondant a l'id
     */
    public Personne getRealisateur(){
        Personne p = null;
        try{
            String sql = "SELECT * FROM personne WHERE id = id_real";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p = new Personne(rs.getString("nom"), rs.getString("prenom"));
            }
        } catch (SQLException e){
            System.out.println("Erreur SQL");
        }
        return p;
    }

    /**
     * Methode de suppression de la table Film
     */
    public static void deleteTable() {
        String deleteString = "DROP TABLE Film";
        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(deleteString);
            stmt.executeUpdate();
            System.out.println("Suppression de la table Film");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la table Film");
        }
    }

    public void save() {
        //si l'id est -1, alors la personne n'existe pas dans la db
        if (id == -1) {
            saveNew();
        } else {
            update();
        }
    }

    /**
     * Getter de id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter de titre
     * @return titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Getter de id_real
     * @return id_real
     */
    public int getId_real(){
        return id_real;
    }

    public void setTitre(String t){
        titre = t;
    }
}

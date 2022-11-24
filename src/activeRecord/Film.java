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

    public static void createTable() {
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

    public static void deleteTable() {
    }

    public void save() {
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
}

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

    /**
     * Methode privee qui permet d'ajouter un nouveau film a la table Film
     */
    private void saveNew() {
        try {
            String sql = "INSERT INTO personne (titre, id_real) VALUES (?, ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, titre);
            ps.setInt(2, id_real);
            ps.executeUpdate();
            //on recupere l'id de la personne
            sql = "SELECT id FROM film WHERE titre = ? AND id_real = ?";
            ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, titre);
            ps.setInt(2, id_real);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL");
        }
    }

    /**
     * Methode privee qui permet de mettre a jour un film dans la table Film
     */
    private void update() {
        try {
            String sql = "UPDATE film SET titre = ?, id = ? WHERE id_real = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, titre);
            ps.setInt(2, id);
            ps.setInt(3, id_real);
            ps.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Erreur SQL");
        }
    }

    /**
     * Methode permettant de sauvegarder un film dans la table Film
     */
    public void save() {
        //si l'id est -1, alors le film n'existe pas dans la db
        if (id == -1) {
            saveNew();
        } else {
            //sinon on met a jour le film
            update();
        }
    }

    /**
     * Getter de l'id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter du titre
     * @return titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Getter de l'id du realisateur
     * @return id_real
     */
    public int getId_real(){
        return id_real;
    }

    /**
     * Setter de titre
     * @param t le nouveau titre
     */
    public void setTitre(String t){
        titre = t;
    }
}

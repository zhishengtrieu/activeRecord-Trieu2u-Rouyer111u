package activeRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Personne {

    private int id;
    private String nom;
    private String prenom;

    public Personne(String spielberg, String steven) {
        this.id = -1;
        this.nom = spielberg;
        this.prenom = steven;
    }

    public static void createTable() {
    }

    public static ArrayList<Personne> findAll() throws SQLException {
        ArrayList<Personne> liste = new ArrayList<Personne>();
        try {
            String sql = "SELECT * FROM personne";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                liste.add(new Personne(rs.getString("nom"), rs.getString("prenom")));
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL");
        }
        return liste;
    }

    public static Personne findById(int i) {
        Personne p = null;
        try{
            String sql = "SELECT * FROM personne WHERE id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, i);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p = new Personne(rs.getString("nom"), rs.getString("prenom"));
            }
        } catch (SQLException e){
            System.out.println("Erreur SQL");
            }
        return p;
    }

    public static ArrayList<Personne> findByName(String fincher) {
        ArrayList<Personne> liste = new ArrayList<Personne>();
        try {
            String sql = "SELECT * FROM personne where prenom = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, fincher);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                liste.add(new Personne(rs.getString("nom"), rs.getString("prenom")));
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL");
        }
        return liste;
    }

    public static void deleteTable() {
    }

    public void save() {
    }

    public void delete() {
    }

    public void setNom(String f_i_n_c_h_e_r) {
    }

    public void setPrenom(String davif) {
    }

    public void getId() {
    }

    public void getNom() {
    }

    public void getPrenom() {
    }
}

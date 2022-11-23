package activeRecord;

public class DBConnection {

    String nom;
    private static DBConnection dbc;

    public DBConnection(String nom) {
        this.nom = nom;
    }


    public DBConnection getInstance() {
        if (dbc == null) {
            dbc = new DBConnection();
        }
        return dbc;
    }

    public void setNomDB(String nomDB){
        this.nom=nomDB;
        getInstance();
    }


}


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private final Connection conn;

    public ReizigerDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO reiziger values (?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, reiziger.getId());
            preparedStatement.setString(2, reiziger.getVoorletters());
            preparedStatement.setString(3, reiziger.getTussenvoegsel());
            preparedStatement.setString(4, reiziger.getAchternaam());
            preparedStatement.setDate(5, (Date) reiziger.getGeboortedatum());
            return preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException + " fout bij opslaan reiziger!");
            return false;
        }
    }
    //update functie
    @Override
    public boolean update(Reiziger reiziger) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?");
            preparedStatement.setString(1, reiziger.getVoorletters());
            preparedStatement.setString(2, reiziger.getTussenvoegsel());
            preparedStatement.setString(3, reiziger.getAchternaam());
            preparedStatement.setDate(4, (Date) reiziger.getGeboortedatum());
            preparedStatement.setInt(5, reiziger.getId());
            return preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException + " fout bij updaten reiziger!");
            return false;
        }
    }
    //verwijder functie
    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM reiziger WHERE reiziger_id = ?");
            preparedStatement.setInt(1, reiziger.getId());
            return preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException + " fout bij verwijderen reiziger!");
            return false;
        }
    }
    // vind bij id
    @Override
    public Reiziger findById(int id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM reiziger WHERE reiziger_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            String voorletters = null;
            String tussenvoegsel = null;
            String achternaam = null;
            Date geboortedatum = null;
            Reiziger reiziger;


            while (resultSet.next()) {
                voorletters = resultSet.getString("voorletters");
                tussenvoegsel = resultSet.getString("tussenvoegsel");
                achternaam = resultSet.getString("achternaam");
                geboortedatum = resultSet.getDate("geboortedatum");
            }
            reiziger = new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum);
            return reiziger;
        } catch (SQLException sqlException) {
            System.out.println(sqlException + " fout bij vinden bij id reiziger!");
            return null;
        }
    }
    //vind bij geboortedatum
    @Override
    public List<Reiziger> findByGbDatum(String datum) {
        try {
            List<Reiziger> opDatum = new ArrayList<>();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum = ?");
            preparedStatement.setDate(1, Date.valueOf(datum));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("reiziger_id");
                int reizigerId = Integer.parseInt(id);
                String voorletters = resultSet.getString("voorletters");
                String tussenvoegsel = resultSet.getString("tussenvoegsel");
                String achternaam = resultSet.getString("achternaam");
                Date geboortedatum = resultSet.getDate("geboortedatum");
                Reiziger reiziger = new Reiziger(reizigerId, voorletters, tussenvoegsel, achternaam, geboortedatum);
                opDatum.add(reiziger);
            }
            return opDatum;
        } catch (SQLException sqlException) {
            System.out.println(sqlException + " fout bij vinden bij geboortedatum reiziger!");
            return null;
        }
    }
    //vind bij all
    @Override
    public List<Reiziger> findAll() {
        try {
            List<Reiziger> alleReizigers = new ArrayList<>();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM reiziger;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("reiziger_id");
                int reizigerId = Integer.parseInt(id);
                String voorletters = resultSet.getString("voorletters");
                String tussenvoegsel = resultSet.getString("tussenvoegsel");
                String achternaam = resultSet.getString("achternaam");
                Date geboortedatum = resultSet.getDate("geboortedatum");
                Reiziger reiziger = new Reiziger(reizigerId, voorletters, tussenvoegsel, achternaam, geboortedatum);
                alleReizigers.add(reiziger);
            }
            return alleReizigers;
        } catch (SQLException sqlException) {
            System.out.println(sqlException + " fout bij vinden bij alle() reiziger!" );
            return null;
        }
    }
}

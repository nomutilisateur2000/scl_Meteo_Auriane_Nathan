package ig.hearc.ig.scl.repository;

import ig.hearc.ig.scl.business.Pays;
import ig.hearc.ig.scl.business.StationMeteo;
import ig.hearc.ig.scl.exception.PaysExisteDeja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StationRepository {
    private static final String QUERY = "INSERT INTO STATION (TIME_ZONE,LATITUDE,LONGITUDE,NOM,NUM_PAYS) VALUES (?,?,?,?,(SELECT NUMERO FROM PAYS WHERE ? = CODE))";
    private final Connection CONNECTION;
    PreparedStatement myStatement;
    public StationRepository(Connection connection) {
        this.CONNECTION = connection;
    }

    public void insert(StationMeteo station,Pays pays) throws SQLException {
        try {
            CONNECTION.setAutoCommit(false);
            myStatement = CONNECTION.prepareStatement(QUERY);
            myStatement.setInt(1,station.getTimeZone());
            myStatement.setDouble(2,station.getLatitude());
            myStatement.setDouble(3,station.getLongitude());
            myStatement.setString(4,station.getNom());
            myStatement.setString(5,pays.getCode());

            int rowsAffected = myStatement.executeUpdate();
            if (rowsAffected == 0){
                throw new SQLException("insertion de la station immpossible");
            }
        } catch (SQLException e){
            if(e.getErrorCode() == 20002){
                throw new PaysExisteDeja("La station existe déjà et ne sera donc pas enregistré");
            }
            throw e;
        }
    }
}

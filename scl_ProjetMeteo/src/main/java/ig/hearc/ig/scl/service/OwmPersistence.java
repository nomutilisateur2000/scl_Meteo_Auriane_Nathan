package ig.hearc.ig.scl.service;

import ig.hearc.ig.scl.business.Meteo;
import ig.hearc.ig.scl.business.Pays;
import ig.hearc.ig.scl.business.StationMeteo;
import ig.hearc.ig.scl.persistence.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class OwmPersistence implements OwmPersistenceManagement{
    public void insert(Meteo meteo, Pays pays, StationMeteo stationMeteo){
        try {
            Connection connection = DBConnection.getConnection();
            if(connection != null){
                System.out.println("youpi");
                connection.close();
            }else{
                System.out.println("Connexion avec la base de donnée impossible");
            }


        } catch (SQLException e) {

        }

    }
}

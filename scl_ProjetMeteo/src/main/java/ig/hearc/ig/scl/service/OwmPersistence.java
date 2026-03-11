package ig.hearc.ig.scl.service;

import ig.hearc.ig.scl.business.Meteo;
import ig.hearc.ig.scl.business.Pays;
import ig.hearc.ig.scl.business.StationMeteo;
import ig.hearc.ig.scl.persistence.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OwmPersistence implements OwmPersistenceManagement{
    public int insert(Meteo meteo, Pays pays, StationMeteo stationMeteo){
        int resultInsert = 0;
        try {
            Connection connection = DBConnection.getConnection();
            if(connection != null){

               /* connection.setAutoCommit(false);
                PreparedStatement myStatement;
                myStatement = connection.prepareStatement("INSERT INTO PAYS (NOM,CODE) VALUES (?,?)");
                myStatement.setString(1,pays.getName());
                myStatement.setString(2,pays.getCode());
                int rowsAffectedPays = myStatement.executeUpdate();
                System.out.println(rowsAffectedPays);
                myStatement.close();

                myStatement = connection.prepareStatement("INSERT INTO STATION (TIME_ZONE,LATITUDE,LONGITUDE,NOM,NUM_PAYS) VALUES (?,?,?,?,(SELECT NUMERO FROM PAYS WHERE ? = CODE))");
                myStatement.setInt(1,stationMeteo.getTimeZone());
                myStatement.setDouble(2,stationMeteo.getLatitude());
                myStatement.setDouble(3,stationMeteo.getLongitude());
                myStatement.setString(4,stationMeteo.getNom());
                myStatement.setString(5,pays.getCode());
                int rowsAffectedStation = myStatement.executeUpdate();
                System.out.println(rowsAffectedStation);
                myStatement.close();

                myStatement = connection.prepareStatement("INSERT INTO METEO (DESCRIPTION,DATE_MESURE,TEMPERATURE,TEMP_RESSENTI,PRESSION,HUMIDITE,VENT_VITESSE,VENT_ORIENTATION,ICON,NUM_STATION) VALUES (?,?,?,?,?,?,?,?,?,(SELECT NUMERO FROM STATION WHERE ? = NOM))");
                myStatement.setString(1,meteo.getDescription());
                myStatement.setDate(2, (Date) meteo.getDate());
                myStatement.setDouble(3,meteo.getTemperature());
                myStatement.setDouble(4,meteo.getTemperatureRessentie());
                myStatement.setInt(5,meteo.getPression());
                myStatement.setDouble(6,meteo.getHumidite());
                myStatement.setDouble(7,meteo.getVentVitesse());
                myStatement.setDouble(8,meteo.getVentOrientation());
                myStatement.setString(9,meteo.getIcon());
                myStatement.setString(10,stationMeteo.getNom());
                int rowsAffectedMeteo = myStatement.executeUpdate();
                System.out.println(rowsAffectedMeteo);
                myStatement.close();

                connection.commit();*/
                connection.close();
                resultInsert++;
                return resultInsert;
            }else{
                return resultInsert;
            }


        } catch (SQLException e) {
            return resultInsert;
        }

    }
}

package ig.hearc.ig.scl.service;

import ig.hearc.ig.scl.business.Meteo;
import ig.hearc.ig.scl.business.Pays;
import ig.hearc.ig.scl.business.StationMeteo;
import ig.hearc.ig.scl.exception.PaysExisteDeja;
import ig.hearc.ig.scl.exception.StationExisteDeja;
import ig.hearc.ig.scl.persistence.DBConnection;
import ig.hearc.ig.scl.repository.MeteoRepository;
import ig.hearc.ig.scl.repository.PaysRepository;
import ig.hearc.ig.scl.repository.StationRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class OwmPersistence implements OwmPersistenceManagement{
    public void insertAll(Meteo meteo, Pays pays, StationMeteo stationMeteo){

        try {
            Connection connection = DBConnection.getConnection();
            connection.setAutoCommit(false);
            PaysRepository paysRepo = new PaysRepository(connection);
            MeteoRepository meteoRepo = new MeteoRepository(connection);
            StationRepository stationRepo = new StationRepository(connection);

            try{
                try{
                    paysRepo.insert(pays);
                } catch (PaysExisteDeja e){
                    System.out.println(e.getMessage());
                }

                try{
                    stationRepo.insert(stationMeteo, pays);
                }catch (StationExisteDeja e){
                    System.out.println(e.getMessage());
                }
                try{
                    meteoRepo.insert(meteo, stationMeteo);
                }catch (Exception e){
                    System.out.println(e.getCause());
                }

                    connection.commit();

            }catch (SQLException e){
                connection.rollback();
                System.out.println(e.getCause());
            } finally {
                connection.close();
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("connexion impossible");
        }catch(NullPointerException e){
            System.out.println("JSP");
            e.printStackTrace();
        }
    }
}

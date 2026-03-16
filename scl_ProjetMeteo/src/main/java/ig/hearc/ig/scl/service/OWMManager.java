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
import ig.hearc.ig.scl.tools.Log;

import java.sql.Connection;
import java.sql.SQLException;

public class OWMManager implements IOWMManager {
    public void insertAll(Meteo meteo, Pays pays, StationMeteo stationMeteo){

        try {
            Connection connection = DBConnection.getConnection();
            if (connection == null){
                Log.warn("Problème lors de la connexion à la base de données");
                return;
            }

            connection.setAutoCommit(false);
            PaysRepository paysRepo = new PaysRepository(connection);
            MeteoRepository meteoRepo = new MeteoRepository(connection);
            StationRepository stationRepo = new StationRepository(connection);


            try{
                try{
                    paysRepo.insert(pays);
                } catch (PaysExisteDeja e){

                }

                try{
                    stationRepo.insert(stationMeteo, pays);
                }catch (StationExisteDeja e){

                }
                try{
                    meteoRepo.insert(meteo, stationMeteo);
                }catch (Exception e){
                    Log.warn(e.getMessage());
                }

                    connection.commit();

            }catch (SQLException e){
                connection.rollback();
                Log.warn(e.getMessage());
            } finally {
                connection.close();
            }

        }catch (SQLException e){
            Log.warn(e.getMessage());
        }
    }
}

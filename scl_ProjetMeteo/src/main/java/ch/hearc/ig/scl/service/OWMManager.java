package ch.hearc.ig.scl.service;

import ch.hearc.ig.scl.business.Meteo;
import ch.hearc.ig.scl.business.Pays;
import ch.hearc.ig.scl.business.StationMeteo;

import ch.hearc.ig.scl.persistence.DBConnection;
import ch.hearc.ig.scl.repository.MeteoRepository;
import ch.hearc.ig.scl.repository.PaysRepository;
import ch.hearc.ig.scl.repository.StationRepository;
import ch.hearc.ig.scl.tools.Log;

import java.sql.Connection;
import java.sql.SQLException;

public class OWMManager implements IOWMManager {
    public boolean insertAll(Meteo meteo, Pays pays, StationMeteo stationMeteo){

        if(stationMeteo.getPays() == null || stationMeteo.getIdStation().equals(0)){
            return false;
        }

        try {
            Connection connection = DBConnection.getConnection();
            if (connection == null){
                Log.warn("Problème lors de la connexion à la base de données");
                return false;
            }

            connection.setAutoCommit(false);
            PaysRepository paysRepo = new PaysRepository(connection);
            MeteoRepository meteoRepo = new MeteoRepository(connection);
            StationRepository stationRepo = new StationRepository(connection);


            try{
                try{
                    paysRepo.insert(pays);
                } catch (SQLException e){

                }

                try{
                    stationRepo.insert(stationMeteo, pays);
                }catch (SQLException e){

                }
                try{
                    meteoRepo.insert(meteo, stationMeteo);
                }catch (SQLException e){
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
        return true;
    }
}

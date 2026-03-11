package ig.hearc.ig.scl.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ig.hearc.ig.scl.business.Meteo;
import ig.hearc.ig.scl.business.StationMeteo;
import ig.hearc.ig.scl.persistence.DBConnection;
import ig.hearc.ig.scl.service.MeteoDeserializer;
import ig.hearc.ig.scl.service.StationMeteoDeserializer;
import ig.hearc.ig.scl.service.ApiCallService;

import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

            try {
                Connection connection = DBConnection.getConnection();
                if(connection != null){
                    System.out.println("youpi");

                    Scanner sc = new Scanner(System.in);
                    double lat, lon;

                    // Saisie latitude
                    do {
                        System.out.print("Entrer une latitude : ");
                        lat = sc.nextDouble();
                    } while (Double.isNaN(lat));

                    // Saisie longitude
                    do {
                        System.out.print("Entrer une longitude : ");
                        lon = sc.nextDouble();
                    } while (Double.isNaN(lon));

                    sc.close();

                    try {
                        // Appel de l'API
                        HttpResponse<String> response = ApiCallService.callAPI(lat, lon);

                        // Mapper pour Meteo
                        ObjectMapper mapperMeteo = new ObjectMapper();
                        SimpleModule moduleMeteo = new SimpleModule();
                        moduleMeteo.addDeserializer(Meteo.class, new MeteoDeserializer());
                        mapperMeteo.registerModule(moduleMeteo);

                        // Mapper pour StationMeteo
                        ObjectMapper mapperStation = new ObjectMapper();
                        SimpleModule moduleStation = new SimpleModule();
                        moduleStation.addDeserializer(StationMeteo.class, new StationMeteoDeserializer());
                        mapperStation.registerModule(moduleStation);

                        // Désérialisation
                        Meteo meteo = mapperMeteo.readValue(response.body(), Meteo.class);
                        StationMeteo station = mapperStation.readValue(response.body(), StationMeteo.class);

                        // Affichage
                        System.out.println("=== Objet Meteo ===");
                        System.out.println(meteo);

                        System.out.println("\n=== Objet StationMeteo ===");
                        System.out.println(station);
                        System.out.println("Pays associé : " + station.getPays());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    connection.close();
                }else{
                    System.out.println("Connexion avec la base de donnée impossible");
                }


            } catch (SQLException e) {

            }




    }
}
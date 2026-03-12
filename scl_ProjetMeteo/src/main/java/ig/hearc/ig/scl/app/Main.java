package ig.hearc.ig.scl.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ig.hearc.ig.scl.business.Meteo;
import ig.hearc.ig.scl.business.StationMeteo;

import ig.hearc.ig.scl.exception.PaysExisteDeja;
import ig.hearc.ig.scl.service.*;

import java.net.http.HttpResponse;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        double lat, lon;
        OwmPersistenceManagement openWeatherMapManager = new OwmPersistence();

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
            OwmPersistenceManagement service = new OwmPersistence();
            service.insertAll(meteo, station.getPays(), station);


        } catch (NullPointerException e){
            System.out.println("La clé d'API n'est pas correcte");
        } catch (PaysExisteDeja paysExisteDeja){
            System.out.println(paysExisteDeja.getMessage());
        } catch (com.fasterxml.jackson.core.JsonProcessingException e){
            System.out.println(e.getMessage());
        }

    }
}
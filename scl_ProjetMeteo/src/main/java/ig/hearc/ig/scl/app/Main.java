package ig.hearc.ig.scl.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ig.hearc.ig.scl.business.Meteo;
import ig.hearc.ig.scl.business.StationMeteo;

import ig.hearc.ig.scl.exception.PaysExisteDeja;
import ig.hearc.ig.scl.service.*;

import java.net.http.HttpResponse;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Double lat = Double.NaN;
        Double lon = Double.NaN;

        // Saisie latitude
        do {
            try{
                System.out.print("Entrer une latitude : ");
                lat = sc.nextDouble();

                if (lat < -90 || lat > 90){
                    System.out.println("La latitude être comprise entre -90 et +90");
                    lat = Double.NaN;
                }
            } catch(InputMismatchException wrongType){
                System.out.println("La longitude être comprise entre -90 et +90");
                sc.nextLine();
            }

        } while (Double.isNaN(lat));

        // Saisie longitude
        do {
            try {
                System.out.print("Entrer une longitude : ");
                lon = sc.nextDouble();
                if (lon < -180 || lon > 180) {
                    System.out.println("La longitude être comprise entre -180 et +180");
                    lon = Double.NaN;
                }
            }catch (InputMismatchException wrongType){
                System.out.println("La longitude être comprise entre -180 et +180");
                sc.nextLine();
            }
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

            //Insertion des données meteo dans la BDD
            IOWMManager service = new OWMManager();
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
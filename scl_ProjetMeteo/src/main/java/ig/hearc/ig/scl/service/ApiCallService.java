package ig.hearc.ig.scl.service;

import java.io.IOException;
import java.net.*;
import java.net.http.*;

public class ApiCallService {
    final static String apiKey = "268a24058a99be0be4586bc24b78e298";

    public ApiCallService() {}

    public static HttpResponse<String> callAPI(Double lat, Double lon){
        HttpResponse<String> response = null;
        // Créer un client HTTP
        HttpClient client = HttpClient.newHttpClient();
        // Construire une requête HTTP
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey))
                .build();
        // Envoyer la requête et obtenir la réponse
        try {
            response = client.send(request,HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}

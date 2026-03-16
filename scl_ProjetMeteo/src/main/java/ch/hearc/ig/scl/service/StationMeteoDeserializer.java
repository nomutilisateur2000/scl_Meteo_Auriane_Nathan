package ch.hearc.ig.scl.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ch.hearc.ig.scl.business.Pays;
import ch.hearc.ig.scl.business.StationMeteo;

import java.io.IOException;

public class StationMeteoDeserializer extends JsonDeserializer<StationMeteo> {

    @Override
    public StationMeteo deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {

        JsonNode root = parser.getCodec().readTree(parser);

        // Fuseau horaire
        int timeZone = root.path("timezone").asInt(0);

        // Coordonnées
        double latitude = root.path("coord").path("lat").asDouble(0.0);
        double longitude = root.path("coord").path("lon").asDouble(0.0);

        // Nom de la station
        String nomStation = root.path("name").asText("Inconnu");

        // --- Création du Pays avec code et récupération du nom via ton service ---
        Pays pays = new Pays();
        String code = root.path("sys").path("country").asText(null);
        if (code != null) {
            pays.setCode(code);
            PaysService.callApiName(pays);
        } else {
            pays.setCode("N/A");
            pays.setName("Inconnu");
        }

        // Retourner la station complète
        return new StationMeteo(timeZone, pays, latitude, longitude, nomStation);
    }
}
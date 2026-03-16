package ch.hearc.ig.scl.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ch.hearc.ig.scl.business.Pays;

import java.io.IOException;

public class PaysDeserializer extends JsonDeserializer<Pays> {

    @Override
    public Pays deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        // Lire le JSON en tant que JsonNode
        JsonNode root = parser.getCodec().readTree(parser);

        // Récupérer le code du pays (sys.country)
        String code = root.path("sys").path("country").asText(null);

        // Créer le Pays
        Pays pays = new Pays();
        if (code != null) {
            pays.setCode(code);

            // Remplir le nom via ton service simplifié
            PaysService.callApiName(pays);
        } else {
            // Valeur par défaut si pas de code
            pays.setCode("N/A");
            pays.setName("Inconnu");
        }

        return pays;
    }
}
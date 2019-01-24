package ore.utils.keycloak.client.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseKeycloakDto {
    private static final Gson TO_STRING_GSON = new GsonBuilder()
            .setPrettyPrinting()
            .setLenient()
            .create();
    
    @Override
    public String toString() {
        return TO_STRING_GSON.toJson(this);
    }
}
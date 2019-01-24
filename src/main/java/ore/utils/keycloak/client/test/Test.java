package ore.utils.keycloak.client.test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import ore.utils.keycloak.client.KeycloakClient;
import ore.utils.keycloak.client.KeycloakClientBuilder;
import ore.utils.keycloak.client.data.KMappingsDto;
import ore.utils.keycloak.client.data.KUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);
    
    public static void main(String[] args) throws Exception {
        String clientName = System.getProperty("test.client.name");
        
        String realm = System.getProperty("test.realm");
        KeycloakClient client = new KeycloakClientBuilder()
                .setHost(System.getProperty("test.host"))
                .setPort(Integer.parseInt(System.getProperty("test.port")))
                .setClientId(System.getProperty("test.client.id"))
                .setUsername(System.getProperty("test.username"))
                .setPassword(System.getProperty("test.password"))
                .build();
        
        String clientId = client.getClientIdMap(realm).get(clientName);
        
        List<KUserDto> users = client.getUsers(realm);
        LOGGER.info("user count: {}", users.size());
        
        for (int i = 0; i < users.size(); i++) {
            KUserDto user = users.get(i);
            String userId = user.id;
            LOGGER.info("{} [{}]: {}", user.username, userId, null);
            LOGGER.info("    {}", client.getUserRolesClientLevel(realm, userId, clientId, true));
        }
    }
}
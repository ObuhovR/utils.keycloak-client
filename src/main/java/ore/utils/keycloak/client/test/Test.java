package ore.utils.keycloak.client.test;

import java.util.List;
import ore.utils.keycloak.client.KeycloakClient;
import ore.utils.keycloak.client.KeycloakClientBuilder;
import ore.utils.keycloak.client.data.KRealm;
import ore.utils.keycloak.client.data.KUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);
    
    public static void print(String s) {
        StringBuilder builder = new StringBuilder(s);
        while (builder.length() > 100) {
            System.out.println(builder.substring(0, 100));
            builder.delete(0, 100);
        }
        System.out.println(builder);
    }
    
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
        
        List<KRealm> realms = client.getRealms();
        LOGGER.info("realms - {}", realms);
        
        String clientId = client.getClientIdMap(realm).get(clientName);
        
        List<KUserDto> users = client.getUsers(realm);
        LOGGER.info("user count: {}", users.size());
    }
}
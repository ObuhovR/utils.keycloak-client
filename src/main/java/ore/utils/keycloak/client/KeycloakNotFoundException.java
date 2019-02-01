package ore.utils.keycloak.client;

public class KeycloakNotFoundException extends RuntimeException {
    public KeycloakNotFoundException(String message) { super(message); }
}
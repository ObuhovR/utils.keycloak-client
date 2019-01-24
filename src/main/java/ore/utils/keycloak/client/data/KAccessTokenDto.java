package ore.utils.keycloak.client.data;

public class KAccessTokenDto extends BaseKeycloakDto {
    public String accessToken;
    public Integer expiresIn;
    public String refreshToken;
    public Integer refreshExpiresIn;
    public String tokenType;
    public String idToken;
    public Integer notBeforePolicy;
    public String sessionState;
}
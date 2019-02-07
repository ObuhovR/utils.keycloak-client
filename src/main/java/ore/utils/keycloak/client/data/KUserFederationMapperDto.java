package ore.utils.keycloak.client.data;

import java.util.Map;

public class KUserFederationMapperDto extends BaseIdKeycloakDto {
    public Map<String, Object> config;
    public String federationMapperType;
    public String federationProviderDisplayName;
}
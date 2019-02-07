package ore.utils.keycloak.client.data;

import java.util.Map;

public class KRequiredActionProviderDto extends BaseKeycloakDto {
    public String alias;
    public Map<String, Object> config;
    public Boolean defaultAction;
    public Boolean enabled;
    public String name;
    public String providerId;
}
package ore.utils.keycloak.client.data;

import java.util.List;

public class KScopeDto extends BaseNamedKeycloakDto {
    public String iconUri;
    public List<KPolicyDto> policies;
    public List<KResourceDto> resources;
}
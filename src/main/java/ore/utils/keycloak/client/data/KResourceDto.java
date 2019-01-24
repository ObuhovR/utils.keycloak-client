package ore.utils.keycloak.client.data;

import java.util.List;

public class KResourceDto extends BaseNamedKeycloakDto {
    public String icon_uri;
    public KResourceOwnerDto owner;
    public List<KPolicyDto> policies;
    public List<KScopeDto> scopes;
    public String type;
    public List<KScopeDto> typedScopes;
    public String uri;
}
package ore.utils.keycloak.client.data;

import java.util.List;

public class KResourceServerDto extends BaseNamedKeycloakDto {
    public static enum PolicyEnforcementMode { ENFORCING, PERMISSIVE, DISABLED }
    
    public Boolean allowRemoteResourceManagement;
    public String clientId;
    public List<KPolicyDto> policies;
    public PolicyEnforcementMode policyEnforcementMode;
    public List<KResourceDto> resources;
    public List<KScopeDto> scopes;
}
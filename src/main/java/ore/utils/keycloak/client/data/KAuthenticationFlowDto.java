package ore.utils.keycloak.client.data;

import java.util.List;

public class KAuthenticationFlowDto extends BaseAliasedKeycloakDto {
    public List<KAuthenticationExecutionExportDto> authenticationExecutions;
    public Boolean builtIn;
    public String description;
    public String providerId;
    public Boolean topLevel;
}
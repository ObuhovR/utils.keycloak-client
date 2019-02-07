package ore.utils.keycloak.client.data;

public class KAuthenticationExecutionExportDto extends BaseKeycloakDto {
    public String authenticator;
    public String authenticatorConfig;
    public Boolean authenticatorFlow;
    public Boolean autheticatorFlow;
    public String flowAlias;
    public Integer priority;
    public String requirement;
    public Boolean userSetupAllowed;
}
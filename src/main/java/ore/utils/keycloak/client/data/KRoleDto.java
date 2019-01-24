package ore.utils.keycloak.client.data;

public class KRoleDto extends BaseNamedKeycloakDto {
    public Boolean scopeParamRequired;
    public Boolean composite;
    public KRoleCompositesDto composites;
    public Boolean clientRole;
    public String containerId;
    public String description;
}
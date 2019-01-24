package ore.utils.keycloak.client.data;

import java.util.List;

public class KClientRoleMappingsDto extends BaseIdKeycloakDto {
    public String client;
    public List<KRoleDto> mappings;
}
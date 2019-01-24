package ore.utils.keycloak.client.data;

import java.util.List;
import java.util.Map;

public class KMappingsDto extends BaseKeycloakDto {
    public Map<String, KClientRoleMappingsDto> clientMappings;
    public List<KRoleDto> realmMappings;
}
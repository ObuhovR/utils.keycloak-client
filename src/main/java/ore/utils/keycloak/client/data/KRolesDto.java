package ore.utils.keycloak.client.data;

import java.util.List;
import java.util.Map;

public class KRolesDto extends BaseKeycloakDto {
    public Map<String, Object> client;
    public List<KRoleDto> realm;
}
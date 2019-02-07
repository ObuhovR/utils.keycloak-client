package ore.utils.keycloak.client.data;

import java.util.List;
import java.util.Map;

public class KGroupDto extends BaseNamedKeycloakDto {
    public Map<String, Object> access;
    public Map<String, Object> attributes;
    public Map<String, Object> clientRoles;
    public String path;
    public List<String> realmRoles;
    public List<KGroupDto> subGroups;
}
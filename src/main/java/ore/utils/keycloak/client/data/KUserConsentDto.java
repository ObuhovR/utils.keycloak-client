package ore.utils.keycloak.client.data;

import java.util.List;
import java.util.Map;

public class KUserConsentDto extends BaseKeycloakDto {
    public String clientId;
    public Long createdDate;
    public Map grantedClientRoles;
    public Map grantedProtocolMappers;
    public List<String> grantedRealmRoles;
    public Long lastUpdatedDate;
}
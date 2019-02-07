package ore.utils.keycloak.client.data;

import java.util.List;

public class KScopeMappingDto extends BaseKeycloakDto {
    public String client;
    public String clientTemplate;
    public List<String> roles;
    public String self;
}
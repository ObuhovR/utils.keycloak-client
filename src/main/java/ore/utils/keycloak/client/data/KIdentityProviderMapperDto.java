package ore.utils.keycloak.client.data;

import java.util.Map;

public class KIdentityProviderMapperDto extends BaseNamedKeycloakDto {
    public Map<String, Object> config;
    public String identityProviderAlias;
    public String identityProviderMapper;
}
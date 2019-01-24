package ore.utils.keycloak.client.data;

import java.util.Map;

public class KProtocolMapperDto extends BaseNamedKeycloakDto {
    public Map config;
    public Boolean consentRequired;
    public String consentText;
    public String protocol;
    public String protocolMapper;
}
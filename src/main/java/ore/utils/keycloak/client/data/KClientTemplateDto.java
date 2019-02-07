package ore.utils.keycloak.client.data;

import java.util.List;
import java.util.Map;

public class KClientTemplateDto extends BaseNamedKeycloakDto {
    public Map<String, Object> attributes;
    public Boolean bearerOnly;
    public Boolean consentRequired;
    public String description;
    public Boolean directAccessGrantsEnabled;
    public Boolean frontchannelLogout;
    public Boolean fullScopeAllowed;
    public Boolean implicitFlowEnabled;
    public String protocol;
    public List<KProtocolMapperDto> protocolMappers;
    public Boolean publicClient;
    public Boolean serviceAccountsEnabled;
    public Boolean standardFlowEnabled;
}
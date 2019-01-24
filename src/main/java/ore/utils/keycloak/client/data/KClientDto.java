package ore.utils.keycloak.client.data;

import java.util.List;
import java.util.Map;

public class KClientDto extends BaseNamedKeycloakDto {
    public Map access;
    public String adminUrl;
    public Map attributes;
    public Boolean authorizationServicesEnabled;
    public KResourceServerDto authorizationSettings;
    public String baseUrl;
    public Boolean bearerOnly;
    public String clientAuthenticatorType;
    public String clientId;
    public String clientTemplate;
    public Boolean consentRequired;
    public List<String> defaultRoles;
    public String description;
    public Boolean directAccessGrantsEnabled;
    public Boolean enabled;
    public Boolean frontchannelLogout;
    public Boolean fullScopeAllowed;
    public Boolean implicitFlowEnabled;
    public Integer nodeReRegistrationTimeout;
    public Integer notBefore;
    public String protocol;
    public List<KProtocolMapperDto> protocolMappers;
    public Boolean publicClient;
    public List<String> redirectUris;
    public Map registeredNodes;
    public String registrationAccessToken;
    public String rootUrl;
    public String secret;
    public Boolean serviceAccountsEnabled;
    public Boolean standardFlowEnabled;
    public Boolean surrogateAuthRequired;
    public Boolean useTemplateConfig;
    public Boolean useTemplateMappers;
    public Boolean useTemplateScope;
    public List<String> webOrigins;
}
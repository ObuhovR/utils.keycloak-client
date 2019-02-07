package ore.utils.keycloak.client.data;

import java.util.Map;

public class KIdentityProviderDto extends BaseKeycloakDto {
    public Boolean addReadTokenRoleOnCreate;
    public String alias;
    public Map<String, Object> config;
    public String displayName;
    public Boolean enabled;
    public String firstBrokerLoginFlowAlias;
    public String internalId;
    public Boolean linkOnly;
    public String postBrokerLoginFlowAlias;
    public String providerId;
    public Boolean storeToken;
    public Boolean trustEmail;
}
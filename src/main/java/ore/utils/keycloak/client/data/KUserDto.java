package ore.utils.keycloak.client.data;

import java.util.List;
import java.util.Map;

public class KUserDto extends BaseKeycloakDto {
    public String id;
    public String username;
    public String email;
    public Boolean emailVerified;
    public String firstName;
    public String lastName;
    public Map access;
    public Map attributes;
    public List<KUserConsentDto> clientConsents;
    public Map clientRoles;
    public Long createdTimestamp;
    public List<KCredentialDto> credentials;
    public List<String> disableableCredentialTypes;
    public Boolean enabled;
    public List<KFederatedIdentityDto> federatedIdentities;
    public String federationLink;
    public List<String> groups;
    public Integer notBefore;
    public String origin;
    public List<String> realmRoles;
    public List<String> requiredActions;
    public String self;
    public String serviceAccountClientId;
}
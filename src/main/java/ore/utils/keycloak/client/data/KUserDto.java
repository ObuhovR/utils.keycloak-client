package ore.utils.keycloak.client.data;

import java.beans.Transient;
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
    public Map<String, List<String>> attributes;
    public List<KUserConsentDto> clientConsents;
    public Map<String, List<KRoleDto>> clientRoles;
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
    
    /**
     * Returns list of values of attribute with given name
     * @param name attribute name
     * @return list of values of attribute with given name
     * @throws IllegalArgumentException if {@code name} is {@code null}
     */
    @Transient
    public List<String> getAttrsByName(String name) {
        if (name == null) throw new IllegalArgumentException("name is null");
        if (this.attributes == null) {
            return null;
        } else {
            return this.attributes.get(name);
        }
    }
    
    /**
     * Returns first "non-null" value of attribute with given name
     * @param name attribute name
     * @return first "non-null" value of attribute with given name
     * @throws IllegalArgumentException if {@code name} is {@code null}
     */
    @Transient
    public String getAttrByName(String name) {
        if (name == null) throw new IllegalArgumentException("name is null");
        List<String> attrs = getAttrsByName(name);
        if (attrs == null) {
            return null;
        } else {
            String result = null;
            
            for (String attr: attrs) {
                if (attr != null) {
                    result = attr;
                    break;
                }
            }
            
            return result;
        }
    }
}
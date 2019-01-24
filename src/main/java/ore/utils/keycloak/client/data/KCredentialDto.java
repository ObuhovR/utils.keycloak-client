package ore.utils.keycloak.client.data;

/**
 * <i>field "config" is skipped (I cannot understand schema of "MultivaluedHashMap")</i>
 */
public class KCredentialDto extends BaseKeycloakDto {
    public String algorithm;
    public Integer counter;
    public Long createdDate;
    public String device;
    public Integer digits;
    public Integer hashIterations;
    public String hashedSaltedValue;
    public Integer period;
    public String salt;
    public Boolean temporary;
    public String type;
    public String value;
}
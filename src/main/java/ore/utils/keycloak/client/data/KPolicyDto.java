package ore.utils.keycloak.client.data;

import java.util.List;
import java.util.Map;

public class KPolicyDto extends BaseNamedKeycloakDto {
    public static enum DecisionStrategy { AFFIRMATIVE, UNANIMOUS, CONSENSUS }
    public static enum Logic { POSITIVE, NEGATIVE }
    
    public Map config;
    public DecisionStrategy decisionStrategy;
    public String description;
    public Logic logic;
    public List<String> policies;
    public List<String> resources;
    public List<String> scopes;
    public String type;
}
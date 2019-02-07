package ore.utils.keycloak.client.data;

import java.util.Map;

public class KUserFederationProviderDto extends BaseIdKeycloakDto {
    public Integer changedSyncPeriod;
    public Map<String, Object> config;
    public String displayName;
    public Integer fullSyncPeriod;
    public Integer lastSync;
    public Integer priority;
    public String providerName;
}
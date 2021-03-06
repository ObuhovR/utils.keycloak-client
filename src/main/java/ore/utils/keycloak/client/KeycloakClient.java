package ore.utils.keycloak.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import static java.text.MessageFormat.format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ore.utils.keycloak.client.data.KAccessTokenDto;
import ore.utils.keycloak.client.data.KClientDto;
import ore.utils.keycloak.client.data.KRealm;
import ore.utils.keycloak.client.data.KRoleDto;
import ore.utils.keycloak.client.data.KUserDto;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

public class KeycloakClient {
    private final URL accessTokenUrl;
    private final URL baseAdminUrl;
    private final String username;
    private final String password;
    private final String clientKey;
    private final Charset charset;
    
    private volatile long accessTokenTime = Long.MIN_VALUE;
    private volatile String accessToken = null;
    private volatile int accessTokenExpiresIn = 0;
    
    private URL getUrl(String... parts) {
        try {
            return KeycloakClientEngine.concat(baseAdminUrl, parts);
        } catch (MalformedURLException exception) {
            throw new RuntimeException(
                    format("cannot create URL for parts {0}", Arrays.toString(parts)),
                    exception
            );
        }
    }
    
    private KAccessTokenDto loadAccessToken() throws IOException {
        List<NameValuePair> httpParameters = new ArrayList<>();
        httpParameters.add(new BasicNameValuePair("username", username));
        httpParameters.add(new BasicNameValuePair("password", password));
        httpParameters.add(new BasicNameValuePair("grant_type", "password"));
        httpParameters.add(new BasicNameValuePair("client_id", clientKey));
            
        HttpEntity requestEntity = new UrlEncodedFormEntity(httpParameters, StandardCharsets.UTF_8);
        
        HttpPost httpPost = new HttpPost(accessTokenUrl.toString());
        httpPost.setEntity(requestEntity);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        
        return KeycloakClientEngine.loadObject(KAccessTokenDto.class, httpPost, charset);
    }
    
    private String getAccessToken() throws IOException {
        synchronized (this) {
            if ((accessToken == null) || (System.currentTimeMillis() > accessTokenTime + accessTokenExpiresIn)) {
                long time = System.currentTimeMillis();
                
                KAccessTokenDto dto = loadAccessToken();
                
                int tempExpiresIn;
                if ((dto.expiresIn == null) || (dto.expiresIn < 0)) tempExpiresIn = 0;
                else tempExpiresIn = dto.expiresIn;
                
                accessToken = dto.accessToken;
                accessTokenTime = time;
                accessTokenExpiresIn = tempExpiresIn;
            }
            return accessToken;
        }
    }
    
    /**
     * 
     * @param host keycloak host name/IP
     * @param port keycloak port
     * @param username username which is used during authorization of this client in keycloak
     * @param password password which is used during authorization of this client in keycloak
     * @param clientKey client key which is used during authorization of this client in keycloak
     * @param charset charset used for encoding http requests and decoding http responses
     * @throws IllegalArgumentException one of:
     *   <ul>
     *     <li>{@code host} is {@code null} or empty</li>
     *     <li>{@code host} contains spaces at ends ({@code host}.{@link String#trim() trim())} is not equal {@code host})</li>
     *     <li>{@code port} is less than 1</li>
     *     <li>{@code port} is greater than 65535</li>
     *     <li>{@code username} is {@code null} or empty</li>
     *     <li>{@code password} is {@code null} or empty</li>
     *     <li>{@code clientKey} is {@code null} or empty</li>
     *     <li>{@code charset} is {@code null}</li>
     *     <li>cannot create Keycloak's base {@link URL URL} because of invalid {@code host} and/or {@code port}</li>
     *   </ul>
     */
    public KeycloakClient(String host, int port, String username, String password, String clientKey, Charset charset) {
        KeycloakClientBuilder.checkHost(host);
        KeycloakClientBuilder.checkPort(port);
        
        String accessTokenUrlSpec = format("http://{0}:{1}/auth/realms/master/protocol/openid-connect/token", host, "" + port);
        String baseAdminUrlSpec = format("http://{0}:{1}/auth/admin/realms", host, "" + port);
        
        try {
            this.accessTokenUrl = new URL(accessTokenUrlSpec);
            this.baseAdminUrl = new URL(baseAdminUrlSpec);
        } catch (MalformedURLException exception) {
            throw new IllegalArgumentException(
                    format("invalid host and/or port - cannot create URL \"{0}\"", baseAdminUrlSpec),
                    exception
            );
        }
        this.username = KeycloakClientBuilder.checkUsername(username);
        this.password = KeycloakClientBuilder.checkPassword(password);
        this.clientKey = KeycloakClientBuilder.checkClientKey(clientKey);
        this.charset = KeycloakClientBuilder.checkCharset(charset);
    }
    
    public URL getBaseAdminUrl() { return baseAdminUrl; }
    
    public String getUsername() { return username; }
    
    public String getPassword() { return password; }
    
    public String getClientKey() { return clientKey; }
    
    /**
     * <p>
     *   Returns string, loaded by GET request (may be json)
     * </p>
     * @param parts parts for URL creation, they will be added to base URL (which looks like "http://[host]:[port]/auth/admin/realms") with separation by '/'
     * @return string representation of keycloak response
     * @throws RuntimeException some runtime error (for example - URL cannot be created by given {@code parts})
     * @throws IOException some I/O error
     */
    public String getString(String... parts) throws IOException {
        URL url = getUrl(parts);
        return KeycloakClientEngine.loadString(KeycloakClientEngine.authorizedJsonGet(getAccessToken(), url), charset);
    }
    
    
    
    // Realm-oriented methods
    
    public List<KRealm> getRealms() throws IOException {
        URL url = getUrl();
        return KeycloakClientEngine.loadObjectByAuthorizedGet(KeycloakClientEngine.REALM_LIST, charset, getAccessToken(), url);
    }
    
    
    
    // Client-oriented methods
    
    public List<KClientDto> getClients(String realm) throws IOException {
        URL url = getUrl(realm, "clients");
        return KeycloakClientEngine.loadObjectByAuthorizedGet(
                KeycloakClientEngine.CLIENT_LIST,
                charset,
                getAccessToken(),
                url
        );
    }
    
    public KClientDto getClient(String realm, String key) throws IOException {
        return KeycloakClientEngine.loadObjectByAuthorizedGet(
                KClientDto.class,
                charset,
                getAccessToken(),
                getUrl(realm, "clients", key)
        );
    }
    
    /**
     * Returns map, where keys are "clientKey", and values are "clientId". Map may be empty; map will not contain nulls as keys or values (if not empty)
     * <br>
     * @param realm realm in which users search will be performed
     * @return map "clientKey"-&gt;"clientId"
     * @throws IOException some I/O error
     */
    public Map<String, String> getClientIdMap(String realm) throws IOException {
        Map<String, String> result = new HashMap<>();
        
        List<KClientDto> clients = getClients(realm);
        if (clients != null) {
            for (int i = 0; i < clients.size(); i++) {
                KClientDto client = clients.get(i);
                if (client != null) {
                    String name = client.clientId;
                    if (name != null) {
                        String value = client.id;
                        if (value != null) result.put(name, value);
                    }
                }
            }
        }
        
        return result;
    }
    
    
    
    // User-oriented methods
    
    public List<KUserDto> getUsers(String realm) throws IOException {
        URL url = getUrl(realm, "users");
        return KeycloakClientEngine.loadObjectByAuthorizedGet(KeycloakClientEngine.USER_LIST, charset, getAccessToken(), url);
    }
    
    public KUserDto getUser(String realm, String id) throws IOException {
        URL url = getUrl(realm, "users", id);
        return KeycloakClientEngine.loadObjectByAuthorizedGet(KUserDto.class, charset, getAccessToken(), url);
    }
    
    public List<KRoleDto> getUserRolesClientLevel(String realm, String userId, String clientId, boolean composite) throws IOException {
        URL url;
        if (composite) {
            url = getUrl(realm, "users", userId, "role-mappings", "clients", clientId, "composite");
        } else {
            url = getUrl(realm, "users", userId, "role-mappings", "clients", clientId);
        }
        return KeycloakClientEngine.loadObjectByAuthorizedGet(
                KeycloakClientEngine.ROLE_LIST,
                charset,
                getAccessToken(),
                url
        );
    }
}
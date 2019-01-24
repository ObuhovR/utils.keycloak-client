package ore.utils.keycloak.client;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import static java.text.MessageFormat.format;

public class KeycloakClientBuilder {
    private String host = null;
    private int port = -1;
    private String username = null;
    private String password = null;
    private String clientId = null;
    private Charset charset = StandardCharsets.UTF_8;
    
    /**
     * 
     * @param host
     * @return 
     * @throws IllegalArgumentException one of:
     *   <ul>
     *     <li>{@code host} is {@code null} or empty</li>
     *     <li>{@code host} contains spaces at ends ({@code host}.{@link String#trim() trim())} is not equal {@code host})</li>
     *   </ul>
     */
    public static String checkHost(String host) {
        if (host == null) throw new IllegalArgumentException("host is null");
        if (!host.trim().equals(host)) throw new IllegalArgumentException("host contains spaces at ends");
        if (host.isEmpty()) throw new IllegalArgumentException("host is empty");
        return host;
    }
    
    /**
     * 
     * @param port
     * @return 
     * @throws IllegalArgumentException one of:
     *   <ul>
     *     <li>{@code port} is less than 1</li>
     *     <li>{@code port} is greater than 65535</li>
     *   </ul>
     */
    public static int checkPort(int port) {
        if (port < 1) throw new IllegalArgumentException(format("port ({0}) is less than 1", port));
        if (port > 65535) throw new IllegalArgumentException(format("port ({0}) is greater than 65535", port));
        return port;
    }
    
    /**
     * 
     * @param username
     * @return 
     * @throws IllegalArgumentException {@code username} is {@code null} or empty
     */
    public static String checkUsername(String username) {
        if (username == null) throw new IllegalArgumentException("username is null");
        if (username.isEmpty()) throw new IllegalArgumentException("username is empty");
        return username;
    }
    
    /**
     * 
     * @param password
     * @return 
     * @throws IllegalArgumentException {@code password} is {@code null} or empty
     */
    public static String checkPassword(String password) {
        if (password == null) throw new IllegalArgumentException("password is null");
        if (password.isEmpty()) throw new IllegalArgumentException("password is empty");
        return password;
    }
    
    /**
     * 
     * @param clientId
     * @return 
     * @throws IllegalArgumentException {@code clientId} is {@code null} or empty
     */
    public static String checkClientId(String clientId) {
        if (clientId == null) throw new IllegalArgumentException("client ID is null");
        if (clientId.isEmpty()) throw new IllegalArgumentException("client ID is empty");
        return clientId;
    }
    
    /**
     * 
     * @param charset
     * @return 
     * @throws IllegalArgumentException {@code charset} is {@code null}
     */
    public static Charset checkCharset(Charset charset) {
        if (charset == null) throw new IllegalArgumentException("charset is null");
        return charset;
    }
    
    /**
     * Creates client builder with charset {@link StandardCharsets#UTF_8 UTF_8}
     */
    public KeycloakClientBuilder() {}
    
    public String getHost() { return host; }
    
    /**
     * 
     * @param host
     * @return 
     * @throws IllegalArgumentException one of:
     *   <ul>
     *     <li>{@code host} is {@code null} or empty</li>
     *     <li>{@code host} contains spaces at ends ({@code host}.{@link String#trim() trim())} is not equal {@code host})</li>
     *   </ul>
     */
    public KeycloakClientBuilder setHost(String host) {
        this.host = checkHost(host);
        return this;
    }
    
    public int getPort() { return port; }
    
    /**
     * 
     * @param port
     * @return 
     * @throws IllegalArgumentException one of:
     *   <ul>
     *     <li>{@code port} is less than 1</li>
     *     <li>{@code port} is greater than 65535</li>
     *   </ul>
     */
    public KeycloakClientBuilder setPort(int port) {
        this.port = checkPort(port);
        return this;
    }
    
    public String getUsername() { return username; }
    
    /**
     * 
     * @param username
     * @return 
     * @throws IllegalArgumentException {@code username} is {@code null} or empty
     */
    public KeycloakClientBuilder setUsername(String username) {
        this.username = checkUsername(username);
        return this;
    }
    
    public String getPassword() { return password; }
    
    /**
     * 
     * @param password
     * @return 
     * @throws IllegalArgumentException {@code password} is {@code null} or empty
     */
    public KeycloakClientBuilder setPassword(String password) {
        this.password = checkPassword(password);
        return this;
    }
    
    public String getClientId() { return clientId; }
    
    /**
     * 
     * @param clientId
     * @return 
     * @throws IllegalArgumentException {@code clientId} is {@code null} or empty
     */
    public KeycloakClientBuilder setClientId(String clientId) {
        this.clientId = checkClientId(clientId);
        return this;
    }
    
    public Charset getCharset() { return charset; }
    
    /**
     * 
     * @param charset
     * @return 
     * @throws IllegalArgumentException {@code charset} is {@code null}
     */
    public KeycloakClientBuilder setCharset(Charset charset) {
        this.charset = checkCharset(charset);
        return this;
    }
    
    /**
     * 
     * @return 
     * @throws IllegalArgumentException one of
     *   <ul>
     *     <li>{@link #getHost() host} is {@code null} or empty</li>
     *     <li>{@link #getHost() host} contains spaces at ends ({@code host}.{@link String#trim() trim())} is not equal {@code host})</li>
     *     <li>{@link #getPort()  port} is less than 1</li>
     *     <li>{@link #getPort()  port} is greater than 65535</li>
     *     <li>{@link #getUsername() username} is {@code null} or empty</li>
     *     <li>{@link #getPassword() password} is {@code null} or empty</li>
     *     <li>{@link #getClientId() clientId} is {@code null} or empty</li>
     *     <li>{@link #getCharset() charset} is {@code null}</li>
     *     <li>cannot create Keycloak's base {@link URL URL} because of invalid {@link #getHost() host} and/or {@link #getPort()  port}</li>
     *   </ul>
     */
    public KeycloakClient build() { return new KeycloakClient(host, port, username, password, clientId, charset); }
}
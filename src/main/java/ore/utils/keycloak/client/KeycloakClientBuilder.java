package ore.utils.keycloak.client;

import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import static java.text.MessageFormat.format;

public class KeycloakClientBuilder {
    private String host = null;
    private int port = -1;
    private String username = null;
    private String password = null;
    private String clientKey = null;
    private Charset charset = StandardCharsets.UTF_8;
    
    /**
     * 
     * @param host keycloak host
     * @return {@code host}
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
     * @param port keycloak port
     * @return {@code port}
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
     * @param username keycloak client's username
     * @return {@code username}
     * @throws IllegalArgumentException {@code username} is {@code null} or empty
     */
    public static String checkUsername(String username) {
        if (username == null) throw new IllegalArgumentException("username is null");
        if (username.isEmpty()) throw new IllegalArgumentException("username is empty");
        return username;
    }
    
    /**
     * 
     * @param password keycloak client's password
     * @return {@code password}
     * @throws IllegalArgumentException {@code password} is {@code null} or empty
     */
    public static String checkPassword(String password) {
        if (password == null) throw new IllegalArgumentException("password is null");
        if (password.isEmpty()) throw new IllegalArgumentException("password is empty");
        return password;
    }
    
    /**
     * 
     * @param clientKey keycloak client's key
     * @return {@code clientKey}
     * @throws IllegalArgumentException {@code clientKey} is {@code null} or empty
     */
    public static String checkClientKey(String clientKey) {
        if (clientKey == null) throw new IllegalArgumentException("client Key is null");
        if (clientKey.isEmpty()) throw new IllegalArgumentException("client Key is empty");
        return clientKey;
    }
    
    /**
     * 
     * @param charset charset for encoding/decoding http requests/responses
     * @return {@code charset}
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
     * @param host keycloak host
     * @return this builder
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
     * @param port keycloak port
     * @return this builder
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
     * @param username keycloak client's username
     * @return this builder
     * @throws IllegalArgumentException {@code username} is {@code null} or empty
     */
    public KeycloakClientBuilder setUsername(String username) {
        this.username = checkUsername(username);
        return this;
    }
    
    public String getPassword() { return password; }
    
    /**
     * 
     * @param password keycloak client's password
     * @return this builder
     * @throws IllegalArgumentException {@code password} is {@code null} or empty
     */
    public KeycloakClientBuilder setPassword(String password) {
        this.password = checkPassword(password);
        return this;
    }
    
    public String getClientKey() { return clientKey; }
    
    /**
     * 
     * @param clientKey keycloak client's key
     * @return this builder
     * @throws IllegalArgumentException {@code clientKey} is {@code null} or empty
     */
    public KeycloakClientBuilder setClientKey(String clientKey) {
        this.clientKey = checkClientKey(clientKey);
        return this;
    }
    
    public Charset getCharset() { return charset; }
    
    /**
     * 
     * @param charset charset for encoding/deconding http requests/responses
     * @return this builder
     * @throws IllegalArgumentException {@code charset} is {@code null}
     */
    public KeycloakClientBuilder setCharset(Charset charset) {
        this.charset = checkCharset(charset);
        return this;
    }
    
    /**
     * 
     * @return keycloak client
     * @throws IllegalArgumentException one of
     *   <ul>
     *     <li>{@link #getHost() host} is {@code null} or empty</li>
     *     <li>{@link #getHost() host} contains spaces at ends ({@code host}.{@link String#trim() trim())} is not equal {@code host})</li>
     *     <li>{@link #getPort()  port} is less than 1</li>
     *     <li>{@link #getPort()  port} is greater than 65535</li>
     *     <li>{@link #getUsername() username} is {@code null} or empty</li>
     *     <li>{@link #getPassword() password} is {@code null} or empty</li>
     *     <li>{@link #getClientKey() clientKey} is {@code null} or empty</li>
     *     <li>{@link #getCharset() charset} is {@code null}</li>
     *     <li>cannot create Keycloak's base {@link URL URL} because of invalid {@link #getHost() host} and/or {@link #getPort()  port}</li>
     *   </ul>
     */
    public KeycloakClient build() { return new KeycloakClient(host, port, username, password, clientKey, charset); }
}
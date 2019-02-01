package ore.utils.keycloak.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import static java.text.MessageFormat.format;
import java.util.Arrays;
import java.util.List;
import ore.utils.keycloak.client.adapters.AccessTokenTypeAdapter;
import ore.utils.keycloak.client.data.KAccessTokenDto;
import ore.utils.keycloak.client.data.KClientDto;
import ore.utils.keycloak.client.data.KRoleDto;
import ore.utils.keycloak.client.data.KUserDto;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

class KeycloakClientEngine {
    private static final Gson GSON = new GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .registerTypeAdapter(KAccessTokenDto.class, new AccessTokenTypeAdapter())
            .create();
    
    public static final Type CLIENT_LIST = new TypeToken<List<KClientDto>>(){}.getType();
    public static final Type ROLE_LIST = new TypeToken<List<KRoleDto>>(){}.getType();
    public static final Type USER_LIST = new TypeToken<List<KUserDto>>(){}.getType();
    
    /**
     * Adds every element of {@code parts} to {@code baseUrl} with separator {@code '/'}
     * @param baseUrl
     * @param parts
     * @return
     * @throws MalformedURLException 
     */
    public static URL concat(URL baseUrl, String... parts) throws MalformedURLException {
        if (parts == null) throw new IllegalArgumentException("part array is null");
        if (parts.length == 0) return baseUrl;
        
        URL result = baseUrl;
        for (int i = 0; i < parts.length; i++) result = new URL(result.toString() + '/' + parts[i]);
        return result;
    }
    
    /**
     * 
     * @param accessToken
     * @param url
     * @return 
     * @throws IllegalArgumentException if one of:
     *   <ul>
     *     <li>{@code accessToken} is {@code null}</li>
     *     <li>{@code url} is {@code null}</li>
     *   </ul>
     */
    public static HttpGet authorizedJsonGet(String accessToken, URL url) {
        if (accessToken == null) throw new IllegalArgumentException("access token is null");
        if (url == null) throw new IllegalArgumentException("url is null");
        
        HttpGet result = new HttpGet(url.toString());
        result.setHeader("Accept", "application/json");
        result.setHeader("Authorization", format("Bearer {0}", accessToken));
        return result;
    }
    
    /**
     * 
     * @param request
     * @return 
     * @throws IOException any I/O error
     * @throws IllegalArgumentException if {@code request} is {@code null}
     */
    public static byte[] loadBytes(HttpUriRequest request) throws IOException {
        if (request == null) throw new IllegalArgumentException("request is null");
        
        byte[] bytes;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response == null) throw new IOException("response is null");
                
                StatusLine statusLine = response.getStatusLine();
                if (statusLine == null) throw new IOException("status line of response is null");
                
                int status = statusLine.getStatusCode();
                if (status != 200) {
                    String reason = statusLine.getReasonPhrase();
                    
                    String message;
                    if ((reason == null) || (reason.trim().isEmpty())) {
                        message = format("HTTP error at \"{0}\" - status {1}", request.getURI(), status);
                    } else {
                        message = format("HTTP error at \"{0}\" - status {1}, reason \"{2}\"", request.getURI(), status, reason);
                    }
                    
                    if (status == 404) throw new KeycloakNotFoundException(message);
                    else throw new IOException(message);
                }
                
                HttpEntity entity = response.getEntity();
                if (entity == null) throw new IOException("response does not contain entity");
                
                InputStream inputStream = entity.getContent();
                if (inputStream == null) throw new IOException("response's entity does not contain content");
                
                try {
                    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                        byte[] buffer = new byte[1024];
                        int read;
                        while ((read = inputStream.read(buffer)) != -1) outputStream.write(buffer, 0, read);
                        bytes = outputStream.toByteArray();
                    }
                } finally {
                    inputStream.close();
                }
            }
        }
        return bytes;
    }
    
    /**
     * 
     * @param request
     * @param charset 
     * @return
     * @throws IOException any I/O error
     * @throws IllegalArgumentException if one of:
     *   <ul>
     *     <li>{@code request} is {@code null}</li>
     *     <li>{@code charset} is {@code null}</li>
     *   </ul>
     */
    public static String loadString(HttpUriRequest request, Charset charset) throws IOException {
        if (charset == null) throw new IllegalArgumentException("charset is null");
        return new String(loadBytes(request), charset);
    }
    
    /**
     * 
     * @param <T>
     * @param resultClass
     * @param request
     * @param charset
     * @return
     * @throws IOException any I/O error
     * @throws IllegalArgumentException one of:
     *   <ul>
     *     <li>{@code resultClass} is {@code null}</li>
     *     <li>{@code request} is {@code null}</li>
     *     <li>{@code charset} is {@code null}</li>
     *   </ul>
     */
    public static <T> T loadObject(Class<T> resultClass, HttpUriRequest request, Charset charset) throws IOException {
        if (resultClass == null) throw new IllegalArgumentException("result class is null");
        if (request == null) throw new IllegalArgumentException("request is null");
        if (charset == null) throw new IllegalArgumentException("charset is null");
        
        String json = loadString(request, charset);
        return GSON.fromJson(json, resultClass);
    }
    
    /**
     * 
     * @param <T>
     * @param resultType
     * @param request
     * @param charset
     * @return
     * @throws IOException any I/O error
     * @throws IllegalArgumentException one of:
     *   <ul>
     *     <li>{@code resultType} is {@code null}</li>
     *     <li>{@code request} is {@code null}</li>
     *     <li>{@code charset} is {@code null}</li>
     *   </ul>
     */
    public static <T> T loadObject(Type resultType, HttpUriRequest request, Charset charset) throws IOException {
        if (resultType == null) throw new IllegalArgumentException("result type is null");
        if (request == null) throw new IllegalArgumentException("request is null");
        if (charset == null) throw new IllegalArgumentException("charset is null");
        
        String json = loadString(request, charset);
        return GSON.fromJson(json, resultType);
    }
    
    /**
     * 
     * @param <T>
     * @param resultClass
     * @param charset
     * @param accessToken
     * @param url
     * @return
     * @throws IOException any I/O error
     * @throws IllegalArgumentException if one of:
     *   <ul>
     *     <li>{@code resultClass} is {@code null}</li>
     *     <li>{@code charset} is {@code null}</li>
     *     <li>{@code accessToken} is {@code null}</li>
     *     <li>{@code url} is {@code null}</li>
     *   </ul>
     */
    public static <T> T loadObjectByAuthorizedGet(Class<T> resultClass, Charset charset, String accessToken, URL url) throws IOException {
        if (resultClass == null) throw new IllegalArgumentException("result class is null");
        if (charset == null) throw new IllegalArgumentException("charset is null");
        if (accessToken == null) throw new IllegalArgumentException("access token is null");
        if (url == null) throw new IllegalArgumentException("url is null");
        
        return loadObject(resultClass, authorizedJsonGet(accessToken, url), charset);
    }
    
    /**
     * 
     * @param <T>
     * @param resultType
     * @param charset
     * @param accessToken
     * @param url
     * @return
     * @throws IOException any I/O error
     * @throws IllegalArgumentException if one of:
     *   <ul>
     *     <li>{@code resultType} is {@code null}</li>
     *     <li>{@code charset} is {@code null}</li>
     *     <li>{@code accessToken} is {@code null}</li>
     *     <li>{@code url} is {@code null}</li>
     *   </ul>
     */
    public static <T> T loadObjectByAuthorizedGet(Type resultType, Charset charset, String accessToken, URL url) throws IOException {
        if (resultType == null) throw new IllegalArgumentException("result type is null");
        if (charset == null) throw new IllegalArgumentException("charset is null");
        if (accessToken == null) throw new IllegalArgumentException("access token is null");
        if (url == null) throw new IllegalArgumentException("url is null");
        
        return loadObject(resultType, authorizedJsonGet(accessToken, url), charset);
    }
}
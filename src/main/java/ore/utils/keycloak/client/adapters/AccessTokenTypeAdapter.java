package ore.utils.keycloak.client.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sun.org.apache.bcel.internal.Constants;
import java.io.IOException;
import static java.text.MessageFormat.format;
import ore.utils.keycloak.client.data.KAccessTokenDto;

public class AccessTokenTypeAdapter extends TypeAdapter<KAccessTokenDto> {
    private static final String ACCESS_TOKEN = "access_token";
    private static final String EXPIRES_IN = "expires_in";
    private static final String REFRESH_EXPIRES_IN = "refresh_expires_in";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String TOKEN_TYPE = "token_type";
    private static final String ID_TOKEN = "id_token";
    private static final String NOT_BEFORE_POLICY = "not-before-policy";
    private static final String SESSION_STATE = "session_state";
    
    @Override
    public void write(JsonWriter writer, KAccessTokenDto value) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public KAccessTokenDto read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) return null;
        else {
            reader.beginObject();
            
            String accessToken = null;
            Integer expiresIn = null;
            String refreshToken = null;
            Integer refreshExpiresIn = null;
            String tokenType = null;
            String idToken = null;
            Integer notBeforePolicy = null;
            String sessionState = null;
            
            while (reader.peek() == JsonToken.NAME) {
                String name = reader.nextName();
                switch (name) {
                    case ACCESS_TOKEN:
                        accessToken = reader.nextString();
                        break;
                    case EXPIRES_IN:
                        expiresIn = reader.nextInt();
                        break;
                    case REFRESH_TOKEN:
                        refreshToken = reader.nextString();
                        break;
                    case REFRESH_EXPIRES_IN:
                        refreshExpiresIn = reader.nextInt();
                        break;
                    case TOKEN_TYPE:
                        tokenType = reader.nextString();
                        break;
                    case ID_TOKEN:
                        idToken = reader.nextString();
                        break;
                    case NOT_BEFORE_POLICY:
                        notBeforePolicy = reader.nextInt();
                        break;
                    case SESSION_STATE:
                        sessionState = reader.nextString();
                        break;
                }
            }
            
            if ((accessToken == null) || accessToken.isEmpty()) {
                throw new IllegalStateException(format("required field \"{0}\" is missed or empty", ACCESS_TOKEN));
            }
            
            reader.endObject();
            
            KAccessTokenDto result = new KAccessTokenDto();
            result.accessToken = accessToken;
            result.expiresIn = expiresIn;
            result.refreshToken = refreshToken;
            result.refreshExpiresIn = refreshExpiresIn;
            result.tokenType = tokenType;
            result.idToken = idToken;
            result.notBeforePolicy = notBeforePolicy;
            result.sessionState = sessionState;
            return result;
        }
    }
}
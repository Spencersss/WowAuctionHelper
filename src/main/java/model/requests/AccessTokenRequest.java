package model.requests;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.AccessToken;
import front.Main;
import okhttp3.*;
import okhttp3.Request;

import java.util.Objects;

public class AccessTokenRequest implements ApiRequest{

    private final OkHttpClient client;
    private AccessToken token;
    private Response response;

    public AccessTokenRequest(OkHttpClient reqClient) {
        this.client = reqClient;
        this.token = null;
        this.response = null;
        this.execute();
    }

    public boolean wasSuccessful() {
        return (token != null);
    }
    public Response getResponse() {
        return this.response;
    }

    public AccessToken getToken() {
        return this.token;
    }

    private void parseToken(Response response) {
        if (response != null) {
            try {
                JsonObject resp = JsonParser.parseString(
                        Objects.requireNonNull(response.body()).string()
                ).getAsJsonObject();
                this.token = new AccessToken(resp.get("access_token").toString());
            } catch (Exception e) {
                e.getStackTrace();
            }
        } else {
            Main.printWithPrefix("Cannot parse token!");
        }
    }

    public void execute() {
        RequestBody body = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .add("client_id", "2b6994390db3470db593eee9e4a96655")
                .add("client_secret", "H7qe5v7SHv9Ud4JEhPrptxDrx3U19f7n")
                .build();


        Request getAccessToken = new Request.Builder()
                .url("https://us.battle.net/oauth/token")
                .post(body)
                .build();

        try {
            Call request = client.newCall(getAccessToken);
            response = request.execute();
            parseToken(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

package model.requests;

import front.Main;
import model.AccessToken;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AuctionHouseRequest implements ApiRequest{

    private final OkHttpClient client;
    private final AccessToken token;
    private Response response;

    public AuctionHouseRequest (OkHttpClient reqClient, AccessToken token) {
        this.client = reqClient;
        this.token = token;
        this.response = null;
        execute();
    }

    public boolean wasSuccessful() {
        return (response != null);
    }

    public AccessToken getToken() {
        return token;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public Response getResponse() {
        return response;
    }

    @Override
    public void execute() {
        String reqUrl = "https://us.api.blizzard.com/data/wow/connected-realm/3693/auctions?namespace=dynamic-us&locale=en_US&access_token=" + getToken().getValue();
        Request request = new Request.Builder()
                .url(reqUrl)
                .build();
        Main.printWithPrefix("Getting auction house data...");
        try {
            Call dataCall = getClient().newCall(request);
            response = dataCall.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

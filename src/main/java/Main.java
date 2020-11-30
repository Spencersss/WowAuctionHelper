import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

public class Main {

    public static String LOGGER_PREFIX = "[ChakaWowHelper] ";

    public static void printWithPrefix(String text) {
        System.out.println(LOGGER_PREFIX + text);
    };

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .add("client_id", "2b6994390db3470db593eee9e4a96655")
                .add("client_secret", "H7qe5v7SHv9Ud4JEhPrptxDrx3U19f7n")
                .build();

        Request getAccessToken = new Request.Builder()
                .url("https://us.battle.net/oauth/token")
                .post(body)
                .build();

        Response response = null;
        printWithPrefix("Getting access token...");
        try {
            Call request = client.newCall(getAccessToken);
            response = request.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (response != null) {
            try {
                JsonObject resp = JsonParser.parseString(response.body().string()).getAsJsonObject();
                new LogicHandler(resp.get("access_token").toString(), client);
            } catch (Exception e) {
                e.getStackTrace();
            }
        } else {
            // handle error...
        }
    }

}

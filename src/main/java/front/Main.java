package front;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.AccessToken;
import okhttp3.*;
import model.requests.AccessTokenRequest;

import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {

    public static String LOGGER_PREFIX = "";

    public static void printWithPrefix(String text) {
        System.out.println(LOGGER_PREFIX + " " + text);
    };

    private static void readConfig() {
        InputStream input = ClassLoader.getSystemResourceAsStream("config.json");
        if (input != null) {
            JsonObject config = JsonParser.parseReader(new InputStreamReader(input)).getAsJsonObject();
            LOGGER_PREFIX = config.get("loggerPrefix").getAsString();
        }
    }

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        readConfig();

        AccessTokenRequest req = new AccessTokenRequest(client);
        if (req.wasSuccessful()) {
            Response response = req.getResponse();
            AccessToken token = req.getToken();

            Main.printWithPrefix(token.getValue());
        }
        //new LogicHandler(, client);
    }

}

package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import front.Main;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AuctionHouseData {

    private HashMap<Integer, List<JsonObject>> auctions;
    private JsonObject rawData = null;

    public AuctionHouseData (Response response) {
        this.auctions = new HashMap<>();
        convertResponse(response);
        populateAuctions(rawData);
    }

    private void convertResponse(Response response) {
        try {
            rawData = JsonParser.parseString(Objects.requireNonNull(response.body()).string()).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            //return null;
        }
    }

    public void auctionsExist(Integer id) {
        if (this.auctions.get(id) == null) {
            List<JsonObject> itemAuctions = new ArrayList<JsonObject>();
            this.auctions.put(id, itemAuctions);
        }
    }

    private void addAuction(Integer id, JsonObject auction) {
        auctionsExist(id);
        this.auctions.get(id).add(auction);
    }

    private List<JsonObject> getAuctions(Integer id) {
        return this.auctions.get(id);
    }

    void getMinItemPrice(Integer id) {
        List<JsonObject> aucs = getAuctions(id);
        double currentMin = Double.MAX_VALUE;
        for (JsonObject auction : aucs) {
            double checkValue = 0;
            if (auction.has("buyout")) {
                checkValue = Double.parseDouble(auction.get("buyout").toString());
            } else if (auction.has("unit_price")) {
                checkValue = Double.parseDouble(auction.get("unit_price").toString());
            }
            double goldUnitPrice = Math.max(0, checkValue / 10000);

            if (goldUnitPrice < currentMin) {
                currentMin = goldUnitPrice;
            }
        }
    }

    void printAllAuctions() {
        for (Integer item : auctions.keySet()) {
            List<JsonObject> auctions = getAuctions(item);
            Main.printWithPrefix("Item ID: " + item);
            for (JsonObject auction : auctions) {
                Main.printWithPrefix("   " + auction.toString());
            }
        }
    }

    void populateAuctions(JsonObject rawJson) {
        JsonArray auctions = rawJson.get("auctions").getAsJsonArray();
        for (JsonElement post : auctions) {
            JsonObject postObj = post.getAsJsonObject();
            int id = Integer.parseInt(postObj.get("item").getAsJsonObject().get("id").toString());
            addAuction(id, postObj);
        }
        //printAllAuctions();
    }

}

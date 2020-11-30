import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicHandler {

    /*

    Nightshade ID = 171315,
    Rising Glory ID = 168586,
    Marrowroot ID = 168589,
    Widowbloom ID = 168583,
    Vigil's Torch = 170554,

    Flask = 171276

     */

    private final Integer REALM_ID = 3693;
    private final String ACCESS_TOKEN;
    private final OkHttpClient client;

    private Map<Integer, Double> minIngrtPrices;

    public LogicHandler(String accesstoken, OkHttpClient client) {
        this.ACCESS_TOKEN = accesstoken.replaceAll("^\"+|\"+$", "");
        this.minIngrtPrices = new HashMap<>();
        this.client = client;
        this.getAuctionHouseData();
    }

    public OkHttpClient getClient() {
        return client;
    }

    public String getACCESS_TOKEN() {
        return ACCESS_TOKEN;
    }

    void getAuctionHouseData() {
        String reqUrl = "https://us.api.blizzard.com/data/wow/connected-realm/3693/auctions?namespace=dynamic-us&locale=en_US&access_token="
                + this.getACCESS_TOKEN().toString();

        Request request = new Request.Builder()
                .url(reqUrl)
                .build();
        Response response = null;
        Main.printWithPrefix("Getting auction house data...");
        try {
            Call dataCall = getClient().newCall(request);
            response = dataCall.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // namespace=dynamic-us&locale=en_US&access_token=USPYEDMRZbC6ow9zbvQteX1C8XESEzjcij

        if (response != null) {
            try {
                JsonObject resp = JsonParser.parseString(response.body().string()).getAsJsonObject();
                exploreData(resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // handle error...
        }
    }

    void displayFlaskOfPower() {
        double flaskTotalPrice = getMinPrice(171315) * 3.0 + getMinPrice(168586) * 4.0 + getMinPrice(168589)
                * 4.0 + getMinPrice(168583) * 4.0 + getMinPrice(170554) * 4.0;
        double flaskMinPrice = getMinPrice(171276);
        double profitMargin = flaskMinPrice - flaskTotalPrice;
        Main.printWithPrefix("-------------------------");
        Main.printWithPrefix("Nightshade Min Price: " + getMinPrice(171315));
        Main.printWithPrefix("Rising Glory Min Price: " + getMinPrice(168586));
        Main.printWithPrefix("Marrowroot Min Price: " + getMinPrice(168589));
        Main.printWithPrefix("Widowbloom Min Price: " + getMinPrice(168583));
        Main.printWithPrefix("Vigil's Torch Min Price: " + getMinPrice(170554));
        Main.printWithPrefix("-------------------------");
        Main.printWithPrefix("Flask of Power Crafting Cost: " + flaskTotalPrice);
        Main.printWithPrefix("Flask of Power Min Price: " + flaskMinPrice);
        Main.printWithPrefix("Profit Margin: " + (profitMargin < 0.0 ? "" : "+") + profitMargin);
        Main.printWithPrefix("");
    }

    void displayEnchantSet1() {
        // 172231
        double sacredShardMinPrice = getMinPrice(172231);
        Main.printWithPrefix("-------------------------");
        Main.printWithPrefix("Sacred Shard Min Price: " + sacredShardMinPrice);
        Main.printWithPrefix("-------------------------");
        Main.printWithPrefix("Tenet Crit Strike Enchant Crafting Cost: " + sacredShardMinPrice * 4.0);
        Main.printWithPrefix("Tenet Crit Strike Enchant Min Price: " + getMinPrice(172361));
        double profitMargin1 = getMinPrice(172361) - (sacredShardMinPrice * 4.0);
        Main.printWithPrefix("Profit Margin: " + (profitMargin1 < 0.0 ? "" : "+") + profitMargin1);
        Main.printWithPrefix("-------------------------");
        Main.printWithPrefix("Tenet Haste Enchant Crafting Cost: " + sacredShardMinPrice * 4.0);
        Main.printWithPrefix("Tenet Haste Enchant Min Price: " + getMinPrice(172362));
        double profitMargin2 = getMinPrice(172362) - (sacredShardMinPrice * 4.0);
        Main.printWithPrefix("Profit Margin: " + (profitMargin2 < 0.0 ? "" : "+") + profitMargin2);
        Main.printWithPrefix("-------------------------");
        Main.printWithPrefix("Tenet Mastery Enchant Crafting Cost: " + sacredShardMinPrice * 4.0);
        Main.printWithPrefix("Tenet Mastery Enchant Min Price: " + getMinPrice(172363));
        double profitMargin3 = getMinPrice(172363) - (sacredShardMinPrice * 4.0);
        Main.printWithPrefix("Profit Margin: " + (profitMargin3 < 0.0 ? "" : "+") + profitMargin3);
        Main.printWithPrefix("-------------------------");
        Main.printWithPrefix("Tenet Versatility Enchant Crafting Cost: " + sacredShardMinPrice * 4.0);
        Main.printWithPrefix("Tenet Versatility Enchant Min Price: " + getMinPrice(172364));
        double profitMargin4 = getMinPrice(172364) - (sacredShardMinPrice * 4.0);
        Main.printWithPrefix("Profit Margin: " + (profitMargin4 < 0.0 ? "" : "+") + profitMargin4);
        Main.printWithPrefix("");
    }

    void displayEnchantSet2() {
        double sacredShardMinPrice = getMinPrice(172231);
        double crystalMinPrice = getMinPrice(172232);
        double totalCost = (sacredShardMinPrice * 3.0) + (crystalMinPrice * 2.0);
        Main.printWithPrefix("-------------------------");
        Main.printWithPrefix("Sacred Shard Min Price: " + sacredShardMinPrice);
        Main.printWithPrefix("Eternal Crystal Min Price: " + crystalMinPrice);
        Main.printWithPrefix("-------------------------");
        Main.printWithPrefix("Ascended Vigour Enchant Crafting Cost: " + totalCost);
        Main.printWithPrefix("Ascended Vigour Enchant Min Price: " + getMinPrice(172365));
        double profitMargin1 = getMinPrice(172365) - totalCost;
        Main.printWithPrefix("Profit Margin: " + (profitMargin1 < 0.0 ? "" : "+") + profitMargin1);
        Main.printWithPrefix("-------------------------");
        Main.printWithPrefix("Eternal Grace Enchant Crafting Cost: " + totalCost);
        Main.printWithPrefix("Eternal Grace Enchant Min Price: " + getMinPrice(172367));
        double profitMargin2 = getMinPrice(172367) - totalCost;
        Main.printWithPrefix("Profit Margin: " + (profitMargin2 < 0.0 ? "" : "+") + profitMargin2);
        Main.printWithPrefix("-------------------------");
        Main.printWithPrefix("Lightless Force Enchant Crafting Cost: " + totalCost);
        Main.printWithPrefix("ALightless Force Enchant Min Price: " + getMinPrice(172370));
        double profitMargin3 = getMinPrice(172370) - totalCost;
        Main.printWithPrefix("Profit Margin: " + (profitMargin3 < 0.0 ? "" : "+") + profitMargin3);
        Main.printWithPrefix("-------------------------");
        Main.printWithPrefix("Sinful Revelation Enchant Crafting Cost: " + totalCost);
        Main.printWithPrefix("Sinful Revelation Enchant Min Price: " + getMinPrice(172368));
        double profitMargin4 = getMinPrice(172368) - totalCost;
        Main.printWithPrefix("Profit Margin: " + (profitMargin1 < 0.0 ? "" : "+") + profitMargin1);
        Main.printWithPrefix("");
    };

    void setMinPrice(Integer id, double value) {
        this.minIngrtPrices.put(id, value);
    }

    Double getMinPrice(Integer id) {
        return this.minIngrtPrices.get(id);
    }

    void handleValueCheck(Integer item_id, Long unit_price) {
        double goldUnitPrice = Math.max(0, (double) unit_price / 10000);

        if (getMinPrice(item_id) == null) {
            setMinPrice(item_id, Double.MAX_VALUE);
        }

        double currentPrice = getMinPrice(item_id);

        if (goldUnitPrice < currentPrice) {
            setMinPrice(item_id, goldUnitPrice);
        }
    }

    void exploreData(JsonObject rawJson) {
        JsonArray auctions = rawJson.get("auctions").getAsJsonArray();
        for (JsonElement post : auctions) {
            JsonObject postObj = post.getAsJsonObject();
            int id = Integer.parseInt(postObj.get("item").getAsJsonObject().get("id").toString());
            // Nightshade, rising, marrow, widow, vigils
            if (postObj.has("buyout")) {
                this.handleValueCheck(id, Long.parseLong(postObj.get("buyout").toString()));
            } else if (postObj.has("unit_price")) {
                this.handleValueCheck(id, Long.parseLong(postObj.get("unit_price").toString()));
            }
        }
        displayFlaskOfPower();
        displayEnchantSet1();
        displayEnchantSet2();
    }

}

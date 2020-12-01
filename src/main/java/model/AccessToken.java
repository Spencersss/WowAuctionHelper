package model;

public class AccessToken {

    private final String value;
    private final long expiryTime;

    public AccessToken (String tokenValue) {
        this.value = tokenValue.replaceAll("^\"+|\"+$", "");
        this.expiryTime = System.currentTimeMillis();
    }

    public String getValue() {
        return this.value;
    }

}

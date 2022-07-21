package util;

public enum HttpHeaderKey {
    REQUEST("request"),
    CONTENT_LENGTH("Content-Length"),
    BODY("body");

    private String key;

    HttpHeaderKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}

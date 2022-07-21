package util;

public enum Url {
    INDEX_FORM("/index.html"),
    REGISTER_FORM("/user/form.html"),
    REGISTER("/user/create");

    private final String url;

    Url(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

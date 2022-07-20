package util;

public enum URL {
    INDEX_FORM("/index.html"),
    REGISTER_FORM("/user/form.html"),
    REGISTER("/user/create");

    private final String url;

    URL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

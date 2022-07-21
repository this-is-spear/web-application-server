package util;

public enum Url {
    INDEX_FORM("/index.html"),
    REGISTER_FORM("/user/form.html"),
    REGISTER("/user/create");
    private static final String QUERY = "?";
    private final String url;

    Url(final String url) {
        this.url = url;
    }


    public boolean is(final String url) {
        if (url.contains(QUERY)) {
            return this.url.equals(url.substring(0, url.indexOf(QUERY)));
        }
        return this.url.equals(url);
    }
}

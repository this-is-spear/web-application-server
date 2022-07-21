package util;

public enum Url {
    INDEX_PAGE("/index.html"),
    REGISTER_PAGE("/user/form.html"),
    REGISTER("/user/create"),
    LOGIN_PAGE("/user/login.html"),
    LOGIN("/user/login"),
    LOGIN_FAILED_PAGE("/user/login_failed.html"),
    USER_LIST_PAGE("/user/list.html"),
    USER_LIST("/user/list");
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

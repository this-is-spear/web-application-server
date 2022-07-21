package util;

public enum HttpMethod {
    GET,
    POST;

    public boolean is(String method) {
        return this.name().equals(method);
    }
}

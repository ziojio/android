package zhuj.http;

public final class HttpMethod {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public static final String HEAD = "HEAD";
    public static final String PATCH = "PATCH";
    public static final String OPTIONS = "OPTIONS";
    public static final String TRACE = "TRACE";

    public static boolean invalidatesCache(String method) {
        return method.equals("POST")
                || method.equals("PUT") || method.equals("DELETE")
                || method.equals("PATCH")
                || method.equals("MOVE");     // WebDAV
    }

    public static boolean requiresRequestBody(String method) {
        return method.equals("POST")
                || method.equals("PUT")
                || method.equals("PATCH")
                || method.equals("PROPPATCH") // WebDAV
                || method.equals("REPORT");   // CalDAV/CardDAV (defined in WebDAV Versioning)
    }

    public static boolean permitsRequestBody(String method) {
        return !(method.equals("GET") || method.equals("HEAD"));
    }

    public static boolean redirectsWithBody(String method) {
        return method.equals("PROPFIND"); // (WebDAV) redirects should also maintain the request body
    }

    public static boolean redirectsToGet(String method) {
        // All requests but PROPFIND should redirect to a GET request.
        return !method.equals("PROPFIND");
    }

    private HttpMethod() {
    }
}
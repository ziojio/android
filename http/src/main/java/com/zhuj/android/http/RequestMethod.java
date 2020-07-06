package com.zhuj.android.http;

public enum RequestMethod {
    GET,
    POST,
    PUT,
    DELETE,
    HEAD,
    PATCH,
    OPTIONS,
    TRACE;

    public boolean allowBody() {
        switch (this) {
            case POST:
            case PUT:
            case PATCH:
            case DELETE:
                return true;
            default:
                return false;
        }
    }

    public static RequestMethod reverse(String method) {
        method = method.toUpperCase();
        switch (method) {
            case "POST": {
                return POST;
            }
            case "PUT": {
                return PUT;
            }
            case "DELETE": {
                return DELETE;
            }
            case "HEAD": {
                return HEAD;
            }
            case "PATCH": {
                return PATCH;
            }
            case "OPTIONS": {
                return OPTIONS;
            }
            case "TRACE": {
                return TRACE;
            }
            default: {
                return GET;
            }
        }
    }

}

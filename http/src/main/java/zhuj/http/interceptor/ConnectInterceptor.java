// package zhuj.http.interceptor;
//
//
// import zhuj.http.RequestMethod;
// import zhuj.http.request.Request;
// import zhuj.http.request.body.RequestBody;
// import zhuj.http.response.Response;
// import zhuj.java.file.IOUtils;
//
// import java.io.IOException;
// import java.io.OutputStream;
// import java.net.ConnectException;
// import java.net.MalformedURLException;
// import java.net.SocketTimeoutException;
// import java.net.URI;
// import java.net.URISyntaxException;
// import java.net.UnknownHostException;
// import java.util.List;
// import java.util.Map;
// import java.util.concurrent.CancellationException;
//
// import okhttp3.Headers;
// import okhttp3.ResponseBody;
//
// /**
//  * Created by Zhenjie Yan on 2018/2/20.
//  */
// class ConnectInterceptor implements Interceptor {
//
//     private final CookieManager mCookieManager;
//     private final ConnectFactory mFactory;
//     private final Network mNetwork;
//
//     private Connection mConnection;
//     private boolean isCanceled;
//
//     ConnectInterceptor() {
//         this.mCookieManager = new CookieManager(Kalle.getConfig().getCookieStore());
//         this.mFactory = Kalle.getConfig().getConnectFactory();
//         this.mNetwork = Kalle.getConfig().getNetwork();
//     }
//
//     @Override
//     public Response intercept(Chain chain) throws IOException {
//         if (isCanceled) throw new CancellationException("The request has been cancelled.");
//
//         Request request = chain.request();
//         RequestMethod method = request.method();
//
//         if (method.allowBody()) {
//             Headers headers = request.headers();
//             RequestBody body = request.body();
//             headers.set(KEY_CONTENT_LENGTH, Long.toString(body.contentLength()));
//             headers.set(KEY_CONTENT_TYPE, body.contentType());
//             mConnection = connect(request);
//             writeBody(body);
//         } else {
//             mConnection = connect(request);
//         }
//         return readResponse(request);
//     }
//
//     /**
//      * Cancel the request.
//      */
//     public void cancel() {
//         isCanceled = true;
//         if (mConnection != null) {
//             mConnection.disconnect();
//         }
//     }
//
//     /**
//      * Connect to the server to change the connection anomalies occurred.
//      *
//      * @param request target request.
//      * @return connection between client and server.
//      * @throws ConnectException anomalies that occurred during the connection.
//      */
//     private Connection connect(Request request) throws ConnectException {
//         if (!mNetwork.isAvailable())
//             throw new NetworkError(String.format("Network Unavailable: %1$s.", request.url()));
//
//         try {
//             Headers headers = request.headers();
//             URI uri = new URI(request.url().toString());
//             List<String> cookieHeader = mCookieManager.get(uri);
//             if (cookieHeader != null && !cookieHeader.isEmpty())
//                 headers.add(KEY_COOKIE, cookieHeader);
//             headers.set(KEY_HOST, uri.getHost());
//             return mFactory.connect(request);
//         } catch (URISyntaxException e) {
//             throw new URLError(String.format("The url syntax error: %1$s.", request.url()), e);
//         } catch (MalformedURLException e) {
//             throw new URLError(String.format("The url is malformed: %1$s.", request.url()), e);
//         } catch (UnknownHostException e) {
//             throw new HostError(String.format("Hostname can not be resolved: %1$s.", request.url()), e);
//         } catch (SocketTimeoutException e) {
//             throw new ConnectTimeoutError(String.format("Connect time out: %1$s.", request.url()), e);
//         } catch (Exception e) {
//             throw new ConnectException(String.format("An unknown exception: %1$s.", request.url()), e);
//         }
//     }
//
//     private void writeBody(RequestBody body) throws WriteException {
//         try {
//             OutputStream stream = mConnection.getOutputStream();
//             body.writeTo(IOUtils.toBufferedOutputStream(stream));
//             IOUtils.closeQuietly(stream);
//         } catch (Exception e) {
//             throw new WriteException(e);
//         }
//     }
//
//     private Response readResponse(Request request) throws ReadException {
//         try {
//             int code = mConnection.getCode();
//             Headers headers = parseResponseHeaders(mConnection.getHeaders());
//
//             List<String> cookieList = headers.get(KEY_SET_COOKIE);
//             if (cookieList != null && !cookieList.isEmpty())
//                 mCookieManager.add(URI.create(request.url().toString()), cookieList);
//
//             String contentType = headers.getContentType();
//             ResponseBody body = new StreamBody(contentType, mConnection.getInputStream());
//             return Response.newBuilder().code(code).headers(headers).body(body).build();
//         } catch (SocketTimeoutException e) {
//             throw new ReadTimeoutError(String.format("Read data time out: %1$s.", request.url()), e);
//         } catch (Exception e) {
//             throw new ReadException(e);
//         }
//     }
//
//     private Headers parseResponseHeaders(Map<String, List<String>> headersMap) {
//         Headers headers = new Headers();
//         for (Map.Entry<String, List<String>> entry : headersMap.entrySet()) {
//             headers.add(entry.getKey(), entry.getValue());
//         }
//         return headers;
//     }
// }
package zhuj.http.response;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class ApiCallback implements Callback {
    private ApiResponseParser apiResponseParser;

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            ApiResponse apiResponse = apiResponseParser.parse(response);
            onSuccess(apiResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected abstract void onSuccess(ApiResponse apiResponse);

}

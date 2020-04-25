
import android.util.Log;

public abstract class SuccessCallback implements HttpCallback<ApiResponse> {
    @Override
    public void onFailure(ErrorMessage e) {
        Log.d("ApiResponse", e.toString());
    }

}

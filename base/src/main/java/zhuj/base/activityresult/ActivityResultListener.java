package zhuj.base.activityresult;

import android.content.Intent;

public interface ActivityResultListener {
    void onActivityResult(int requestCode, int resultCode, Intent data);

}

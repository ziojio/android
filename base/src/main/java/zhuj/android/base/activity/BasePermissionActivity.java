package zhuj.android.base.activity;

import java.util.Arrays;
import java.util.LinkedList;

public abstract class BasePermissionActivity extends IActivity {
    protected LinkedList<String> permissionList = new LinkedList<>();

    public void permissions(String... permissions) {
        permissionList.addAll(Arrays.asList(permissions));
    }

    protected void requestPermission() {
        if (permissionList.isEmpty()) {

        } else {

        }
    }
}

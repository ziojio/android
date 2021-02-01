package zhuj.android.permission;

import android.Manifest;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PermissionConstants {
    public static final int REQUEST_OVERLAY_PERMISSION_CODE = 10001;
    public static final int REQUEST_WRITE_SETTINGS_PERMISSION_CODE = 10002;

    public static final String STORAGE = Manifest.permission_group.STORAGE;
    public static final String CAMERA = Manifest.permission_group.CAMERA;
    public static final String LOCATION = Manifest.permission_group.LOCATION;
    public static final String MICROPHONE = Manifest.permission_group.MICROPHONE;
    public static final String PHONE = Manifest.permission_group.PHONE;
    public static final String SMS = Manifest.permission_group.SMS;
    public static final String SENSORS = Manifest.permission_group.SENSORS;
    public static final String CONTACTS = Manifest.permission_group.CONTACTS;
    public static final String CALENDAR = Manifest.permission_group.CALENDAR;
    public static final String ACTIVITY_RECOGNITION = Manifest.permission_group.ACTIVITY_RECOGNITION;
    public static final String CALL_LOG = Manifest.permission_group.CALL_LOG;

    public static final String[] ALL_PERMISSION = {
            STORAGE, CAMERA, LOCATION, MICROPHONE, PHONE, SENSORS, SMS, CONTACTS, CALENDAR, ACTIVITY_RECOGNITION, CALL_LOG
    };

    private static final String[] GROUP_CALENDAR = {
            Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR
    };
    private static final String[] GROUP_CAMERA = {
            Manifest.permission.CAMERA
    };
    private static final String[] GROUP_CONTACTS = new String[]{
            Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.GET_ACCOUNTS
    };
    private static final String[] GROUP_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final String[] GROUP_MICROPHONE = {
            Manifest.permission.RECORD_AUDIO
    };

    private static final String[] GROUP_PHONE = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.MODIFY_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.ADD_VOICEMAIL, Manifest.permission.USE_SIP
    };
    private static final String[] GROUP_SENSORS = {
            Manifest.permission.BODY_SENSORS
    };
    private static final String[] GROUP_SMS = {
            Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH, Manifest.permission.RECEIVE_MMS,
    };
    private static final String[] GROUP_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @StringDef({STORAGE, CAMERA, LOCATION, MICROPHONE, PHONE, SENSORS, SMS, CONTACTS, CALENDAR, ACTIVITY_RECOGNITION, CALL_LOG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface permission {
    }

    /**
     * 根据权限组获取指定组的权限
     *
     * @param permission
     */
    public static String[] getPermissions(@permission String permission) {
        switch (permission) {
            case CALENDAR:
                return GROUP_CALENDAR;
            case CAMERA:
                return GROUP_CAMERA;
            case CONTACTS:
                return GROUP_CONTACTS;
            case LOCATION:
                return GROUP_LOCATION;
            case MICROPHONE:
                return GROUP_MICROPHONE;
            case PHONE:
                return GROUP_PHONE;
            case SENSORS:
                return GROUP_SENSORS;
            case SMS:
                return GROUP_SMS;
            case STORAGE:
                return GROUP_STORAGE;
        }
        return new String[]{permission};
    }
}

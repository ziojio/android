package zhuj.android.app.model;

public class UpdateAppInfo {
    public static int NO_START = 0;
    public static int DOWNLOADING = 1;
    public static int PAUSE = 2;
    public static int RESUME = 3;
    public static int SUCCESS = 4;
    public static int FAILED = 5;

    public String apkUrl;
    public String apkName;


    public String downloadPath;
    public String version;
    public int status;
    public int retry = 0;

    public UpdateAppInfo(String apkUrl) {
        this.apkUrl = apkUrl;
        this.status = NO_START;
        setAppName(apkUrl);
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    private void setAppName(String apkName) {
        if (apkUrl.endsWith(".apk")) {
            int idx = apkUrl.lastIndexOf('/');
            if (idx == -1) {
                this.apkName = apkName;
            } else {
                this.apkName = apkUrl.substring(idx + 1);
            }
        }
    }

    @Override
    public String toString() {
        return "UpdateAppInfo{" +
                "apkUrl='" + apkUrl + '\'' +
                ", downloadPath='" + downloadPath + '\'' +
                '}';
    }
}

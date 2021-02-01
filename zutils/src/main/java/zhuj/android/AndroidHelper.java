package zhuj.android;

import androidx.annotation.NonNull;

public class AndroidHelper {
    private AndroidHelper(){
    }

    /**
     * 比较两个版本号
     *
     * @param versionName1
     * @param versionName2
     * @return [> 0 versionName1 > versionName2] [= 0 versionName1 = versionName2]  [< 0 versionName1 < versionName2]
     */
    public static int compareVersionName(@NonNull String versionName1, @NonNull String versionName2) {
        if (versionName1.equals(versionName2)) {
            return 0;
        }
        String[] versionArray1 = versionName1.split("\\.");//注意此处为正则匹配，不能用"."；
        String[] versionArray2 = versionName2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }

}

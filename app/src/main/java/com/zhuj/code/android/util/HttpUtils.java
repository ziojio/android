package com.jbzh.android.util;

import com.jbzh.android.logger.Logger;
import com.jbzh.jbsdk.jbyunapi;

import java.util.HashMap;

public class HttpUtils {
    //    public static final String HTTP_HOST = "http://jbzhapi.vanpin.com";
//    public static final String PREFIX = "/v1/api";
    public static final String ossPrefix = "http://jbzhyuncdn.vanpin.com/";

    //
    //  http parameter
    public static final String LOGIN_TYPE = "loginType";
    public static final String TOKEN = "token";
    public static final String SN_MD5 = "snMD5";
    public static final String USER_ID = "yid";
    public static final String NICK_NAME = "nick";
    public static final String CLASSROOM_ID = "classroom_id";
    public static final String LIVE_ID = "live_id";
    public static final String STATE = "state";

    public static final String FILE_NAME = "file_name";
    public static final String FILE_PATH = "file_path";
    public static final String FILE_TYPE = "file_type";
    public static final String THUMB = "thumb";
    public static final String OPERATION = "operation";
    public static final String INFO = "info";

    //  http URL
    public static final String URL_LOGIN = "/device/yunLogin";
    public static final String URL_GET_USER_INFO = "/device/getYunInfo";
    public static final String URL_CLASS_MEMBERS = "/lesson/listLessonMember";
    public static final String URL_LIST_TEACH_CLASS = "/lesson/listTeacherLesson";
    public static final String URL_SET_CLASS_LIVE_INFO = "/live/getClassLiveInfo";
    public static final String URL_GET_CLASS_LIVE_INFO = "/live/getClassLiveInfo";
    public static final String URL_PAINT_VIEW_LIB = "/appdata/paintview/libs.json";
    public static final String URL_ADD_UPLOAD_INFO = "/files/addUploadInfo";


    public static void getUserInfo(jbyunapi.OnAPICallBack callback) {
        String url = jbyunapi.ConfigBaseURL + jbyunapi.gprefix + URL_GET_USER_INFO;
        Logger.i("getUserInfo. url=%s", url);
        jbyunapi.asynchttp("post", url, null, jbyunapi.gheader(), callback);
    }

    public static void getClassLiveId(String classNum, jbyunapi.OnAPICallBack callback) {
        String url = jbyunapi.ConfigBaseURL + jbyunapi.gprefix + URL_GET_CLASS_LIVE_INFO;
        Logger.i("getClassLiveId. url=%s", url);
        HashMap<String, String> params = new HashMap<>();
        params.put(CLASSROOM_ID, classNum);
        jbyunapi.asynchttp("post", url, params, jbyunapi.gheader(), callback);
    }

    public static void setClassLiveId(String classNum, String liveId, jbyunapi.OnAPICallBack callback) {
        String url = jbyunapi.ConfigBaseURL + jbyunapi.gprefix + URL_SET_CLASS_LIVE_INFO;
        Logger.i("setClassLiveId. url=%s", url);
        HashMap<String, String> params = new HashMap<>();
        params.put(CLASSROOM_ID, classNum);
        params.put(LIVE_ID, liveId);
        params.put(STATE, "1");
        jbyunapi.asynchttp("post", url, params, jbyunapi.gheader(), callback);
    }

    public static void getClassMembers(String classNum, jbyunapi.OnAPICallBack callback) {
        String url = jbyunapi.ConfigBaseURL + jbyunapi.gprefix + URL_CLASS_MEMBERS;
        Logger.i("getClassMembers . url=%s", url);
        HashMap<String, String> params = new HashMap<>();
        params.put(CLASSROOM_ID, classNum);
        jbyunapi.asynchttp("get", url, params, jbyunapi.gheader(), callback);
    }

    public static void addUploadInfo(String file_name, String file_path, String file_type, String thumb, String operation,
                                     String info, jbyunapi.OnAPICallBack callback) {
        String url = jbyunapi.ConfigBaseURL + jbyunapi.gprefix + URL_ADD_UPLOAD_INFO;
        Logger.i("addUploadInfo . url=%s", url);
        HashMap<String, String> params = new HashMap<>();
        params.put(FILE_NAME, file_name);
        params.put(FILE_PATH, file_path);
        params.put(FILE_TYPE, file_type);
        params.put(THUMB, thumb);
        params.put(OPERATION, operation);
        params.put(INFO, info);
        jbyunapi.asynchttp("post", url, params, jbyunapi.gheader(), callback);
    }
}

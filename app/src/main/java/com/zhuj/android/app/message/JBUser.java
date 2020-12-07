package com.zhuj.android.app.message;


public class JBUser implements IUser {
    private String uid;
    private String name;
    private int status;

    public JBUser(String id, String name) {
        this.uid = id;
        this.name = name;
    }

    @Override
    public String getUId() {
        return String.valueOf(uid);
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public String getAvatarPath() {
        return "R.drawable.bg_circle_6dp_blue";
    }
}

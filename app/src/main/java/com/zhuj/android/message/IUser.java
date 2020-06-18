package com.zhuj.android.message;


public interface IUser {

    /**
     * User id.
     * @return user id, unique
     */
    String getUId();

    /**
     * Display name of user
     * @return display name
     */
    String getDisplayName();

    /**
     * Get user avatar file path.
     * @return avatar file path
     */
    String getAvatarPath();

}

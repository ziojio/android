package zhuj.utils.pref;

import android.content.SharedPreferences;

import com.zhuj.code.Preconditions;
import com.zhuj.code.json.GsonUtils;
import com.zhuj.code.lang.StringUtils;

import java.lang.reflect.Type;
import java.util.Map;

public class SPWrap {
    private final SharedPreferences sp;

    public SPWrap(SharedPreferences sp) {
        Preconditions.checkNotNull(sp);
        this.sp = sp;
    }

    //=======================================键值保存==============================================//

    public boolean commit(String key, boolean value) {
        return sp.edit().putBoolean(key, value).commit();
    }

    public void apply(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public boolean commit(String key, float value) {
        return sp.edit().putFloat(key, value).commit();
    }

    public void apply(String key, float value) {
        sp.edit().putFloat(key, value).apply();
    }

    public boolean commit(String key, long value) {
        return sp.edit().putLong(key, value).commit();
    }

    public void apply(String key, long value) {
        sp.edit().putLong(key, value).apply();
    }

    public boolean commit(String key, String value) {
        return sp.edit().putString(key, value).commit();
    }

    public void apply(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public boolean commit(String key, int value) {
        return sp.edit().putInt(key, value).commit();
    }

    public void apply(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public boolean commit(String key, Object object) {
        if (object instanceof String) {
            return sp.edit().putString(key, (String) object).commit();
        } else if (object instanceof Integer) {
            return sp.edit().putInt(key, (Integer) object).commit();
        } else if (object instanceof Boolean) {
            return sp.edit().putBoolean(key, (Boolean) object).commit();
        } else if (object instanceof Float) {
            return sp.edit().putFloat(key, (Float) object).commit();
        } else if (object instanceof Long) {
            return sp.edit().putLong(key, (Long) object).commit();
        } else {
            return sp.edit().putString(key, StringUtils.toString(object)).commit();
        }
    }

    public void apply(String key, Object object) {
        if (object instanceof String) {
            sp.edit().putString(key, (String) object).apply();
        } else if (object instanceof Integer) {
            sp.edit().putInt(key, (Integer) object).apply();
        } else if (object instanceof Boolean) {
            sp.edit().putBoolean(key, (Boolean) object).apply();
        } else if (object instanceof Float) {
            sp.edit().putFloat(key, (Float) object).apply();
        } else if (object instanceof Long) {
            sp.edit().putLong(key, (Long) object).apply();
        } else {
            sp.edit().putString(key, StringUtils.toString(object)).apply();
        }
    }

    //=======================================键值获取==================================================//

    public boolean getBoolean(String key, boolean defValue) {
        try {
            return sp.getBoolean(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }


    public long getLong(String key, long defValue) {
        try {
            return sp.getLong(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    public float getFloat(String key, float defValue) {
        try {
            return sp.getFloat(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }


    public String getString(String key, String defValue) {
        try {
            return sp.getString(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }


    public int getInt(String key, int defValue) {
        try {
            return sp.getInt(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }


    public <T> T getObject(String key, Type type) {
        return GsonUtils.fromJson(getString(key, ""), type);
    }


    public Object getObject(String key, Object defObj) {
        try {
            if (defObj instanceof String) {
                return sp.getString(key, (String) defObj);
            } else if (defObj instanceof Integer) {
                return sp.getInt(key, (Integer) defObj);
            } else if (defObj instanceof Boolean) {
                return sp.getBoolean(key, (Boolean) defObj);
            } else if (defObj instanceof Float) {
                return sp.getFloat(key, (Float) defObj);
            } else if (defObj instanceof Long) {
                return sp.getLong(key, (Long) defObj);
            } else {
                return GsonUtils.fromJson(getString(key, ""), defObj.getClass());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defObj;
    }


    //=======================================公共方法==================================================//

    public boolean contains(String key) {
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param sp SharedPreferences实例
     * @return
     */
    public Map<String, ?> getAll(SharedPreferences sp) {
        try {
            return sp.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 去除某一键值对
     *
     * @param key
     * @return
     */
    public boolean remove(String key) {
        return sp.edit().remove(key).commit();
    }

    /**
     * 清空销毁
     *
     * @param sp SharedPreferences实例
     */
    public boolean clear(SharedPreferences sp) {
        return sp.edit().clear().commit();
    }


}

package com.mc.enjoysdk.util;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * {@link EnjoyUtil} EnjoySDK Prop 工具类 </br>
 * </br>
 * * 接口功能：</br>
 * <ol>
 * <li>{@link PropUtil#setValueToProp(String, String)}</li> 设置 Prop 值 </br>
 * <li>{@link PropUtil#getStringFromProp(String)}</li> 获取 String 类型 Prop 值 </br>
 * <li>{@link PropUtil#getIntFromProp(String, int)}</li> 获取 Int 类型 Prop 值 </br>
 * <li>{@link PropUtil#getBooleanFromProp(String, boolean)}</li> 获取 Boolean 类型 Prop 值 </br>
 * </ol>
 */
public class PropUtil {
    private static final String TAG = "PropUtil";

    public PropUtil() {
    }

    /**
     * 设置 Prop 值 </br>
     * </br>
     * @param key Prop key </br>
     * @param val Prop value </br>
     */
    public static void setValueToProp(String key, String val) {
        try {
            Class<?> classType = Class.forName("android.os.SystemProperties");
            Method method = classType.getDeclaredMethod("set", String.class, String.class);
            method.invoke(classType, key, val);
        } catch (ClassNotFoundException var4) {
            Log.e("PropUtil", "ClassNotFountException, e == " + var4.getMessage());
        } catch (NoSuchMethodException var5) {
            Log.e("PropUtil", "NoSuchMethodException, e == " + var5.getMessage());
        } catch (InvocationTargetException var6) {
            Log.e("PropUtil", "InvocationTargetException, e == " + var6.getMessage());
        } catch (Exception var7) {
            Log.e("PropUtil", "Exception, e == " + var7.getMessage());
        }
    }

    /**
     * 获取 String 类型 Prop 值 </br>
     * </br>
     * @param key Prop key </br>
     * @return Prop value </br>
     */
    public static String getStringFromProp(String key) {
        String value = "";

        try {
            Class<?> classType = Class.forName("android.os.SystemProperties");
            Method getMethod = classType.getDeclaredMethod("get", String.class);
            value = (String)getMethod.invoke(classType, key);
        } catch (Exception var4) {
            Log.e("PropUtil", "getValue Exception, e == " + var4.getMessage());
        }

        return value;
    }

    /**
     * 获取 Int 类型 Prop 值 </br>
     * </br>
     * @param key Prop key </br>
     * @return Prop value </br>
     */
    public static int getIntFromProp(String key, int def) {
        int value = def;

        try {
            Class<?> classType = Class.forName("android.os.SystemProperties");
            Method getMethod = classType.getDeclaredMethod("getInt", String.class, int.class);
            value = (int) getMethod.invoke(classType, key, def);
        } catch (Exception var4) {
            Log.e("PropUtil", "getValue Exception, e == " + var4.getMessage());
        }

        return value;
    }

    /**
     * 获取 Boolean 类型 Prop 值 </br>
     * </br>
     * @param key Prop key </br>
     * @return Prop value </br>
     */
    public static boolean getBooleanFromProp(String key, boolean def) {
        boolean value = def;

        try {
            Class<?> classType = Class.forName("android.os.SystemProperties");
            Method getMethod = classType.getDeclaredMethod("getBoolean", String.class, boolean.class);
            value = (Boolean) getMethod.invoke(classType, key, def);
        } catch (Exception var4) {
            Log.e("PropUtil", "getValue Exception, e == " + var4.getMessage());
        }

        return value;
    }
}


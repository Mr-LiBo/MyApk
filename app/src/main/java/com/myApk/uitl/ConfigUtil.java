/**
 * version:1.0
 * 版权: XXXXX
 * zKF69930
 * 文件名: com/huawei/ccloud/framework/utils/ConfigUtil.java 
 */

package com.myApk.uitl;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.myApk.common.GlobalMessageType;

/**
 * 配置辅助类，包含本地配置辅助类和服务端辅助类
 * 
 * @author zkf69930
 */
public  final class ConfigUtil
{


        // 配置默认值
        private static final boolean DEFAULT_BOOLEAN_VALUE = false;

        private static final int DEFAULT_INT_VALUE = 0;

        private static final long DEFAULT_LONG_VALUE = 0;

        private static final float DEFAULT_FLOAT_VALUE = 0;

        private static final String DEFAULT_STRING_VALUE = "";

        private static final String DEFAULT_SP_NAME = GlobalMessageType.SharedFileKey.COMMON_STROE_FILE_NAME;

        private static Map<String, Object> propreties = new HashMap<String, Object>();

        private ConfigUtil() {

        }

        private static SharedPreferences getSharePre(Context ctx) {
            return ctx.getSharedPreferences(DEFAULT_SP_NAME,
                    Context.MODE_PRIVATE);
        }

        /**
         * 获取boolean配置
         *
         * @param ctx ctx
         * @param key key
         * @return boolean
         */
        public static boolean getBoolean(Context ctx, String key) {
            Boolean booleas = (Boolean) propreties.get(key);
            if (booleas == null) {
                booleas = getBoolean(ctx, key, DEFAULT_BOOLEAN_VALUE);
                propreties.put(key, booleas);
            }
            return booleas;
        }

        /**
         * 获取boolean配置
         *
         * @param ctx          ctx
         * @param key          key
         * @param defaultValue defaultValue
         * @return boolean
         */
        public static boolean getBoolean(Context ctx, String key,boolean defaultValue) {
            if (null == ctx || null == key) {
                return defaultValue;
            }

            Boolean booleas = (Boolean) propreties.get(key);
            if (booleas == null) {
                booleas = getSharePre(ctx).getBoolean(key, defaultValue);
                propreties.put(key, booleas);
            }
            return booleas;
        }

        /**
         * 设置boolean值
         *
         * @param ctx   ctx
         * @param key   key
         * @param value value
         * @return boolean
         */
        public static boolean putBoolean(Context ctx, String key, boolean value) {
            if (null == ctx || null == key) {
                return false;
            }
            propreties.put(key, value);
            SharedPreferences sharePre = getSharePre(ctx);
            Editor edit = sharePre.edit();
            edit.putBoolean(key, value);
            return edit.commit();
        }

        /**
         * 获取int配置
         *
         * @param ctx ctx
         * @param key key
         * @return int
         */
        public static int getInt(Context ctx, String key) {
            return getInt(ctx, key, DEFAULT_INT_VALUE);
        }

        /**
         * 获取int配置
         *
         * @param ctx          ctx
         * @param key          key
         * @param defaultValue defaultValue
         * @return int
         */
        public static int getInt(Context ctx, String key, int defaultValue) {
            if (null == ctx || null == key) {
                return defaultValue;
            }

            Integer intv = (Integer) propreties.get(key);
            if (intv == null) {
                try {
                    intv = getSharePre(ctx).getInt(key, defaultValue);
                } catch (ClassCastException e) {
                    intv = defaultValue;
                }

                propreties.put(key, intv);
            }
            return intv;
        }

        /**
         * 设置int值
         *
         * @param ctx   ctx
         * @param key   key
         * @param value value
         * @return boolean
         */
        public static boolean putInt(Context ctx, String key, int value) {
            if (null == ctx || null == key) {
                return false;
            }

            propreties.put(key, value);

            SharedPreferences sharePre = getSharePre(ctx);
            Editor edit = sharePre.edit();
            edit.putInt(key, value);
            return edit.commit();
        }

        /**
         * 获取long配置
         *
         * @param ctx ctx
         * @param key key
         * @return long
         */
        public static long getLong(Context ctx, String key) {
            return getLong(ctx, key, DEFAULT_LONG_VALUE);
        }

        /**
         * 获取long配置
         *
         * @param ctx          ctx
         * @param key          key
         * @param defaultValue defaultValue
         * @return long
         */
        public static long getLong(Context ctx, String key, long defaultValue) {
            if (null == ctx || null == key) {
                return defaultValue;
            }

            Long longv = (Long) propreties.get(key);
            if (longv == null) {
                try {
                    longv = getSharePre(ctx).getLong(key, defaultValue);
                } catch (ClassCastException e) {
                    longv = defaultValue;
                }
                propreties.put(key, longv);
            }
            return longv;
        }

        /**
         * 设置long值
         *
         * @param ctx   ctx
         * @param key   key
         * @param value value
         * @return boolean
         */
        public static boolean putLong(Context ctx, String key, long value) {
            if (null == ctx || null == key) {
                return false;
            }

            propreties.put(key, value);

            SharedPreferences sharePre = getSharePre(ctx);
            Editor edit = sharePre.edit();
            edit.putLong(key, value);
            return edit.commit();
        }

        /**
         * 获取float配置
         *
         * @param ctx ctx
         * @param key key
         * @return float
         */
        public static float getFloat(Context ctx, String key) {
            return getFloat(ctx, key, DEFAULT_FLOAT_VALUE);
        }

        /**
         * 获取float配置
         *
         * @param ctx          ctx
         * @param key          key
         * @param defaultValue defaultValue
         * @return float
         */
        public static float getFloat(Context ctx, String key, float defaultValue) {
            if (null == ctx || null == key) {
                return defaultValue;
            }

            Float floatv = (Float) propreties.get(key);
            if (floatv == null) {
                floatv = getSharePre(ctx).getFloat(key, defaultValue);
                propreties.put(key, floatv);
            }
            return floatv;
        }

        /**
         * 设置float值
         *
         * @param ctx   ctx
         * @param key   key
         * @param value value
         * @return boolean
         */
        public static boolean putFloat(Context ctx, String key, float value) {
            if (null == ctx || null == key) {
                return false;
            }

            propreties.put(key, value);

            SharedPreferences sharePre = getSharePre(ctx);
            Editor edit = sharePre.edit();
            edit.putFloat(key, value);
            return edit.commit();
        }

        /**
         * 获取String配置
         *
         * @param ctx ctx
         * @param key key
         * @return String
         */
        public static String getString(Context ctx, String key) {
            return getString(ctx, key, DEFAULT_STRING_VALUE);
        }

        /**
         * 获取String配置
         *
         * @param ctx          ctx
         * @param key          key
         * @param defaultValue defaultValue
         * @return String
         */
        public static String getString(Context ctx, String key,
                                       String defaultValue) {
            if (null == ctx || null == key) {
                return defaultValue;
            }

            String strv = (String) propreties.get(key);
            if (strv == null) {
                strv = getSharePre(ctx).getString(key, defaultValue);
                propreties.put(key, strv);
            }
            return strv;
        }

        /**
         * 设置String值
         *
         * @param ctx   ctx
         * @param key   key
         * @param value value
         * @return boolean
         */
        public static boolean putString(Context ctx, String key, String value) {
            if (null == ctx || null == key) {
                return false;
            }

            propreties.put(key, value);

            SharedPreferences sharePre = getSharePre(ctx);
            Editor edit = sharePre.edit();
            if (null == value) {
                edit.remove(key);
            } else {
                edit.putString(key, value);
            }
            return edit.commit();
        }

        /**
         * 以Map形式返回所有配置项信息
         *
         * @param ctx ctx
         * @return Map<String, String>
         */
        public static Map<String, String> getAllConfig(Context ctx) {
            SharedPreferences sharePre = getSharePre(ctx);
            return (Map<String, String>) sharePre.getAll();
        }

        /**
         * 删除配置
         *
         * @param ctx ctx
         * @param key key
         * @return boolean
         */
        public static boolean remove(Context ctx, String key) {
            if (null == ctx || null == key) {
                return false;
            }

            propreties.put(key, null);

            SharedPreferences sharePre = getSharePre(ctx);
            Editor edit = sharePre.edit();
            edit.remove(key);
            return edit.commit();
        }

        /**
         * 清除配置
         *
         * @param ctx ctx
         * @return boolean
         */
        public static boolean clear(Context ctx) {
            if (null == ctx) {
                return false;
            }
            SharedPreferences sharePre = getSharePre(ctx);
            Editor edit = sharePre.edit();
            edit.clear();
            return edit.commit();
        }
}

package com.myApk.common;

/**
 * Created by admin on 2015/9/16.
 */
public interface GlobalMessageType {

    interface TypeMsg
    {
        int BASE = 0x1000000;
        int load_service_success = BASE+1;
    }

    interface TrdPtyErrPolicyMessage {
        int BASE = 0x2000000;
        int TRDPTYERRPOLICY_SUCCESS_SERVER = BASE +1;
        int TRDPTYERRPOLICY_SUCCESS_LOCAL = BASE +2;
    }

    interface SharedFileKey
    {
        // --------------------------------通用模块---------------------------------
        /** 存储的SharedPreferences文件名称 */
        String COMMON_STROE_FILE_NAME = "config";

        /**是否升级*/
        String UPDATE_SOFTWARE = "update_software";

    }



}

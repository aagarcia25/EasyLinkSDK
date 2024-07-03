
package com.paxsz.elwin.utils;

/**
 * ClassName:ConstantsUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2019年4月23日 下午5:31:53 <br/>
 * 
 * @author zhubenhui
 * @version
 */
public class Constant {
    // 通信模式
    public static final String EL_FUN_CONNECT = "connect";
    public static final String EL_CONNECT_TYPE_USB = "usb";
    // 参数文件
    public static final String EL_FUN_FILE_DOWNLOAD = "filedownload";
    public static final String EL_FILE_DOWNLOAD_TYPE_EMV = "emv";
    public static final String EL_FILE_DOWNLOAD_TYPE_CLSS = "clss";
    public static final String EL_FILE_DOWNLOAD_TYPE_UI = "ui";

    // 参数文件路径
    public static final String EL_FILE_PARAM_EMV_PATH = "D:\\code\\AppData\\emv_param.emv";
    public static final String EL_FILE_PARAM_CLSS_PATH = "D:\\code\\AppData\\clss_param.clss";
    public static final String EL_FILE_PARAM_UI_PATH = "D:\\code\\AppData\\ui_d180.ui";

    public static final String EL_FUN_SET_DATA = "setData";
    public static final String EL_SET_DATA_TYPE_TRANS = "setData_trans";
    public static final String EL_SET_DATA_TYPE_CONFIG = "setData_config";

    public static final String EL_FUN_GET_DATA = "getData";
    public static final String EL_FUN_START_TRANSACTION = "startTransaction";

    public static boolean isFirstDownload = true;

    private static final String SEARCHCARDMODE = "searchMode";
    private static final String SET_Config_EG = "0214010702160101";

}

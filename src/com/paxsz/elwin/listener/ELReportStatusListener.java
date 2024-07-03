package com.paxsz.elwin.listener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.paxsz.easylink.listener.IReportStatusListener;
import com.paxsz.easylink.listener.ReportConstant;
import com.paxsz.easylink.model.AppSelectResponse;
import com.paxsz.easylink.model.DataModel.DataType;
import com.paxsz.easylink.model.TLVDataObject;
import com.paxsz.elwin.test.MainFrame;
import com.paxsz.elwin.utils.Convert;
import com.paxsz.elwin.utils.LogUtils;

public class ELReportStatusListener implements IReportStatusListener {
    private static final String TAG = "ELReportStatusListener";
    String onEnterPinResult = "";
    String onReadCardResult = "";
    String onReportSearchModeResult = "";
    String onSelectAppResult = "";
    String onSetParamToPinPadResult = "";

    String readCardType = "";
    String readCardStatu = "";
    String mSearchMode = "";
    private long mStartReadTime;
    private long mEndReadTime;
    private JTextArea mReportsArea;

    public ELReportStatusListener() {
        mReportsArea = new MainFrame("").getReportArea();
    }

    @Override
    public void onEnterPin(String prompts, int pinLen, String enterPinEvent) {

        onEnterPinResult = "onEnterPin : " + prompts + "\r\n pinLen : " + pinLen
                + "\r\n enterPinEvent : " + enterPinEvent;
        refreshGUI(onEnterPinResult);

        LogUtils.debug(TAG, "onEnterPin,prompts:-" + prompts + ",pinLen-" + pinLen
                + ",enterPinEvent-" + enterPinEvent);
    }

    @Override
    public void onReadCard(String prompts, int type, int readCardStatus) {
        switch (type) {
            case ReportConstant.CONTACT:
                readCardType = "Card Type : Contact Card";
                break;

            case ReportConstant.CONTACTLESS:
                readCardType = "Card Type : Contactless Card ";
                break;

            case ReportConstant.MAGNETIC:
                readCardType = "Card Type : Magnetic Card";
                break;

            default:
                break;
        }

        switch (readCardStatus) {
            case ReportConstant.STATUS_IDLE:
                readCardStatu = "Read Card Status : \r\n STATUS_IDLE";
                break;
            case ReportConstant.STATUS_NOT_READY:
                readCardStatu = "Read Card Status : \r\n STATUS_NOT_READY";
                break;
            case ReportConstant.STATUS_PROCESSING:
                readCardStatu = "Read Card Status : \r\n STATUS_PROCESSING ";
                break;
            case ReportConstant.STATUS_READY_TO_READ:
                readCardStatu = "Read Card Status : \r\n STATUS_READY_TO_READ";
                break;
            case ReportConstant.DETECTED_CARD_SUCCESSFULLY:
                mStartReadTime = System.currentTimeMillis();
                readCardStatu = "Read Card Status : \r\n DETECTED_CARD_SUCCESSFULLY";
                break;
            case ReportConstant.STATUS_READ_CARD_SUCCESSFULLY:
                mEndReadTime = System.currentTimeMillis();
                long readTime = mEndReadTime - mStartReadTime;
                readCardStatu =
                        "Read Card Status : \r\n STATUS_READ_CARD_SUCCESSFULLY  \r\n \r\n Time of successful card reading is : "
                                + readTime + " ms ";
                break;
            case ReportConstant.STATUS_PROCESSING_ERROR_CONTACTLESS_ERROR:
                readCardStatu = "Read Card Status : \r\n STATUS_PROCESSING_ERROR_CONTACTLESS_ERROR";
                break;
            case ReportConstant.STATUS_CARD_NOT_REMOVED_FROM_READER:
                readCardStatu =
                        "Read Card Status : \r\n STATUS_PROCESSING_ERROR_CARD_NOT_REMOVED_FROM_READER";
                break;
            case ReportConstant.STATUS_PROCESSING_ERROR_CONTACTLESS_COLLISION_DETECTED:
                readCardStatu =
                        "Read Card Status : \r\n STATUS_PROCESSING_ERROR_CONTACTLESS_COLLISION_DETECTED";
                break;
            case ReportConstant.STATUS_PROCESSING_ERROR_CONTACTLESS_CONDITIONS_NOT_SATISFIED:
                readCardStatu =
                        "Read Card Status : \r\n STATUS_PROCESSING_ERROR_CONTACTLESS_CONDITIONS_NOT_SATIFIED";
                break;
        }

        onReadCardResult =
                "onReadCard　: \r\n" + prompts + "\r\n" + readCardType + "\r\n" + readCardStatu;

        refreshGUI(onReadCardResult);

        LogUtils.debug(TAG, "onReadCard,prompts-" + prompts + ",type-" + readCardType
                + ",readCardStatus-" + readCardStatus);

    }

    @Override
    public void onReportSearchMode(String prompts, int mode) {
        switch (mode) {
            case ReportConstant.MSR_SWIPE:
                mSearchMode = ReportConstant.MSR_SWIPE + "(MSR_SWIPE)";
                break;
            case ReportConstant.ICC_INSERT:
                mSearchMode = ReportConstant.ICC_INSERT + "(ICC_INSERT)";
                break;
            case ReportConstant.PICC_TAP:
                mSearchMode = ReportConstant.PICC_TAP + "(PICC_TAP)";
                break;
            case ReportConstant.SWIPE_FALLBACK:
                mSearchMode = ReportConstant.SWIPE_FALLBACK + "(SWIPE_FALLBACK)";
                break;
            case ReportConstant.ICC_INSERT_AND_PICC_TAP:
                mSearchMode = ReportConstant.ICC_INSERT_AND_PICC_TAP + "(ICC_INSERT_AND_PICC_TAP)";
                break;
            case ReportConstant.MSR_SWIPE_AND_ICC_INSERT:
                mSearchMode =
                        ReportConstant.MSR_SWIPE_AND_ICC_INSERT + "(MSR_SWIPE_AND_ICC_INSERT)";
                break;

            case ReportConstant.MSR_SWIPE_PICC_TAP:
                mSearchMode = ReportConstant.MSR_SWIPE_PICC_TAP + "(MSR_SWIPE_PICC_TAP)";
                break;

            case ReportConstant.MSR_SWIPE_AND_ICC_INSERT_AND_PICC_TAP:
                mSearchMode = ReportConstant.MSR_SWIPE_AND_ICC_INSERT_AND_PICC_TAP
                        + "(MSR_SWIPE_AND_ICC_INSERT_AND_PICC_TAP)";
                break;

        }
        mReportsArea.setText(
                "onReportSearchMode : \r\n" + prompts + "\r\n the searchMode is: " + mSearchMode);
        mReportsArea.append("\r\n \r\n");

        LogUtils.debug(TAG, "onReportSearchMode,prompts:" + prompts + ",mode-" + mode);

    }

    @Override
    public AppSelectResponse onSelectApp(String prompts, int timeout, List<String> appList) {
        String appStr = "please select app: \r\n";
        AppSelectResponse appSelectResponse = new AppSelectResponse();
        // mReportsStr += "onSelectApp>>> \r\n";
        /*
         * AppSelectResponse appSelectResponse = new AppSelectResponse();
         * appSelectResponse.setStatus(AppSelectResponse.SUCCESS); for (Iterator iterator =
         * appList.iterator(); iterator.hasNext();) { String message = (String) iterator.next();
         * LogUtils.info(TAG, message); }
         */

        // for D180c ,just setText
        onSelectAppResult = "AppSelectResponse : \r\n" + prompts;
        refreshGUI(onSelectAppResult);

        if (appList != null) {

            for (String app : appList) {
                appStr = appStr + app + " \r\n";
            }

            appSelectResponse.setAppLabel(appList.get(0));
            appSelectResponse.setAppIndex(0);
            appSelectResponse.setStatus(AppSelectResponse.SUCCESS);
            refreshGUI(appStr + "the default select is first one: " + appList.get(0));
            return appSelectResponse;
        }

        LogUtils.debug(TAG, "onSelectApp,prompts:" + prompts + ",timeout" + timeout);
        return null;
    }

    @Override
    public ArrayList<TLVDataObject> onSetParamToPinPad(String prompts,
            ArrayList<TLVDataObject> configTLVs, ArrayList<TLVDataObject> emvTLVs, int timeout) {
        ArrayList<TLVDataObject> tlvDataObject = new ArrayList<TLVDataObject>();
        StringBuilder configs = new StringBuilder("configTlv: \r\n ");
        if (configTLVs != null && configTLVs.size() > 0) {
            for (TLVDataObject configTLV : configTLVs) {
                configs.append("tag: " + Convert.bcdToStr(configTLV.getTag()) + ", value: "
                        + Convert.bcdToStr(configTLV.getValue()) + " \r\n ");
            }
        }
        StringBuilder emvTlv = new StringBuilder(" \r\n emvTlv: \r\n ");
        if (emvTLVs != null && emvTLVs.size() > 0) {
            for (TLVDataObject emvTLV : emvTLVs) {
                emvTlv.append("tag:" + Convert.bcdToStr(emvTLV.getTag()) + ", value: "
                        + Convert.bcdToStr(emvTLV.getValue()) + " \r\n ");
            }
        }

        String PinKeyParam = "020201010203010C02040100";
        byte[] PinKeyParamArrays =
                Convert.strToBcd(PinKeyParam, Convert.EPaddingPosition.PADDING_RIGHT);
        Convert.unpackValues(DataType.CONFIGURATION_DATA, PinKeyParamArrays, tlvDataObject);

        onSetParamToPinPadResult = "onSetParamToPinPad: \r\n" + prompts + " \r\n "
                + configs.toString() + emvTlv.toString();

        refreshGUI(onSetParamToPinPadResult);
        LogUtils.debug(TAG,
                "onSetParamToPinPad: \r\n configTLV :" + configs + "\r\n emvTLV : " + emvTlv);

        return tlvDataObject;
    }

    public void refreshGUI(String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                mReportsArea.append(text + "\r\n \r\n");
                mReportsArea.paintImmediately(mReportsArea.getBounds());
                mReportsArea.selectAll();
            }
        });

    }

}

package com.paxsz.elwin.listener;

import com.paxsz.easylink.listener.FileDownloadListener;
import com.paxsz.elwin.utils.LogUtils;

public class ELFileDownloadListener implements FileDownloadListener {

    private static final String TAG = "ELFileDownloadListener";

    @Override
    public boolean cancelDownload() {
        LogUtils.error(TAG, "File download is stopped....");
        // JTextArea textArea = new MainTestFrame("").getTextArea();
        // textArea.setText("File download is stopped....");
        return false;
    }

    @Override
    public void onDownloadProgress(int arg0, int arg1) {
        // TODO Auto-generated method stub

    }
}

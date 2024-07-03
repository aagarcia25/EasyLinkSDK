
package com.paxsz.elwin.listener;

import javax.swing.JOptionPane;

import com.paxsz.easylink.listener.IDeviceStateChangeListener;
import com.paxsz.elwin.utils.LogUtils;

/**
 * ClassName:ELDeviceStateChangeListener <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2019年6月18日 下午3:18:46 <br/>
 * 
 * @author zhubenhui
 * @version
 */
public class ELDeviceStateChangeListener implements IDeviceStateChangeListener {

    private static final String TAG = "ELDeviceStateChangeListener";

    @Override
    public void onDisconnect() {
        JOptionPane.showMessageDialog(null, "USB Disconnected ...", "Warnning!",
                JOptionPane.ERROR_MESSAGE);

        LogUtils.debug(TAG, "The usb device is disconnected!");

    }

}

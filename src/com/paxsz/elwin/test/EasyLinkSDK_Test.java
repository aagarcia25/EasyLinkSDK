
package com.paxsz.elwin.test;

import java.awt.Button;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.paxsz.easylink.api.EasyLinkSdkManager;
import com.paxsz.easylink.device.DeviceInfo;
import com.paxsz.easylink.device.DeviceInfo.CommType;
import com.paxsz.easylink.model.DataModel.DataType;
import com.paxsz.easylink.model.KcvInfo;
import com.paxsz.easylink.model.KeyInfo;
import com.paxsz.easylink.model.KeyType;
import com.paxsz.easylink.model.ShowPageInfo;
import com.paxsz.easylink.model.UIRespInfo;
import com.paxsz.easylink.model.picc.EDetectMode;
import com.paxsz.easylink.model.picc.ELedStatus;
import com.paxsz.easylink.model.picc.PiccCardInfo;
import com.paxsz.elwin.listener.ELDeviceStateChangeListener;
import com.paxsz.elwin.listener.ELFileDownloadListener;
import com.paxsz.elwin.listener.ELReportStatusListener;
import com.paxsz.elwin.utils.Convert;
import com.paxsz.elwin.utils.Convert.EPaddingPosition;
import com.paxsz.elwin.utils.LogUtils;

/**
 * ClassName:FirstFrameTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2019年4月24日 下午5:25:04 <br/>
 * 
 * @author zhubenhui
 * @version
 */
public class EasyLinkSDK_Test extends JPanel implements ActionListener, ItemListener {
    public static final String TAG = "EasyLinkSDK_Test";
    public static String mTransType = "Sale";

    private JFrame mframe;
    private JButton mBtnScanFile;
    private JButton mBtnLoadFile;
    private JTextField mInputData;
    private JTextArea mResultsArea;

    private JButton mBtnConnect;
    private JButton mBtnDisconnect;
    private JButton mBtnSetTransData;
    private JButton mBtnGetTransData;
    private JButton mBtnStarttrans;
    private JButton mBtnCompletetrans;
    private JButton mBtnGetPinBlock;
    private JButton mBtnSendcancle;
    private JButton mBtnGetconnecteddevice;
    private JButton mBtnCalcmac;
    private JButton mBtnIncreaseksn;
    private JButton mBtnEncryptdata;
    private JButton mBtnShowpage;
    private JRadioButton mRdbtnUsb;
    private JRadioButton mRdbtnBt;
    private static DeviceInfo sDeviceInfo;
    private static EasyLinkSdkManager sEasyLinkMagager;
    private JButton mBtnSetConfigData;
    private JRadioButton mRdbtnSale;
    private JRadioButton mRdbtnRefund;
    private JButton mBtnGetConfigData;
    private static ExecutorService sExecutorService;

    private JRadioButton mRdbtnCashBack;
    private JButton mBtnInputAmount;
    private JTextField mTextFile;
    private JButton mBtnSetDebug;
    private JButton mBtnGetUSBDevice;
    private JButton mBtnGetversion;
    private JButton mBtnWritekey;
    private JButton mBtnWriteTIK;
    private JButton mBtnPiccOpen;
    private JButton mBtnPiccDetect;
    private JButton mBtnPiccRemove;
    private JButton mBtnPiccLight;
    private JButton mBtnPiccCmdExchange;
    private JButton mBtnPiccM1Authority;
    private JButton mBtnPiccM1ReadBlock;
    private JButton mBtnPiccM1Operate;
    private Button mBtnRefresh;
    private List<String> mDevicesCom;
    private JComboBox mJComboBox;
    private int mSelectedIndex;

    /**
     * Launch the application.
     */
    public EasyLinkSDK_Test() {

        initManager();

        registerListener();

        initGUI();

        initEvent();

    }

    // Initialize the interface manager
    private static void initManager() {
        sEasyLinkMagager = EasyLinkSdkManager.getInstance();
        sExecutorService = Executors.newFixedThreadPool(5);
        sDeviceInfo = new DeviceInfo(CommType.USB);
        // sDeviceInfo.setCommName("COM23");
    }

    // Register listener
    private static void registerListener() {
        sEasyLinkMagager.registerReportStatusListener(new ELReportStatusListener());

        sEasyLinkMagager.registerDisconnectStateListener(new ELDeviceStateChangeListener());
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initGUI() {
        setBounds(0, 30, 567, 742);
        setLayout(null);

        // Set the function interface label
        JLabel lblConntype = new JLabel("ConnType:");
        lblConntype.setBounds(34, 10, 78, 15);
        add(lblConntype);

        JLabel lblConnect = new JLabel("Connect:");
        lblConnect.setBounds(34, 74, 78, 15);
        add(lblConnect);

        JLabel lblFiledownload = new JLabel("FileDownload:");
        lblFiledownload.setBounds(34, 99, 78, 15);
        add(lblFiledownload);

        JLabel lblTranstype = new JLabel("TransType :");
        lblTranstype.setBounds(34, 182, 78, 15);
        add(lblTranstype);

        JLabel lblInputData = new JLabel("Input Data :");
        lblInputData.setBounds(34, 207, 78, 15);
        add(lblInputData);

        JLabel lblImpfunction = new JLabel("ImpFunction:");
        lblImpfunction.setBounds(34, 232, 88, 15);
        add(lblImpfunction);

        JLabel lblLoadParamFile = new JLabel("load param file you need!");
        lblLoadParamFile.setForeground(Color.RED);
        lblLoadParamFile.setBounds(159, 99, 211, 15);
        add(lblLoadParamFile);

        mRdbtnUsb = new JRadioButton("USB");
        mRdbtnUsb.setBounds(159, 6, 67, 23);
        add(mRdbtnUsb);

        mRdbtnBt = new JRadioButton("BT");
        mRdbtnBt.setBounds(301, 6, 54, 23);
        add(mRdbtnBt);

        mRdbtnSale = new JRadioButton("Sale");
        mRdbtnSale.setBounds(159, 178, 88, 23);
        add(mRdbtnSale);

        mRdbtnRefund = new JRadioButton("Refund");
        mRdbtnRefund.setBounds(248, 177, 78, 24);
        add(mRdbtnRefund);

        mRdbtnCashBack = new JRadioButton("CashBack");
        mRdbtnCashBack.setBounds(343, 177, 88, 24);
        add(mRdbtnCashBack);

        // input file path
        mTextFile = new JTextField();
        mTextFile.setBounds(159, 118, 248, 21);
        add(mTextFile);
        mTextFile.setColumns(20);

        // input parameter
        // mTextTLVData.setDocument(new JTextRegInput("^[0-9a-zA-Z]{0,1024}$"));

        mInputData = new JTextField();
        mInputData.setBounds(159, 201, 248, 21);
        add(mInputData);
        mInputData.setColumns(10);

        // show the function return code
        mResultsArea = new MainFrame("").getResultArea();

        mBtnConnect = new JButton("connect");
        mBtnConnect.setBounds(159, 70, 100, 23);
        add(mBtnConnect);

        mBtnDisconnect = new JButton("disconnect");
        mBtnDisconnect.setBounds(282, 70, 100, 23);
        add(mBtnDisconnect);

        mBtnScanFile = new JButton("browse");
        mBtnScanFile.setBounds(159, 149, 88, 23);
        add(mBtnScanFile);

        mBtnLoadFile = new JButton("loadFile");
        mBtnLoadFile.setBounds(270, 148, 100, 23);
        add(mBtnLoadFile);

        mBtnSetTransData = new JButton("setTransData");
        mBtnSetTransData.setBackground(Color.YELLOW);
        mBtnSetTransData.setBounds(159, 232, 111, 23);
        add(mBtnSetTransData);

        mBtnSetConfigData = new JButton("setConfigData");
        mBtnSetConfigData.setBackground(Color.YELLOW);
        mBtnSetConfigData.setBounds(286, 232, 121, 23);
        add(mBtnSetConfigData);

        mBtnInputAmount = new JButton("setByOneKey");
        mBtnInputAmount.setBackground(Color.ORANGE);
        mBtnInputAmount.setBounds(417, 259, 129, 23);
        add(mBtnInputAmount);

        mBtnGetTransData = new JButton("getTransData");
        mBtnGetTransData.setBackground(Color.YELLOW);
        mBtnGetTransData.setBounds(159, 259, 112, 23);
        add(mBtnGetTransData);

        mBtnGetConfigData = new JButton("getConfigData");
        mBtnGetConfigData.setBackground(Color.YELLOW);
        mBtnGetConfigData.setBounds(286, 259, 121, 23);
        add(mBtnGetConfigData);

        mBtnStarttrans = new JButton("startTransaction");
        mBtnStarttrans.setBackground(Color.green);
        mBtnStarttrans.setBounds(159, 292, 151, 23);
        add(mBtnStarttrans);

        mBtnCompletetrans = new JButton("completeTransaction");
        mBtnCompletetrans.setBackground(Color.green);
        mBtnCompletetrans.setBounds(320, 292, 164, 23);
        add(mBtnCompletetrans);

        mBtnGetPinBlock = new JButton("getPinBlock");
        mBtnGetPinBlock.setBounds(159, 325, 111, 23);
        add(mBtnGetPinBlock);

        mBtnSendcancle = new JButton("sendCancel");
        mBtnSendcancle.setBounds(286, 325, 111, 23);
        add(mBtnSendcancle);

        mBtnGetconnecteddevice = new JButton("getConnectedDevice");
        mBtnGetconnecteddevice.setBounds(286, 456, 167, 23);
        add(mBtnGetconnecteddevice);

        mBtnCalcmac = new JButton("calcMAC");
        mBtnCalcmac.setBounds(286, 358, 111, 23);
        add(mBtnCalcmac);

        mBtnIncreaseksn = new JButton("increaseKSN");
        mBtnIncreaseksn.setBounds(417, 358, 111, 23);
        add(mBtnIncreaseksn);

        mBtnEncryptdata = new JButton("encryptData");
        mBtnEncryptdata.setBounds(160, 358, 111, 23);
        add(mBtnEncryptdata);

        mBtnShowpage = new JButton("showPage");
        mBtnShowpage.setBounds(417, 391, 111, 23);
        add(mBtnShowpage);

        mBtnSetDebug = new JButton("setDebugMode");
        mBtnSetDebug.setBounds(286, 391, 121, 23);
        add(mBtnSetDebug);

        mBtnGetUSBDevice = new JButton("getUsbDevice");
        mBtnGetUSBDevice.setBounds(159, 456, 121, 23);
        add(mBtnGetUSBDevice);

        mBtnGetversion = new JButton("getVersion");
        mBtnGetversion.setBounds(159, 391, 111, 23);
        add(mBtnGetversion);

        mBtnWritekey = new JButton("writeKey");
        mBtnWritekey.setBounds(159, 423, 111, 23);
        add(mBtnWritekey);

        mBtnWriteTIK = new JButton("writeTIK");
        mBtnWriteTIK.setBounds(286, 423, 111, 23);
        add(mBtnWriteTIK);
        
        // PICC APIs ========
        mBtnPiccOpen = new JButton("piccOpen");
        mBtnPiccOpen.setBounds(159, 493, 111, 23);
        add(mBtnPiccOpen);
        
        mBtnPiccDetect = new JButton("piccDetect");
        mBtnPiccDetect.setBounds(286, 493, 111, 23);
        add(mBtnPiccDetect);
        
        mBtnPiccRemove = new JButton("piccRemove");
        mBtnPiccRemove.setBounds(417, 493, 111, 23);
        add(mBtnPiccRemove);
        
        mBtnPiccLight = new JButton("piccLight");
        mBtnPiccLight.setBounds(159, 523, 111, 23);
        add(mBtnPiccLight);
        
        mBtnPiccCmdExchange = new JButton("piccCmdExchange");
        mBtnPiccCmdExchange.setBounds(286, 523, 111, 23);
        add(mBtnPiccCmdExchange);
        
        mBtnPiccM1Authority = new JButton("piccM1Authority");
        mBtnPiccM1Authority.setBounds(417, 523, 111, 23);
        add(mBtnPiccM1Authority);
        
        mBtnPiccM1ReadBlock = new JButton("piccM1ReadBlock");
        mBtnPiccM1ReadBlock.setBounds(159, 553, 141, 23);
        add(mBtnPiccM1ReadBlock);
        
        mBtnPiccM1Operate = new JButton("picM1Operate");
        mBtnPiccM1Operate.setBounds(316, 553, 141, 23);
        add(mBtnPiccM1Operate);
        // PICC APIs End========
        

        mJComboBox = new JComboBox();
        mJComboBox.setBounds(159, 35, 93, 21);
        add(mJComboBox);

        JLabel lblComPort = new JLabel("USB COM:");
        lblComPort.setBounds(34, 38, 70, 15);
        addData2Box();
        add(lblComPort);

        mBtnRefresh = new Button("Refresh");
        mBtnRefresh.setBounds(282, 34, 65, 23);
        add(mBtnRefresh);

    }

    private void initEvent() {
        // choose COM Port
        mJComboBox.addItemListener(this);
        // communication mode
        mRdbtnUsb.addActionListener(this);
        mRdbtnUsb.setSelected(true);
        mRdbtnBt.addActionListener(this);
        // transaction type
        mRdbtnSale.addActionListener(this);
        mRdbtnSale.setSelected(true);
        mRdbtnRefund.addActionListener(this);
        mRdbtnCashBack.addActionListener(this);

        mBtnConnect.addActionListener(this);
        mBtnDisconnect.addActionListener(this);
        mBtnScanFile.addActionListener(this);
        mBtnLoadFile.addActionListener(this);
        mBtnSetTransData.addActionListener(this);
        mBtnSetConfigData.addActionListener(this);
        mBtnInputAmount.addActionListener(this);

        mBtnGetTransData.addActionListener(this);
        mBtnGetConfigData.addActionListener(this);
        mBtnStarttrans.addActionListener(this);
        mBtnCompletetrans.addActionListener(this);
        mBtnGetPinBlock.addActionListener(this);
        mBtnSendcancle.addActionListener(this);
        mBtnGetconnecteddevice.addActionListener(this);
        mBtnCalcmac.addActionListener(this);
        mBtnIncreaseksn.addActionListener(this);
        mBtnEncryptdata.addActionListener(this);
        mBtnShowpage.addActionListener(this);

        mBtnSetDebug.addActionListener(this);
        mBtnGetUSBDevice.addActionListener(this);
        mBtnGetversion.addActionListener(this);

        mBtnWritekey.addActionListener(this);
        mBtnWriteTIK.addActionListener(this);
        mBtnRefresh.addActionListener(this);
        
        mBtnPiccOpen.addActionListener(this);
        mBtnPiccDetect.addActionListener(this);
        mBtnPiccRemove.addActionListener(this);
        mBtnPiccLight.addActionListener(this);
        mBtnPiccCmdExchange.addActionListener(this);
        mBtnPiccM1Authority.addActionListener(this);
        mBtnPiccM1ReadBlock.addActionListener(this);
        mBtnPiccM1Operate.addActionListener(this);

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // TODO Auto-generated method stub
        if (e.getStateChange() == ItemEvent.SELECTED) {
            mSelectedIndex = mJComboBox.getSelectedIndex();
            System.out.println("mSelectedIndex = " + mSelectedIndex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Refresh":
                mJComboBox.removeAllItems();
                addData2Box();
                break;

            case "USB":
                mRdbtnUsb.setSelected(true);
                mRdbtnBt.setSelected(false);
                sDeviceInfo.setCommType(CommType.USB);
                System.out.println("USB......");
                LogUtils.debug(TAG, "CommType set USB");
                break;

            case "BT":
                mRdbtnBt.setSelected(true);
                mRdbtnUsb.setSelected(false);
                sDeviceInfo.setCommType(CommType.BLUETOOTH);
                System.out.println("BT......");
                LogUtils.debug(TAG, "CommType set USB");
                break;

            case "Sale":
                getTransType("Sale");
                break;

            case "Refund":
                getTransType("Refund");
                break;

            case "CashBack":
                getTransType("CashBack");
                break;

            case "connect":
                if (mDevicesCom == null) {
                    mResultsArea.setText("No Device detected!");
                    return;
                }
                String com = mDevicesCom.get(mSelectedIndex);
                // System.out.println("Set COM Port ： " + com);
                sDeviceInfo.setCommName(com);

                CompletableFuture.supplyAsync(() -> {
                    int connectCode = sEasyLinkMagager.connect(sDeviceInfo);
                    boolean isConnected = sEasyLinkMagager.isConnected();
                    return "Connect : \r\n the return code is : " + connectCode
                            + "\r\n the connected status is : " + isConnected;
                }, sExecutorService)
                        .thenAccept(connectResult -> mResultsArea.setText(connectResult));

                LogUtils.debug(TAG, "connect was clicked!");
                break;

            case "disconnect":
                CompletableFuture.supplyAsync(() -> {
                    int disconnectCode = sEasyLinkMagager.disconnect();
                    boolean isConnected = sEasyLinkMagager.isConnected();
                    return "DisConnect: \r\n the return code is: " + disconnectCode
                            + "\r\n the connected status is : " + isConnected;
                }, sExecutorService)
                        .thenAccept(disconnectResult -> mResultsArea.setText(disconnectResult));

                LogUtils.debug(TAG, "disconnect was clicked!");
                break;

            case "browse":
                browseFile();

                LogUtils.debug(TAG, "browse was clicked!");
                break;

            case "loadFile":
                fileDownload();

                LogUtils.debug(TAG, "loadFile was clicked!");
                break;

            case "setTransData":
                String transTLVData = getInputContent();
                setDataBefore(transTLVData, DataType.TRANSACTION_DATA);

                LogUtils.debug(TAG, "setTransData was clicked!");
                break;

            case "setConfigData":
                String setConfigTLVData = getInputContent();
                setDataBefore(setConfigTLVData, DataType.CONFIGURATION_DATA);

                LogUtils.debug(TAG, "setConfigData was clicked!");
                break;

            case "setByOneKey":
                String inputAmount = getInputContent();
                String createData = createTransData(inputAmount);
                setDataBefore(createData, DataType.TRANSACTION_DATA);

                LogUtils.debug(TAG, "inputAmount was clicked!");
                break;

            case "getTransData":
                String getTransTLVData = getInputContent();
                getDataAfter(getTransTLVData, DataType.TRANSACTION_DATA);

                LogUtils.debug(TAG, "getTransData was clicked!");
                break;

            case "getConfigData":
                String getConfigTLVData = getInputContent();;
                getDataAfter(getConfigTLVData, DataType.CONFIGURATION_DATA);

                LogUtils.debug(TAG, "getConfigData was clicked!");
                break;

            case "startTransaction":
                CompletableFuture
                        .supplyAsync(() -> sEasyLinkMagager.startTransaction(), sExecutorService)
                        .thenAccept(
                                getResultCode -> mResultsArea.append("\r\n \r\n StartTransaction"
                                        + ": \r\n the result code is : " + getResultCode));

                LogUtils.debug(TAG, "startTrans was clicked!");
                break;

            case "completeTransaction":
                CompletableFuture
                        .supplyAsync(() -> sEasyLinkMagager.completeTransaction(), sExecutorService)
                        .thenAccept(
                                getResultCode -> mResultsArea.append("\r\n \r\n CompleteTransaction"
                                        + ": \r\n the result code is : " + getResultCode));

                LogUtils.debug(TAG, "completeTrans was clicked!");
                break;

            case "getPinBlock":
                getPINBlock();

                LogUtils.debug(TAG, "getPinBlock was clicked!");
                break;

            case "sendCancel":
                // send cancel while startTransaction/getPinBlock/completeTransaction
                CompletableFuture.supplyAsync(() -> sEasyLinkMagager.sendCancel(), sExecutorService)
                        .thenAccept(getResultCode -> mResultsArea.append("\r\n \r\n SendCancel"
                                + ": \r\n the result code is : " + getResultCode));

                LogUtils.debug(TAG, "sendCancel was clicked!");
                break;

            case "writeKey":

                CompletableFuture.supplyAsync(() -> writeKey(), sExecutorService)
                        .thenAccept(writeKeyCode -> mResultsArea
                                .setText("WriteKey : \r\n the return code is :" + writeKeyCode));

                LogUtils.debug(TAG, "writeKey was clicked!");

                break;

            case "writeTIK":

                int writeTIKCode = writeTIK();
                mResultsArea.setText("WriteTIK : \r\n the return code is :" + writeTIKCode);

                LogUtils.debug(TAG, "writeTIK was clicked!");
                break;

            case "setDebugMode":
                // Set true if you want to see the sdk log, default is false. Please set false when
                // you publish a project.
                String inputContent = getInputContent();
                Boolean value = Boolean.valueOf(inputContent);
                sEasyLinkMagager.setDebugMode(value);
                mResultsArea.setText("SetDebugMode :"
                        + "\r\n there is no return code! \r\n the debug mode value is : " + value);

                LogUtils.debug(TAG, "setDebugMode was clicked!");
                break;

            case "getUsbDevice":
                List<DeviceInfo> usbDevice = sEasyLinkMagager.getUsbDevice();
                System.out.println("usbDevice:" + usbDevice);

                if (usbDevice == null) {
                    mResultsArea.setText("there is no device connected by USB!");
                } else {
                    for (DeviceInfo deviceInfo : usbDevice) {
                        mResultsArea.append(
                                "\r\n the USB device was found \r\n : " + deviceInfo.getCommName());
                    }
                }

                LogUtils.debug(TAG, "getUsbDevice was clicked!");
                break;

            case "getVersion":
                String versionName = sEasyLinkMagager.getVersionName();
                int versionCode = sEasyLinkMagager.getVersionCode();
                mResultsArea.setText("the SDK version name is : \r\n" + versionName
                        + "\r\n the SDK version code is : \r\n" + versionCode);

                LogUtils.debug(TAG, "getVersion was clicked!");
                break;

            case "getConnectedDevice":
                DeviceInfo connectedDevice = sEasyLinkMagager.getConnectedDevice();
                if (connectedDevice == null) {
                    mResultsArea.setText("there is no device connected by USB!");
                } else {
                    mResultsArea.setText("the device connected by USB is : \r\n"
                            + connectedDevice.getDeviceName());
                }

                LogUtils.debug(TAG, "getConnectedDevice was clicked!");
                break;

            case "calcMAC":
                String inputMacData = getInputContent();
                caclMAC(inputMacData);

                LogUtils.debug(TAG, "calcMAC was clicked!");
                break;

            case "increaseKSN":
                CompletableFuture.supplyAsync(() -> increaseKSN(), sExecutorService)
                        .thenAccept(result -> mResultsArea.setText("IncreaseKSN : \r\n" + result));

                LogUtils.debug(TAG, "increaseKSN was clicked!");
                break;

            case "encryptData":
                // String toEncryptData = getInputContent();
                String toEncryptData = mInputData.getText().toString().trim();
                encryptData(toEncryptData);

                LogUtils.debug(TAG, "encryptData was clicked!");
                break;

            case "showPage":
                String pageName = getInputContent();
                CompletableFuture.supplyAsync(() -> showPage(pageName), sExecutorService)
                        .thenAccept(showResults -> mResultsArea.setText(showResults));

                LogUtils.debug(TAG, "showPage was clicked!");
                break;
            
            case "piccOpen":
            	 CompletableFuture
                 .supplyAsync(() -> sEasyLinkMagager.piccOpen(), sExecutorService)
                 .thenAccept(
                         getResultCode -> mResultsArea.append("\r\n \r\n piccOpen"
                                 + ": \r\n the result code is : " + getResultCode));

            	 LogUtils.debug(TAG, "piccOpen was clicked!");
            	break;
            	
            case "piccDetect":
            	{
            		int ret = 0;
                	
                	PiccCardInfo outPiccCardInfo = new PiccCardInfo();
                	
                	while (true) {
                		
                		CompletableFuture
                        .supplyAsync(() -> sEasyLinkMagager.piccDetect(EDetectMode.ISO14443_AB, outPiccCardInfo), sExecutorService)
                        .thenAccept(
                                getResultCode -> mResultsArea.append("\r\n \r\n piccDetect"
                                        + ": \r\n the result code is : " + getResultCode));
                		
//                		ret = sEasyLinkMagager.piccDetect(EDetectMode.ISO14443_AB, outPiccCardInfo);
                		LogUtils.debug(TAG, "piccDetect = " + ret);
//                		mResultsArea.append("\rn \r\npiccDetect = " + ret);
                		if (ret == 0)
                		{
                			break;
                		}
                		try {
            				Thread.sleep(500);
            			} catch (InterruptedException e1) {
            				// TODO Auto-generated catch block
            				e1.printStackTrace();
            			}
                	}
            	}
            	break;
            	
            case "piccLight":
            	CompletableFuture
            	.supplyAsync(() -> sEasyLinkMagager.piccLight((byte)0x01, ELedStatus.ON), sExecutorService)
                .thenAccept(
                        getResultCode -> mResultsArea.append("\r\n \r\n piccLight"
                                + ": \r\n the result code is : " + getResultCode));

                LogUtils.debug(TAG, "piccLight was clicked!");
                break;
           	
            case "piccClose":
	           	 CompletableFuture
	                .supplyAsync(() -> sEasyLinkMagager.piccClose(), sExecutorService)
	                .thenAccept(
	                        getResultCode -> mResultsArea.append("\r\n \r\n piccClose"
	                                + ": \r\n the result code is : " + getResultCode));
	
	           	 LogUtils.debug(TAG, "piccClose was clicked!");
	           	 break;

            default:
                break;
        }

    }

    private void addData2Box() {
        List<DeviceInfo> usbDevices = sEasyLinkMagager.getUsbDevice();
        mDevicesCom = new ArrayList<>();
        if (usbDevices != null) {
            for (DeviceInfo deviceInfo : usbDevices) {
                mDevicesCom.add(deviceInfo.getCommName());
            }
        }

        if (mDevicesCom.size() == 0) {
            mJComboBox.addItem("NULL");
        } else {
            for (String com : mDevicesCom) {
                mJComboBox.addItem(com);
            }
        }
    }

    // Encrypt the input data
    private void encryptData(String toEncryptData) {
        byte[] dataByte = Convert.strToBcd(toEncryptData, EPaddingPosition.PADDING_RIGHT);
        ByteArrayOutputStream encryptDataValue = new ByteArrayOutputStream();
        CompletableFuture.supplyAsync(() -> {
            int encryptResult = sEasyLinkMagager.encryptData(dataByte, encryptDataValue);
            String encryptData = Convert.bcdToStr(encryptDataValue.toByteArray());
            return "the encryptData return code is : " + encryptResult
                    + "\r\n the encryptData is : \r\n" + encryptData;
        }, sExecutorService)
                .thenAccept(encryptDataResult -> mResultsArea.setText(encryptDataResult));
    }

    // Find files from the local file system
    private void browseFile() {
        FileDialog openDialog = new FileDialog(mframe, "open", FileDialog.LOAD);
        openDialog.setLocationRelativeTo(null);
        openDialog.setVisible(true); // Displays the file dialog

        String dirpath = openDialog.getDirectory(); // Get the path of the file and saves it to a
                                                    // string.
        String fileName = openDialog.getFile(); // Get the file name and saves it to a string

        if (dirpath == null || fileName == null) // Determine whether the path and file are empty
            return;
        File file = new File(dirpath, fileName); // Create a new path and name
        mTextFile.setText(file.getAbsolutePath());

    }

    // Download the file in the file query box
    private void fileDownload() {
        String filePath = mTextFile.getText().toString().trim();
        // File file = new File(filePath);
        // if (!file.exists()) {
        // JOptionPane.showMessageDialog(null, "文件不存在 ...", "警告", JOptionPane.ERROR_MESSAGE);
        // return;
        // }
        CompletableFuture
                .supplyAsync(
                        () -> sEasyLinkMagager.fileDownLoad(filePath, new ELFileDownloadListener()),
                        sExecutorService)
                .thenAccept(resultCode -> mResultsArea.setText("the file absolute path is : \r\n"
                        + filePath + "\r\n the file download return code is : " + resultCode));
    }

    private void getTransType(String transType) {
        mRdbtnSale.setSelected(false);
        mRdbtnRefund.setSelected(false);
        mRdbtnCashBack.setSelected(false);

        switch (transType) {
            case "Sale":
                mRdbtnSale.setSelected(true);
                mTransType = "Sale";
                break;

            case "Refund":
                mRdbtnRefund.setSelected(true);
                mTransType = "Refund";
                break;

            case "CashBack":
                mRdbtnCashBack.setSelected(true);
                mTransType = "CashBack";
                break;

            default:
                break;
        }

    }

    // dataType,there is two type of the parameter，
    // DataType.TRANSACTION_DATA,DataType.CONFIGURATION_DATA
    private void setDataBefore(String data, DataType dataType) {
        byte[] tlvData = Convert.strToBcd(data, EPaddingPosition.PADDING_RIGHT);
        String setType =
                (dataType == DataType.TRANSACTION_DATA) ? "setTransData :" : "setConfigData :";
        ByteArrayOutputStream resultDataStream = new ByteArrayOutputStream();
        CompletableFuture.supplyAsync(() -> {
            int resultCode = sEasyLinkMagager.setData(dataType, tlvData, resultDataStream);
            String setData = Convert.bcdToStr(resultDataStream.toByteArray());
            return "\r\n the return code is : " + resultCode + "\r\n the setData is : " + data
                    + "\r\n the err data is : " + setData;
        }, sExecutorService).thenAccept(setResult -> mResultsArea.setText(setType + setResult));
    }

    // dataType,there is two type of the parameter，
    // DataType.TRANSACTION_DATA,DataType.CONFIGURATION_DATA
    private void getDataAfter(String data, DataType dataType) {
        byte[] tlvData = Convert.strToBcd(data, EPaddingPosition.PADDING_RIGHT);
        String getType =
                (dataType == DataType.TRANSACTION_DATA) ? "getTransData :" : "getConfigData :";
        ByteArrayOutputStream resultData = new ByteArrayOutputStream();
        CompletableFuture.supplyAsync(() -> {
            int resultCode = sEasyLinkMagager.getData(dataType, tlvData, resultData);
            String getData = Convert.bcdToStr(resultData.toByteArray());
            return "\r\n the return code is : " + resultCode + "\r\n the data is : " + getData;
        }, sExecutorService).thenAccept(getResult -> mResultsArea.setText(getType + getResult));
    }

    // To get PINBLOCK, please invoke setData API to set TAG 0202
    // (pinEncryptionType) and TAG 0203 (pinEncryptionKeyIdx) first.
    private void getPINBlock() {
        String pan = getInputContent();;
        ByteArrayOutputStream pinBlock = new ByteArrayOutputStream();
        ByteArrayOutputStream ksn = new ByteArrayOutputStream();
        int getPinBlockCode = sEasyLinkMagager.getPinBlock(pan, pinBlock, ksn);
        String getPinBlock = Convert.bcdToStr(pinBlock.toByteArray());
        String getKsn = Convert.bcdToStr(ksn.toByteArray());
        String PinText = "GetPinBlock: \r\n the return code is : " + getPinBlockCode
                + "\r\n the PinBlock is : " + getPinBlock + "\r\n the Ksn is : " + getKsn;
        mResultsArea.setText("\r\n \r\n " + PinText);
    }

    // This API is used to calculate MAC. To encrypt data, please invoke setData API to set TAG
    // 0210 (macKeyIdx) and TAG 0212 (macKeyType) first.
    private void caclMAC(String data) {
        byte[] dataArrays = Convert.strToBcd(data, Convert.EPaddingPosition.PADDING_RIGHT);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // Whether or not to start new thread
        int resultCode = sEasyLinkMagager.calcMAC(dataArrays, outputStream);
        String output = Convert.bcdToStr(outputStream.toByteArray());
        mResultsArea.setText("CaclMAC : \r\n the return code is : " + resultCode
                + "\r\n the data is : \r\n" + output);
    }

    private String createTransData(String data) {
        String transAmount = "";
        String transType = "";
        String backAmount = "";
        transAmount = String.format("%012d", Integer.parseInt(data));

        switch (mTransType) {
            case "Sale":
                transType = "00";
                backAmount = "";
                break;
            case "Refund":
                transType = "20";
                backAmount = "";
                break;

            case "CashBack":
                transType = "09";
                backAmount = "9F0306" + String.format("%012d", 500);
                break;
        }

        SimpleDateFormat df = new SimpleDateFormat("yyMMdd-HHmmss");// 设置日期格式
        String formatDate = df.format(new Date());
        String[] date = formatDate.split("-");

        transAmount = "9F0206" + transAmount + "5F2A0208405F3601029A03" + date[0] + "9F2103"
                + date[1] + "9C01" + transType + backAmount;
        return transAmount;
    }

    private String getInputContent() {
        String inputContent = mInputData.getText().toString().trim();
        // if (inputContent.isEmpty()) {
        // JOptionPane.showMessageDialog(mContentPane, "PLS Input Data...", "InputData",
        // JOptionPane.WARNING_MESSAGE);
        // return null;
        // }
        return inputContent;
    }

    // get the index of the input data, and converts it to byte
    private String increaseKSN() {
        String index = getInputContent();
        byte groupIndex = Byte.valueOf(index).byteValue();
        int resultCode = sEasyLinkMagager.increaseKSN(groupIndex);

        byte[] tag = Convert.strToBcd("0202020502030206", EPaddingPosition.PADDING_RIGHT);
        ByteArrayOutputStream resultData = new ByteArrayOutputStream();
        sEasyLinkMagager.getData(DataType.CONFIGURATION_DATA, tag, resultData);
        String valueStr = Convert.bcdToStr(resultData.toByteArray());

        return "the return code is : " + resultCode + "\r\n the tag value is :" + valueStr;
    }

    private String showPage(String data) {
        String results = "";
        ArrayList<ShowPageInfo> showPageInfo = new ArrayList<>();
        UIRespInfo uiRespInfo = new UIRespInfo();
        int showPageCode = sEasyLinkMagager.showPage(data, 5000, showPageInfo, uiRespInfo);
        if (uiRespInfo.getActionData() == null) {
            results = "ShowPage : \r\n actionData=null \r\n the return code is :" + showPageCode
                    + "\r\n UIName: " + uiRespInfo.getType() + "\r\n UIData : null";
        } else {
            results = "ShowPage : \r\n the return code is :" + showPageCode + "\r\n UIName: "
                    + uiRespInfo.getType() + "\r\n UIData : "
                    + Convert.bcdToStr(uiRespInfo.getActionData());
        }

        return results;
    }

    private int writeKey() {
        /*
         * KeyInfo keyInfo = new KeyInfo(); keyInfo.setSrcKeyType(KeyType.PED_TMK); //
         * keyInfo.setSrcKeyIndex((byte) 11); keyInfo.setSrcKeyIndex((byte) 0); // clear injection
         * 
         * keyInfo.setDstKeyType(KeyType.PED_TPK); keyInfo.setDstKeyIndex((byte) 12);
         * keyInfo.setDstKeyValue(Convert.strToBcd("38D79AD1486FB7ABD25B6716BA8FE1C7",
         * EPaddingPosition.PADDING_LEFT)); keyInfo.setDstKeyLen((byte) 16);
         */
        KeyInfo keyInfo = new KeyInfo();
        keyInfo.setSrcKeyType(KeyType.PED_TLK);
        keyInfo.setSrcKeyIndex((byte) 0x00);
        keyInfo.setDstKeyType(KeyType.PED_TPK);
        keyInfo.setDstKeyIndex((byte) 0x0c);
        keyInfo.setDstKeyValue(Convert.strToBcd("38D79AD1486FB7ABD25B6716BA8FE1C7",
                EPaddingPosition.PADDING_LEFT));
        keyInfo.setDstKeyLen((byte) 16);

        KcvInfo kcvInfo = new KcvInfo(); // kcvInfo.setCheckMode(2);
        byte[] checkBuf = new byte[112]; // checkBuf[0] = 4;
        byte[] kcvData = Convert.strToBcd("92CBB1D2013EADA7", EPaddingPosition.PADDING_LEFT);
        System.arraycopy(kcvData, 0, checkBuf, 1, kcvData.length);
        kcvInfo.setCheckMode(0x00);
        // byte[] checkBuf = null;
        kcvInfo.setCheckBuf(checkBuf);

        int writeKeyCode = sEasyLinkMagager.writeKey(keyInfo, kcvInfo, 6);
        return writeKeyCode;
    }

    private int writeTIK() {
        byte[] keyValueIn =
                Convert.strToBcd("6AC292FAA1315B4D858AB3A3D7D5933A", EPaddingPosition.PADDING_LEFT);
        byte[] ksnIn = Convert.strToBcd("FFFF9876543210E00000", EPaddingPosition.PADDING_RIGHT);
        KcvInfo kcvInfoIn = new KcvInfo();
        kcvInfoIn.setCheckMode(0x00);
        kcvInfoIn.setCheckBuf(new byte[1]);

        // what is group index
        int writeTIKCode =
                sEasyLinkMagager.writeTIK((byte) 3, (byte) 0, keyValueIn, ksnIn, kcvInfoIn, 6);
        return writeTIKCode;
    }
    
    private int piccLight() {
    	int ret = 0;
    	
    	ret = sEasyLinkMagager.piccLight((byte)0x01, ELedStatus.ON);
//    	ret = sEasyLinkMagager.piccLight((byte)0x02, ELedStatus.ON);
//    	ret = sEasyLinkMagager.piccLight((byte)0x03, ELedStatus.ON);
//    	ret = sEasyLinkMagager.piccLight((byte)0x04, ELedStatus.ON);
    	
    	return ret;
    }
    
    private int piccDetect() {
    	int ret = 0;
    	
    	PiccCardInfo outPiccCardInfo = new PiccCardInfo();
    	
    	while (true) {
    		ret = sEasyLinkMagager.piccDetect(EDetectMode.ISO14443_AB, outPiccCardInfo);
    		if (ret == 0)
    		{
    			break;
    		}
    		try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	return ret;
    }

}

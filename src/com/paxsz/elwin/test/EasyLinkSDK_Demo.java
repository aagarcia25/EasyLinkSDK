
package com.paxsz.elwin.test;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

import com.paxsz.easylink.api.EasyLinkSdkManager;
import com.paxsz.easylink.device.DeviceInfo;
import com.paxsz.easylink.device.DeviceInfo.CommType;
import com.paxsz.easylink.model.DataModel.DataType;
import com.paxsz.elwin.listener.ELDeviceStateChangeListener;
import com.paxsz.elwin.listener.ELReportStatusListener;
import com.paxsz.elwin.utils.Convert;
import com.paxsz.elwin.utils.Convert.EPaddingPosition;
import com.paxsz.elwin.utils.LogUtils;

/**
 * ClassName:EasyLinkWindowsDemo <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2019年5月15日 下午9:00:28 <br/>
 * 
 * @author zhubenhui
 * @version
 */
public class EasyLinkSDK_Demo extends JPanel implements ActionListener, ItemListener {
	public static final String TAG = "EasyLinkSDK_Demo";
    String mTransType = "Sale";
    private static EasyLinkSdkManager mEasyLinkSdkManager;
    private ExecutorService sExecutorService;
    private JButton mBtnConnect;
    private JButton mBtnDisconnect;
    private JButton mBtnDotransaction;
    private JRadioButton mRdbtnSale;
    private JRadioButton mRdbtnRefund;
    private JRadioButton mRdbtnCashback;
    private JTextField mTextAmount;
    private JTextArea mTransResultsArea;
    private JLabel lblReloj;
    private CompletableFuture mFuture;

    private DeviceInfo mDeviceInfo;
    private JComboBox mJComboBox;
    private List<String> mDevicesCom;
    private Button mBtnRefresh;
    private int mSelectedIndex;

    /**
     * @wbp.nonvisual location=80,47
     */

    /**
     * Create the application.
     * 
     * @wbp.parser.entryPoint
     */
    public EasyLinkSDK_Demo() {
    	initClock();
        initManager();

        registerListener();

        initJPanel();

        initEvent();
    }

    private void initManager() {
        mEasyLinkSdkManager = EasyLinkSdkManager.getInstance();
        sExecutorService = Executors.newFixedThreadPool(5);
    }

    // Register listener
    private static void registerListener() {
        mEasyLinkSdkManager.registerReportStatusListener(new ELReportStatusListener());

        mEasyLinkSdkManager.registerDisconnectStateListener(new ELDeviceStateChangeListener());
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initJPanel() {
        setBounds(0, 30, 567, 542);
        setLayout(null);

        JLabel lblPlsConnectDevice = new JLabel("PLS Choose COM First!");
        lblPlsConnectDevice.setBounds(30, 10, 227, 15);
        add(lblPlsConnectDevice);
        
        lblReloj = new JLabel("Iniciando Reloj");
        lblReloj.setBounds(250, 10, 227, 15);
        add(lblReloj);


        mJComboBox = new JComboBox();
        mJComboBox.setBounds(45, 33, 93, 20);
        addData2Box();
        add(mJComboBox);

        mBtnRefresh = new Button("Refresh");
        mBtnRefresh.setBounds(170, 33, 65, 20);
        add(mBtnRefresh);

        mBtnConnect = new JButton("connect");
        mBtnConnect.setBounds(45, 60, 93, 23);
        add(mBtnConnect);

        mBtnDisconnect = new JButton("disconnect");
        mBtnDisconnect.setBounds(170, 60, 122, 23);
        add(mBtnDisconnect);

        JLabel lblTransactionType = new JLabel("Transaction Type : ");
        lblTransactionType.setBounds(30, 89, 154, 15);
        add(lblTransactionType);

        mRdbtnSale = new JRadioButton("sale");
        mRdbtnSale.setBounds(40, 105, 55, 23);
        add(mRdbtnSale);

        mRdbtnRefund = new JRadioButton("refund");
        mRdbtnRefund.setBounds(117, 105, 67, 23);
        add(mRdbtnRefund);

        mRdbtnCashback = new JRadioButton("cashBack");
        mRdbtnCashback.setBounds(205, 105, 121, 23);
        add(mRdbtnCashback);

        JLabel lblPleaseInputAny = new JLabel("PLS Input Amount!");
        lblPleaseInputAny.setBounds(30, 134, 238, 15);
        add(lblPleaseInputAny);
        

        mTextAmount = new JTextField();
        mTextAmount.setBounds(40, 159, 280, 21);
        add(mTextAmount);
        mTextAmount.setColumns(10);

        mBtnDotransaction = new JButton("doTransaction");
        mBtnDotransaction.setBounds(40, 190, 300, 23);
        add(mBtnDotransaction);

        mTransResultsArea = new MainFrame("").getResultArea();

    }

    private void initClock() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String currentTime = sdf.format(new Date());
                lblReloj.setText(currentTime);
            }
        });
        timer.start();
    }
    
    private void initEvent() {
        mBtnConnect.addActionListener(this);
        mBtnDisconnect.addActionListener(this);
        mBtnDotransaction.addActionListener(this);
        mRdbtnSale.addActionListener(this);
        mRdbtnRefund.addActionListener(this);
        mRdbtnCashback.addActionListener(this);
        mRdbtnSale.setSelected(true);
        mJComboBox.addItemListener(this);
        mBtnRefresh.addActionListener(this);
        mDeviceInfo = new DeviceInfo(CommType.USB);

        // mDeviceInfo.setCommName("COM23");
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

            case "sale":
                getTransType("sale");
                break;

            case "refund":
                getTransType("refund");
                break;

            case "cashBack":
                getTransType("cashBack");
                break;

            case "connect":
                if (mDevicesCom == null) {
                    mTransResultsArea.setText("No Device detected!");
                    return;
                }
                String com = mDevicesCom.get(mSelectedIndex);
                // System.out.println("Set COM Port ： " + com);
                mDeviceInfo.setCommName(com);

                CompletableFuture
                        .supplyAsync(() -> mEasyLinkSdkManager.connect(mDeviceInfo),
                                sExecutorService)
                        .thenAccept(getResultCode -> mTransResultsArea.setText(
                                "Connect" + ": \r\n the result code is : " + getResultCode));
                break;

            case "disconnect":
                CompletableFuture.supplyAsync(() -> {
                    int disconnectCode = mEasyLinkSdkManager.disconnect();
                    boolean isConnected = mEasyLinkSdkManager.isConnected();
                    return "DisConnect: \r\n the return code is: " + disconnectCode
                            + "\r\n the connected status is : " + isConnected;
                }, sExecutorService).thenAccept(
                        disconnectResult -> mTransResultsArea.setText(disconnectResult));

                break;

            case "doTransaction":
                doTransaction();
                break;

            default:
                break;
        }
    }

    private void getTransType(String transType) {
        mRdbtnSale.setSelected(false);
        mRdbtnRefund.setSelected(false);
        mRdbtnCashback.setSelected(false);

        switch (transType) {
            case "sale":
                mRdbtnSale.setSelected(true);
                mTransType = "Sale";
                break;

            case "refund":
                mRdbtnRefund.setSelected(true);
                mTransType = "Refund";
                break;

            case "cashBack":
                mRdbtnCashback.setSelected(true);
                mTransType = "CashBack";
                break;

            default:
                break;
        }

    }

    private void addData2Box() {
        List<DeviceInfo> usbDevices = mEasyLinkSdkManager.getUsbDevice();
        mDevicesCom = new ArrayList<String>();
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

    private void doTransaction() {
        long startTime = System.currentTimeMillis();
        String inputAmount = mTextAmount.getText().toString().trim();

        if (inputAmount.isEmpty()) {
            JOptionPane.showMessageDialog(null, "PLS Input Data...", "InputData",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String transData = createTransData(inputAmount);

        mFuture = CompletableFuture
                .supplyAsync(() -> setDataBefore(transData, DataType.TRANSACTION_DATA),
                        sExecutorService)
                .thenApplyAsync(setDataPrompts -> {
                    return setDataPrompts + getDataAfter("9F02", DataType.TRANSACTION_DATA);
                }, sExecutorService).thenApplyAsync(getDataPrompts -> {
                    return getDataPrompts + startTransAction();
                }, sExecutorService).thenApplyAsync(getAfterTrans -> {
                    return getAfterTrans + getTag0301();
                }, sExecutorService).thenApplyAsync(getAfter0301 -> {
                    return getAfter0301 + getDataAfter("031A", DataType.CONFIGURATION_DATA);
                }, sExecutorService).thenApplyAsync(getCVMPrompts -> {
                    return getCVMPrompts + getDataAfter("031B", DataType.CONFIGURATION_DATA);
                }, sExecutorService).thenApplyAsync(getOnlinePrompts -> {
                    return getOnlinePrompts
                            + setDataBefore("03080100", DataType.CONFIGURATION_DATA);
                }, sExecutorService).thenApplyAsync(setOnlineAut -> {
                    return setOnlineAut + completeTransaction();
                }).thenAccept(resultPrompts -> {
                    long endTime = System.currentTimeMillis();
                    long usedTime = endTime - startTime;
                    LogUtils.debug(TAG, resultPrompts);
                    // Verificar el resultado final
                    String resultMessage;
                    if (resultPrompts.contains("code is : 0")) {
                        resultMessage = "Transacción exitosa";
                        
                    } else {
                        resultMessage = "Transacción fallida, favor de revisar los resultados en el área";
                    }

                    // Mostrar el mensaje y los resultados de la transacción
                    mTransResultsArea.setText("EMV FLOW : " + resultPrompts
                            + "\r\n the whole transaction used time is : " + usedTime + " ms");
                    JOptionPane.showMessageDialog(null, resultMessage, "Resultado de la Transacción", 
                            JOptionPane.INFORMATION_MESSAGE);
                });
        
        
        
    }

    // get CurrentTxnType,the tag is 0301,
    // if the value is 0x01 or 0x02,it is not need completeTransaction.
    private String getTag0301() {

        byte[] tag = Convert.strToBcd("0301", EPaddingPosition.PADDING_RIGHT);
        ByteArrayOutputStream resultData = new ByteArrayOutputStream();
        int data = mEasyLinkSdkManager.getData(DataType.CONFIGURATION_DATA, tag, resultData);
        if (data != 0) {
            return "get tag(0301) value failed !";
        }
        String getDataValue = Convert.bcdToStr(resultData.toByteArray());
        // the length of return code should be correct
        if (getDataValue == null || getDataValue.isEmpty() || getDataValue.length() <= 10) {
            // cancel
            return data + "--get  value  is not correct!";
        }

        String valueStr = getDataValue.substring(getDataValue.length() - 2, getDataValue.length());
        if (valueStr.equals("01") || valueStr.equals("02")) {
            // 01:Magnetic Swipe Card
            // 02Fallback Swipe Card
            // cancel
            try {
                mFuture.cancel(true);
                System.out.println("future is cancelled: " + mFuture.isCancelled());
            } catch (Exception e) {
                LogUtils.error("0301", "there is a exception for task cancel!");
            }

            return "\r\n the value of 0301 is : " + getDataValue
                    + "\r\n the CurrentTxnType is not need completeTransaction";
        }
        return "\r\n the value of 0301 is : " + getDataValue
                + "\r\n the CurrentTxnType need completeTransaction!";
    }

    private String completeTransaction() {
        int completeTransactionCode = mEasyLinkSdkManager.completeTransaction();
        return "\r\n \r\n the completeTransaction return code is : " + completeTransactionCode;
    }

    private String startTransAction() {
        int startTransactionCode = mEasyLinkSdkManager.startTransaction();
        return "\r\n \r\n the startTransaction return code is :" + startTransactionCode;
    }

    private String setDataBefore(String data, DataType dataType) {
        byte[] tlvData = Convert.strToBcd(data, EPaddingPosition.PADDING_RIGHT);
        ByteArrayOutputStream resultDataStream = new ByteArrayOutputStream();
        int setDataCode = mEasyLinkSdkManager.setData(dataType, tlvData, resultDataStream);
        String setErrCode = Convert.bcdToStr(resultDataStream.toByteArray());

        return "\r\n \r\n the data is : \r\n" + data + "\r\n the setData return code is : "
                + setDataCode + "\r\n If the code is not 0,the error code is : " + setErrCode;
    }

    private String getDataAfter(String data, DataType dataType) {
        byte[] tlvData = Convert.strToBcd(data, EPaddingPosition.PADDING_RIGHT);
        ByteArrayOutputStream resultData = new ByteArrayOutputStream();
        int getDataCode = mEasyLinkSdkManager.getData(dataType, tlvData, resultData);
        String getDataValue = Convert.bcdToStr(resultData.toByteArray());

        return "\r\n \r\n the getData return code is : " + getDataCode + "\r\n the getData tag is :"
                + data + ", the value is : " + getDataValue;
    }

    public String createTransData(String data) {
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
                backAmount = "9F0306" + transAmount;
                break;
        }

        SimpleDateFormat df = new SimpleDateFormat("yyMMdd-HHmmss");// 设置日期格式
        String formatDate = df.format(new Date());
        String[] date = formatDate.split("-");

        transAmount = "9F0206" + transAmount + "5F2A0208405F3601029A03" + date[0] + "9F2103"
                + date[1] + "9C01" + transType + backAmount;
        return transAmount;
    }

}

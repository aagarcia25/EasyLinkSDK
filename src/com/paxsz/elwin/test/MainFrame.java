
package com.paxsz.elwin.test;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;

import com.paxsz.easylink.api.EasyLinkSdkManager;
import com.paxsz.elwin.utils.LogUtils;
import com.paxsz.elwin.utils.LogUtils.Level;

/**
 * ClassName:MainFrame <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2019年6月17日 上午10:50:15 <br/>
 * 
 * @author zhubenhui
 * @version
 */
public class MainFrame implements ActionListener {

    private static final String TAG = "MainFrame";
    private JButton mJBtnDemo;
    private JButton mJBtnVersion;
    private JButton mJBtnHelp;
    private Container mContentPane;
    private JFrame mJFrame;
    private JButton mJBtnTest;
    private EasyLinkSDK_Demo mPanelDemo;
    private EasyLinkSDK_Test mPanelTest;
    private static JTextArea mResultsArea;
    private JScrollPane mJSPResult;
    private static JTextArea mMReportsArea;
    private JScrollPane mMJSPReport;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame window = new MainFrame();
                    window.mJFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainFrame() {
        windowInit();

        componentInit();

        jPanelInit();

        eventInit();
    }

    /**
     * Avoid repeated initialization.
     */
    public MainFrame(String message) {}

    /**
     * Initialize the frame.
     */
    private void windowInit() {
        mJFrame = new JFrame();
        mJFrame.setSize(900, 900);//(900, 600);
        mJFrame.setLocationRelativeTo(null); // Centered relative to the screen
        mJFrame.setResizable(false); // Cannot change frame size
        mJFrame.setTitle("EasyLinkWinSDK");

        Image image =
                Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/res/logo.png"));
        mJFrame.setIconImage(image);

        mContentPane = getContentPane();
        mContentPane.setLayout(null);

        LogUtils.setLogOutLevel(Level.DEBUG);
        LogUtils.debug(TAG, "init frame ...");
    }

    /**
     * Initialize the component of the frame.
     */
    private void componentInit() {
        // add tool bar
        JToolBar jToolBar = new JToolBar();
        jToolBar.setBounds(0, 0, mJFrame.getWidth(), 30);
        jToolBar.setFloatable(false);
        mContentPane.add(jToolBar);

        mJBtnDemo = new JButton("Demo");
        mJBtnDemo.setBorderPainted(false);
        jToolBar.add(mJBtnDemo);

        mJBtnTest = new JButton("Api");
        mJBtnTest.setBorderPainted(false);
        jToolBar.add(mJBtnTest);

        mJBtnVersion = new JButton("Version");
        mJBtnVersion.setBorderPainted(false);
        jToolBar.add(mJBtnVersion);

        mJBtnHelp = new JButton("Help");
        mJBtnHelp.setBorderPainted(false);
        jToolBar.add(mJBtnHelp);

        
        JLabel lblResultsArea = new JLabel("Results Area :");
        lblResultsArea.setBounds(589, 40, 100, 15);
        mContentPane.add(lblResultsArea);

        mResultsArea = new JTextArea("the return code...");
        mResultsArea.setLineWrap(true);// Enable line wrap
        mResultsArea.setWrapStyleWord(true);// Activate line breaking word function
        mResultsArea.setEditable(false); // Set read-only
        mJSPResult = new JScrollPane(mResultsArea);
        mJSPResult.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mJSPResult.setBounds(589, 65, 282, 211);
        mJSPResult.setViewportView(mResultsArea);
        mContentPane.add(mJSPResult);

        JLabel lblReportsArea = new JLabel("Reports Area :");
        lblReportsArea.setBounds(589, 294, 100, 15);
        mContentPane.add(lblReportsArea);

        mMReportsArea = new JTextArea("the report data...");
        mMReportsArea.setLineWrap(true);// Enable line wrap
        mMReportsArea.setWrapStyleWord(true);// Activate line breaking word function
        mMReportsArea.setEditable(false); // Set read-only
        mMJSPReport = new JScrollPane(mMReportsArea);
        mMJSPReport.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mMJSPReport.setBounds(589, 319, 282, 243);
        mMJSPReport.setViewportView(mMReportsArea);
        mContentPane.add(mMJSPReport);

        LogUtils.debug(TAG, "init component ...");
    }

    /**
     * Initialize the jpanel.
     */
    private void jPanelInit() {
        mPanelDemo = new EasyLinkSDK_Demo();
        mContentPane.add(mPanelDemo);
        mPanelDemo.setVisible(true);

        mPanelTest = new EasyLinkSDK_Test();
        mContentPane.add(mPanelTest);
        mPanelTest.setVisible(false);
    }

    /**
     * Initialize the event of the component.
     */
    private void eventInit() {
        mJFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        mJBtnDemo.addActionListener(this);
        mJBtnTest.addActionListener(this);
        mJBtnVersion.addActionListener(this);
        mJBtnHelp.addActionListener(this);

        LogUtils.debug(TAG, "init event");
    }

    public Container getContentPane() {

        return mJFrame.getContentPane();
    }

    public JTextArea getResultArea() throws NullPointerException {
        if (mResultsArea == null) {
            throw new NullPointerException("the mResultsArea is null! ");
        }

        return mResultsArea;
    }

    public JTextArea getReportArea() throws NullPointerException {
        if (mMReportsArea == null) {
            throw new NullPointerException("the mMReportsArea is null! ");
        }
        return mMReportsArea;
    }

    public String getVersionName() {
        return "EasyLinkWinSDK_TestDemo_V1.02.00_20200722";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Demo":
                // clear the text content
                mResultsArea.setText("");
                mMReportsArea.setText("");

                // Switch to show panel
                mPanelDemo.setVisible(true);
                mPanelTest.setVisible(false);

                LogUtils.debug(TAG, "click Demo");
                break;

            case "Api":
                mResultsArea.setText("");
                mMReportsArea.setText("");

                mPanelDemo.setVisible(false);
                mPanelTest.setVisible(true);

                LogUtils.debug(TAG, "click Test");
                break;

            case "Version":
                String testVersionName = getVersionName();
                String sdkversionName = EasyLinkSdkManager.getInstance().getVersionName();

                JOptionPane.showMessageDialog(null,
                        "The Test App Version is : " + testVersionName
                                + "\r\n The Test SDK Version is :" + sdkversionName,
                        "The App Version!", JOptionPane.INFORMATION_MESSAGE);

                LogUtils.debug(TAG, "click Version");
                break;

            case "Help":

                LogUtils.debug(TAG, "click Help ");
                break;

            default:
                break;
        }

    }

    private void exit() {
        Object[] options = {"Yes", "No"};
        JOptionPane pane = new JOptionPane("Are you sure to exit?", JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION, null, options, options[1]);
        JDialog dialog = pane.createDialog(mJFrame, "Warnning!");
        dialog.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
        dialog.setVisible(true);

        if (pane.getValue() == options[0]) {
            System.exit(0);
        } else {
            mJFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }

}

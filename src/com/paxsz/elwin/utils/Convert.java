package com.paxsz.elwin.utils;

import java.text.DecimalFormat;
import java.util.List;

import com.paxsz.easylink.model.DataModel;
import com.paxsz.easylink.model.TLVDataObject;

public class Convert {
    private static final String TAG = Convert.class.getSimpleName();
    private static Convert instance;

    private Convert() {}

    public synchronized static Convert getInstance() {
        if (instance == null) {
            instance = new Convert();
        }

        return instance;
    }

    public static String bcdToStr(byte[] b) throws IllegalArgumentException {
        if (b == null) {
            LogUtils.error(TAG, "bcdToStr input arg is null");
            throw new IllegalArgumentException("bcdToStr input arg is null");
        }

        char HEX_DIGITS[] =
                {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }

        return sb.toString();
    }

    public static byte[] strToBcd(String str, EPaddingPosition paddingPosition)
            throws IllegalArgumentException {
        if (str == null || paddingPosition == null) {
            LogUtils.error(TAG, "strToBcd input arg is null");
            throw new IllegalArgumentException("strToBcd input arg is null");
        }

        int len = str.length();
        int mod = len % 2;
        if (mod != 0) {
            if (paddingPosition == EPaddingPosition.PADDING_RIGHT) {
                str = str + "0";
            } else {
                str = "0" + str;
            }
            len = str.length();
        }
        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }
        byte bbt[] = new byte[len];
        abt = str.getBytes();
        int j, k;
        for (int p = 0; p < str.length() / 2; p++) {
            if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else if ((abt[2 * p] >= 'A') && (abt[2 * p] <= 'Z')) {
                j = abt[2 * p] - 'A' + 0x0a;
            } else {
                j = abt[2 * p] - '0';
            }

            if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else if ((abt[2 * p + 1] >= 'A') && (abt[2 * p + 1] <= 'Z')) {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            } else {
                k = abt[2 * p + 1] - '0';
            }

            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

    public void longToByteArray(long l, byte[] to, int offset, EEndian endian)
            throws IllegalArgumentException {
        if (to == null || endian == null) {
            LogUtils.error(TAG, "longToByteArray input arg is null");
            throw new IllegalArgumentException("longToByteArray input arg is null");
        }

        if (endian == EEndian.BIG_ENDIAN) {
            to[offset] = (byte) ((l >>> 56) & 0xff);
            to[offset + 1] = (byte) ((l >>> 48) & 0xff);
            to[offset + 2] = (byte) ((l >>> 40) & 0xff);
            to[offset + 3] = (byte) ((l >>> 32) & 0xff);
            to[offset + 4] = (byte) ((l >>> 24) & 0xff);
            to[offset + 5] = (byte) ((l >>> 16) & 0xff);
            to[offset + 6] = (byte) ((l >>> 8) & 0xff);
            to[offset + 7] = (byte) (l & 0xff);
        } else {
            to[offset + 7] = (byte) ((l >>> 56) & 0xff);
            to[offset + 6] = (byte) ((l >>> 48) & 0xff);
            to[offset + 5] = (byte) ((l >>> 40) & 0xff);
            to[offset + 4] = (byte) ((l >>> 32) & 0xff);
            to[offset + 3] = (byte) ((l >>> 24) & 0xff);
            to[offset + 2] = (byte) ((l >>> 16) & 0xff);
            to[offset + 1] = (byte) ((l >>> 8) & 0xff);
            to[offset] = (byte) (l & 0xff);
        }
    }

    public byte[] longToByteArray(long l, EEndian endian) throws IllegalArgumentException {
        if (endian == null) {
            LogUtils.error(TAG, "longToByteArray input arg is null");
            throw new IllegalArgumentException("longToByteArray input arg is null");
        }

        byte[] to = new byte[8];

        if (endian == EEndian.BIG_ENDIAN) {
            to[0] = (byte) ((l >>> 56) & 0xff);
            to[1] = (byte) ((l >>> 48) & 0xff);
            to[2] = (byte) ((l >>> 40) & 0xff);
            to[3] = (byte) ((l >>> 32) & 0xff);
            to[4] = (byte) ((l >>> 24) & 0xff);
            to[5] = (byte) ((l >>> 16) & 0xff);
            to[6] = (byte) ((l >>> 8) & 0xff);
            to[7] = (byte) (l & 0xff);
        } else {
            to[7] = (byte) ((l >>> 56) & 0xff);
            to[6] = (byte) ((l >>> 48) & 0xff);
            to[5] = (byte) ((l >>> 40) & 0xff);
            to[4] = (byte) ((l >>> 32) & 0xff);
            to[3] = (byte) ((l >>> 24) & 0xff);
            to[2] = (byte) ((l >>> 16) & 0xff);
            to[1] = (byte) ((l >>> 8) & 0xff);
            to[0] = (byte) (l & 0xff);
        }

        return to;
    }

    public void intToByteArray(int i, byte[] to, int offset, EEndian endian)
            throws IllegalArgumentException {
        if (to == null || endian == null) {
            LogUtils.error(TAG, "longToByteArray input arg is null");
            throw new IllegalArgumentException("longToByteArray input arg is null");
        }

        if (endian == EEndian.BIG_ENDIAN) {
            to[offset] = (byte) ((i >>> 24) & 0xff);
            to[offset + 1] = (byte) ((i >>> 16) & 0xff);
            to[offset + 2] = (byte) ((i >>> 8) & 0xff);
            to[offset + 3] = (byte) (i & 0xff);
        } else {
            to[offset] = (byte) (i & 0xff);
            to[offset + 1] = (byte) ((i >>> 8) & 0xff);
            to[offset + 2] = (byte) ((i >>> 16) & 0xff);
            to[offset + 3] = (byte) ((i >>> 24) & 0xff);
        }
    }

    public byte[] intToByteArray(int i, EEndian endian) throws IllegalArgumentException {
        if (endian == null) {
            LogUtils.error(TAG, "intToByteArray input arg is null");
            throw new IllegalArgumentException("intToByteArray input arg is null");
        }

        byte[] to = new byte[4];

        if (endian == EEndian.BIG_ENDIAN) {
            to[0] = (byte) ((i >>> 24) & 0xff);
            to[1] = (byte) ((i >>> 16) & 0xff);
            to[2] = (byte) ((i >>> 8) & 0xff);
            to[3] = (byte) (i & 0xff);
        } else {
            to[0] = (byte) (i & 0xff);
            to[1] = (byte) ((i >>> 8) & 0xff);
            to[2] = (byte) ((i >>> 16) & 0xff);
            to[3] = (byte) ((i >>> 24) & 0xff);
        }

        return to;
    }

    public void shortToByteArray(short s, byte[] to, int offset, EEndian endian)
            throws IllegalArgumentException {
        if (to == null || endian == null) {
            LogUtils.error(TAG, "shortToByteArray input arg is null");
            throw new IllegalArgumentException("shortToByteArray input arg is null");
        }

        if (endian == EEndian.BIG_ENDIAN) {
            to[offset] = (byte) ((s >>> 8) & 0xff);
            to[offset + 1] = (byte) (s & 0xff);
        } else {
            to[offset] = (byte) (s & 0xff);
            to[offset + 1] = (byte) ((s >>> 8) & 0xff);
        }
    }

    public byte[] shortToByteArray(short s, EEndian endian) throws IllegalArgumentException {
        if (endian == null) {
            LogUtils.error(TAG, "shortToByteArray input arg is null");
            throw new IllegalArgumentException("shortToByteArray input arg is null");
        }

        byte[] to = new byte[2];

        if (endian == EEndian.BIG_ENDIAN) {
            to[0] = (byte) ((s >>> 8) & 0xff);
            to[1] = (byte) (s & 0xff);
        } else {
            to[0] = (byte) (s & 0xff);
            to[1] = (byte) ((s >>> 8) & 0xff);
        }

        return to;
    }

    public long longFromByteArray(byte[] from, int offset, EEndian endian)
            throws IllegalArgumentException {
        if (from == null || endian == null) {
            LogUtils.error(TAG, "longFromByteArray input arg is null");
            throw new IllegalArgumentException("longFromByteArray input arg is null");
        }

        if (endian == EEndian.BIG_ENDIAN) {
            return ((from[offset] << 56) & 0xff00000000000000L)
                    | ((from[offset + 1] << 48) & 0xff000000000000L)
                    | ((from[offset + 2] << 40) & 0xff0000000000L)
                    | ((from[offset + 3] << 32) & 0xff00000000L)
                    | ((from[offset + 4] << 24) & 0xff000000)
                    | ((from[offset + 5] << 16) & 0xff0000) | ((from[offset + 6] << 8) & 0xff00)
                    | (from[offset + 7] & 0xff);
        } else {
            return ((from[offset + 7] << 56) & 0xff00000000000000L)
                    | ((from[offset + 6] << 48) & 0xff000000000000L)
                    | ((from[offset + 5] << 40) & 0xff0000000000L)
                    | ((from[offset + 4] << 32) & 0xff00000000L)
                    | ((from[offset + 3] << 24) & 0xff000000)
                    | ((from[offset + 2] << 16) & 0xff0000) | ((from[offset + 1] << 8) & 0xff00)
                    | (from[offset] & 0xff);
        }
    }

    public int intFromByteArray(byte[] from, int offset, EEndian endian)
            throws IllegalArgumentException {
        if (from == null || endian == null) {
            LogUtils.error(TAG, "intFromByteArray input arg is null");
            throw new IllegalArgumentException("intFromByteArray input arg is null");
        }

        if (endian == EEndian.BIG_ENDIAN) {
            return ((from[offset] << 24) & 0xff000000) | ((from[offset + 1] << 16) & 0xff0000)
                    | ((from[offset + 2] << 8) & 0xff00) | (from[offset + 3] & 0xff);
        } else {
            return ((from[offset + 3] << 24) & 0xff000000) | ((from[offset + 2] << 16) & 0xff0000)
                    | ((from[offset + 1] << 8) & 0xff00) | (from[offset] & 0xff);
        }
    }

    public short shortFromByteArray(byte[] from, int offset, EEndian endian)
            throws IllegalArgumentException {
        if (from == null || endian == null) {
            LogUtils.error(TAG, "shortFromByteArray input arg is null");
            throw new IllegalArgumentException("shortFromByteArray input arg is null");
        }

        if (endian == EEndian.BIG_ENDIAN) {
            return (short) (((from[offset] << 8) & 0xff00) | (from[offset + 1] & 0xff));
        } else {
            return (short) (((from[offset + 1] << 8) & 0xff00) | (from[offset] & 0xff));
        }
    }

    public String stringPadding(String src, char paddingChar, long expLength,
            EPaddingPosition paddingpos) throws IllegalArgumentException {
        if (src == null || paddingpos == null) {
            LogUtils.error(TAG, "stringPadding input arg is null");
            throw new IllegalArgumentException("stringPadding input arg is null");
        }

        if (src.length() >= expLength) {
            return src;
        }

        if (paddingpos == EPaddingPosition.PADDING_RIGHT) {

            StringBuffer sb = new StringBuffer(src);
            for (int i = 0; i < expLength - src.length(); i++) {
                sb.append(paddingChar);
            }

            return sb.toString();
        } else {

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < expLength - src.length(); i++) {
                sb.append(paddingChar);
            }

            sb.append(src);
            return sb.toString();
        }
    }

    public String amountMajorToMinUnit(double major, ECurrencyExponent exponent)
            throws IllegalArgumentException {
        if (exponent == null) {
            LogUtils.error(TAG, "amountMajorToMinUnit input arg is null");
            throw new IllegalArgumentException("amountMajorToMinUnit input arg is null");
        }

        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        String majorS = decimalFormat.format(major);

        String[] ht = majorS.split("\\.");

        String h = ht[0];
        String t = ht[1];

        if (exponent == ECurrencyExponent.CURRENCY_EXPONENT_2) {

            t = t.substring(0, 2);

            return h + t;
        } else if (exponent == ECurrencyExponent.CURRENCY_EXPONENT_0) {
            return h;
        } else if (exponent == ECurrencyExponent.CURRENCY_EXPONENT_1) {

            t = t.substring(0, 1);

            return h + t;
        } else if (exponent == ECurrencyExponent.CURRENCY_EXPONENT_3) {

            t = t.substring(0, 3);

            return h + t;
        }

        return "";
    }

    public String amountMajorToMinUnit(String major, ECurrencyExponent exponent)
            throws IllegalArgumentException {
        if (major == null || exponent == null) {
            LogUtils.error(TAG, "amountMajorToMinUnit input arg is null");
            throw new IllegalArgumentException("amountMajorToMinUnit input arg is null");
        }
        major = major.replace(",", "");
        return amountMajorToMinUnit(Double.parseDouble(major.trim()), exponent);
    }

    public String amountMinUnitToMajor(String minUnit, ECurrencyExponent exponent,
            boolean isCommaStyle) throws IllegalArgumentException {
        if (minUnit == null || exponent == null || minUnit.equals("")) {
            LogUtils.error(TAG, "amountMinUnitToMajor input arg is null");
            throw new IllegalArgumentException("amountMinUnitToMajor input arg is null");
        }
        if (!minUnit.matches("[0-9]+")) {
            LogUtils.error(TAG, "amountMinUnitToMajor input arg is illegal");
            throw new IllegalArgumentException("amountMinUnitToMajor input arg is illegal");
        }
        // 去掉头尾可能的空格
        String temp = minUnit.trim();
        temp = Long.parseLong(temp) + "";// 删除minUnit的前导0
        if (exponent == ECurrencyExponent.CURRENCY_EXPONENT_2) {
            if (temp.length() < 3) {
                int add = 3 - temp.length();
                for (int i = 0; i < add; i++) {
                    temp = "0" + temp;
                }
            }
            temp = temp.substring(0, temp.length() - 2) + "." + temp.substring(temp.length() - 2);
        } else if (exponent == ECurrencyExponent.CURRENCY_EXPONENT_1) {
            if (temp.length() < 2) {
                int add = 2 - temp.length();
                for (int i = 0; i < add; i++) {
                    temp = "0" + temp;
                }
            }
            temp = temp.substring(0, temp.length() - 1) + "." + temp.substring(temp.length() - 1);
        } else if (exponent == ECurrencyExponent.CURRENCY_EXPONENT_3) {
            if (temp.length() < 4) {
                int add = 4 - temp.length();
                for (int i = 0; i < add; i++) {
                    temp = "0" + temp;
                }
            }
            temp = temp.substring(0, temp.length() - 3) + "." + temp.substring(temp.length() - 3);
        }
        if (isCommaStyle == true) {
            int index = temp.indexOf(".");
            String strAdd = "";
            if (index != -1) {
                strAdd = temp.substring(index);
                temp = temp.substring(0, index);
            }
            temp = new StringBuffer(temp).reverse().toString(); // 先将字符串颠倒顺序
            String str2 = "";
            int size = (temp.length() % 3 == 0) ? (temp.length() / 3) : (temp.length() / 3 + 1); // 每三位取一长度
            /*
             * 比如把一段字符串分成n段,第n段可能不是三个数,有可能是一个或者两个,
             * 现将字符串分成两部分.一部分为前n-1段,第二部分为第n段.前n-1段，每一段加一",".而第n段直接取出即可
             */
            for (int i = 0; i < size - 1; i++) { // 前n-1段
                str2 += temp.substring(i * 3, i * 3 + 3) + ",";
            }
            for (int i = size - 1; i < size; i++) { // 第n段
                str2 += temp.substring(i * 3, temp.length());
            }
            str2 = new StringBuffer(str2).reverse().toString();
            str2 += strAdd;
            return str2;
        }
        return temp;
    }

    public static void unpackValues(final DataModel.DataType dataType, byte[] data,
            List<TLVDataObject> tlvDataObjects) {
        int dataLength = data.length;
        int index = 0;
        while (index < dataLength - 1) {
            int tagLength = 0;
            if (dataType == DataModel.DataType.TRANSACTION_DATA) {
                if (dataLength < index + 2) {
                    return;
                }
                // Operate 0x1F to & with the first bit, the tag's length is 1 bit if the result is
                // not equal to 0x1F.
                // 先取第一位和0x1F相与，结果如果不等于0x1F，则Tag为1位，等于的话再进行拆分
                if ((data[index] & 0x1F) != 0x1F) {
                    tagLength = 1;
                } else {
                    if ((data[index + 1] & 0x80) == 0x80) {
                        tagLength = 3;
                    } else {
                        tagLength = 2;
                    }
                }
            } else if (dataType == DataModel.DataType.CONFIGURATION_DATA) {
                if (dataLength < index + 3) {
                    return;
                }

                // The length of custom TLV's tag is 2 bits.
                // 自定义的TLV里，Tag固定为两位
                tagLength = 2;
            }

            int tagStart = index + tagLength;
            if (dataLength <= tagStart) {
                return;
            }

            // data[index] data[index+1]...data[tagStart-1] make up the tag.
            // 前面tagLength位表示Tag，即data[index] data[index+1]...data[tagStart-1]为Tag
            int valueLength = 0;
            int units = 0;
            if ((data[tagStart] & 0x80) != 0x80) {
                // data[tagStart] means the length of the value.
                // 此时data[tagStart]的值即表示value的长度
                valueLength = data[tagStart];
            } else {
                // data[tagStart+1] data[tagStart+2]...data[tagStart+units] is the length of the
                // value.
                // 此时data[tagStart]仅为指示作用，data[tagStart+1], data[tagStart+2]...data[tagStart+units]
                // 这么多位的值为value的长度
                units = data[tagStart] & 0x7F;

                if (dataLength <= tagStart + units) {
                    return;
                }

                for (int i = 1; i <= units; i++) {
                    // 例子：假如units为2，则data[tagStart+1]和data[tagStart+2]组成的值为value的长度，那么计算如下：
                    // data[tagStart+1]乘以2的(8 * (units-1))次方，然后加上data[tagStart+2]，
                    // 即data[tagStart+1] << (8 * (units-1))+data[tagStart+2]
                    valueLength = (data[tagStart + i] << (8 * (units - i))) + valueLength;
                }
            }

            TLVDataObject tv = new TLVDataObject();

            byte[] tags = new byte[tagLength];
            System.arraycopy(data, index, tags, 0, tagLength);
            tv.setTag(tags);

            if (dataLength < tagStart + units + 1 + valueLength) {
                return;
            }

            if (valueLength != 0) {
                // Get the value.
                // 本次value的值
                byte[] values = new byte[valueLength];
                // Copy from data[tagLength + 1 + units] for valueLength bits.
                // 从data的tagLength + 1 + units位开始复制，复制valueLen位数据到values数组里，values数组放置的起始位置为0
                System.arraycopy(data, index + tagLength + 1 + units, values, 0, valueLength);
                tv.setValue(values);
            } else {
                tv.setValue(new byte[0]);
            }

            tlvDataObjects.add(tv);

            index = index + tagLength + 1 + units + valueLength;
        }
    }

    /**
     * padding position
     * 
     */
    public static enum EPaddingPosition {
        /**
         * padding left
         */
        PADDING_LEFT,
        /**
         * padding right
         */
        PADDING_RIGHT
    }

    /**
     * endian type
     * 
     */
    public static enum EEndian {
        /**
         * little endian
         */
        LITTLE_ENDIAN,
        /**
         * big endian
         */
        BIG_ENDIAN
    }

    /**
     * currency exponent. refer to ISO4217
     * 
     */
    public enum ECurrencyExponent {
        /**
         * currency exponent 0
         */
        CURRENCY_EXPONENT_0,
        /**
         * currency exponent 1
         */
        CURRENCY_EXPONENT_1,
        /**
         * currency exponent 2
         */
        CURRENCY_EXPONENT_2,
        /**
         * currency exponent 3
         */
        CURRENCY_EXPONENT_3
    }
}

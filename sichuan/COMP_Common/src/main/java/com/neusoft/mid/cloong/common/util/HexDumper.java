package com.neusoft.mid.cloong.common.util;

/**
 * 16进制转换
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-3-7 下午02:05:14
 */
public final class HexDumper {

    private HexDumper() {
    }

    private static final byte[] highDigits;

    private static final byte[] lowDigits;

    private static final String newLine = "\r\n";

    // initialize lookup tables
    static {
        final byte[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
                'D', 'E', 'F' };

        int i;
        byte[] high = new byte[256];
        byte[] low = new byte[256];

        for (i = 0; i < 256; i++) {
            high[i] = digits[i >>> 4];
            low[i] = digits[i & 0x0F];
        }

        highDigits = high;
        lowDigits = low;
    }

    public static String getHexdump(String in) {
        return getHexdump(in.getBytes());
    }

    /**
     * byte数组转16进制字符
     * @param bb
     * @return 16进制字符
     */
    public static String getHexdump(byte[] bb) {

        StringBuffer out = new StringBuffer(bb.length * 2);

        // fill the first
        int byteValue = bb[0] & 0xFF;
        out.append((char) highDigits[byteValue]);
        out.append((char) lowDigits[byteValue]);
        int size = bb.length;
        size--;

        // and the others, too
        for (; size > 0; size--) {
            byteValue = bb[bb.length - size] & 0xFF;
            out.append((char) highDigits[byteValue]);
            out.append((char) lowDigits[byteValue]);
        }
        return out.toString();
    }

    /**
     * 把16进制字符串转换成字节数组
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
        if (null == hex) {
            return null;
        }
        hex = hex.toUpperCase();
        if (hex.length() % 2 != 0) {
            hex = "0" + hex;
        }
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * byte数组转16进制字符,按照给定的数字为列输出
     * @param bb
     * @return 16进制字符
     */
    public static String getHexdump(byte[] bb, int colNum) {

        StringBuffer out = new StringBuffer(bb.length * 2);

        // fill the first
        int byteValue = bb[0] & 0xFF;
        out.append(newLine);
        out.append((char) highDigits[byteValue]);
        out.append((char) lowDigits[byteValue]);
        out.append(" ");
        int size = bb.length;
        size--;

        // and the others, too
        for (; size > 0; size--) {
            byteValue = bb[bb.length - size] & 0xFF;
            out.append((char) highDigits[byteValue]);
            out.append((char) lowDigits[byteValue]);
            out.append(" ");
            if ((bb.length - size + 1) % (colNum) == 0){ out.append(newLine);}
            if (size == 1){ out.append(" " + newLine);}
        }
        return out.toString();
    }

    public static byte hexStringToOneByte(String hexstr) {
        if (hexstr.length() != 2) {
            return 0;
        }
        byte b = 0;
        char c0 = hexstr.charAt(0);
        char c1 = hexstr.charAt(1);
        b = (byte) ((parse(c0) << 4) | parse(c1));
        return b;
    }

    private static int parse(char c) {
        if (c >= 'a'){ return (c - 'a' + 10) & 0x0f;}
        if (c >= 'A'){ return (c - 'A' + 10) & 0x0f;}
        return (c - '0') & 0x0f;
    }
}

package com.neusoft.mid.cloong.common.util.ip;

/**
 * IP字符串与十进制长整型数据类型转换的工具类
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年2月15日 下午3:13:04
 */
class IpTransUtil {

    /**
     * 私有构造器，防止被实例化
     */
    private IpTransUtil() {

    }

    /**
     * ip2Dec 字符串转换为长整型
     * @param ip IP字符串 格式为xxx.xxx.xxx.xxx
     * @return 长整型的IP
     */
    public static Long ip2Dec(String ip) {

        String[] ss = ip.split("\\.");

        Long result = 0L;

        for (int i = 0; i < ss.length; i++) {
            Long t = Long.valueOf(ss[i]);

            Long tmp = t << (3 - i) * 8L;

            result += tmp;
        }

        return result;
    }

    /**
     * dec2Ip 长整型转换为字符串
     * @param dec 长整型十进制IP
     * @return 格式为xxx.xxx.xxx.xxx的字符串
     */
    public static String dec2Ip(Long dec) {

        String result = "";

        Long tmp = new Long(0L);

        for (int i = 3; i >= 0; i--) {

            tmp = (dec / (1 << 8L * i));

            result += tmp;

            dec -= (tmp << 8L * i);

            if (i != 0) result += ".";
        }

        return result;
    }

}

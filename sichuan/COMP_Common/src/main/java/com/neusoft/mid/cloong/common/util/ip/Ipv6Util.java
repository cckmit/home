/**
 * 
 */
package com.neusoft.mid.cloong.common.util.ip;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Ipv6Util {

	private Ipv6Util() {

    }
	
	/**
     * getIpv6ExtremeIp 根据ip段获取首尾IP
     * @param subnet ip段，格式为 2409:8C62:0F10:0001:0:35::/96
     * @return 由首位IP组成的list，list[0]为首IP，list[1]为尾IP
     * @throws MaskErrorException 若掩码位数不合法，会抛出异常
     */
	public static List<String> getIpv6ExtremeIp(String subnet) throws MaskErrorException {
		List<String> list = new ArrayList<String>();
    	String ipv6 = subnet.split("/")[0];
    	int ipv6Mast = (128 - Integer.parseInt(subnet.split("/")[1])) / 16;
    	String firstIp = ipv6 + "1";
    	String lastIpTmp = Ipv6Util.ipv6toInt(firstIp).toString(16).substring(0, 32 - 4 * ipv6Mast) + Ipv6Util.addF(4 * ipv6Mast);
    	String lastIpFull = Ipv6Util.fullAddressAddFlag(lastIpTmp);
    	String lastIp = Ipv6Util.int2ipv6(Ipv6Util.ipv6toInt(lastIpFull));
    	
    	list.add(firstIp);
        list.add(lastIp);
        return list;
	}
	
	public static List<String> getIpv6List(int powerNum, String gateWayIp, String first, String last) {
		List<String> ipList = new ArrayList<String>();
		double ipSize = Math.pow(2, powerNum) - 1; // 255个IP
//		DecimalFormat df = new DecimalFormat("########");
//		System.out.println("2的8次方位: " + df.format(ipSize));
		for (int i = 1; i <= ipSize; i++) {
			String strHex2 = String.format("%X", i); // 转成16进制
			String ip = gateWayIp + strHex2;
			ipList.add(ip);
		}
		return ipList;
	}
	
	public static void main(String[] args) {
		double ipSize = Math.pow(2, 4) - 1;
//		DecimalFormat df = new DecimalFormat("########");
//		System.out.println("2的8次方位: " + df.format(ipSize));
		for (int i = 1; i <= ipSize; i++) {
			String strHex2 = String.format("%X", i);
			System.out.println(strHex2);
			String ip = "2409:8C62:0F10:0001:0:28::" + strHex2;
			System.out.println(ip);
		}
	}
	
	public static String getIpv6Size(String subnetSize) {
		double ipSize = Math.pow(2, (128 - Integer.parseInt(subnetSize))) - 1;
		DecimalFormat df = new DecimalFormat("########");
    	return df.format(ipSize);
	}
	
	public static BigInteger ipv6toInt(String ipv6) {

        int compressIndex = ipv6.indexOf("::");
        if (compressIndex != -1) {
            String part1s = ipv6.substring(0, compressIndex);
            String part2s = ipv6.substring(compressIndex + 1);
            BigInteger part1 = ipv6toInt(part1s);
            BigInteger part2 = ipv6toInt(part2s);
            int part1hasDot = 0;
            char ch[] = part1s.toCharArray();
            for (char c : ch) {
                if (c == ':') {
                    part1hasDot++;
                }
            }
            // ipv6 has most 7 dot
            return part1.shiftLeft(16 * (7 - part1hasDot)).add(part2);
        }
        String[] str = ipv6.split(":");
        BigInteger big = BigInteger.ZERO;
        for (int i = 0; i < str.length; i++) {
            // ::1
            if (str[i].isEmpty()) {
                str[i] = "0";
            }
            big = big.add(BigInteger.valueOf(Long.valueOf(str[i], 16)).shiftLeft(
                    16 * (str.length - i - 1)));
        }
        return big;
    }
	
	public static String addF(int num) {
        String zerostr = "";
        for (int i = 0; i < num; i++) {
            zerostr += "f";
        }
        return zerostr;
    }
	
	public static String fullAddressAddFlag(String bString) {
		String regex = "(.{4})";
		bString = bString.replaceAll (regex, "$1:");
		bString = bString.substring(0, bString.length() - 1);
		return bString;
    }
	
	public static String int2ipv6(BigInteger big) {
        String str = "";
        BigInteger ff = BigInteger.valueOf(0xffff);
        for (int i = 0; i < 8; i++) {
            str = big.and(ff).toString(16) + ":" + str;

            big = big.shiftRight(16);
        }
        // the last :
        str = str.substring(0, str.length() - 1);

        return str.replaceFirst("(^|:)(0+(:|$)){2,8}", "::");
    }
	
}

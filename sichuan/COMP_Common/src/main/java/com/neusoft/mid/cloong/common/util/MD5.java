package com.neusoft.mid.cloong.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.neusoft.mid.iamp.logger.LogService;

public class MD5 {
    private static LogService logger = LogService.getLogger(MD5.class);

    public static String getMd5Bytes(String md5Str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(md5Str.getBytes());
        } catch (NoSuchAlgorithmException e) {
            logger.error("Str:" + md5Str + ",generate failed!", e);
        }
        return HexDumper.getHexdump(md.digest());
    }
}

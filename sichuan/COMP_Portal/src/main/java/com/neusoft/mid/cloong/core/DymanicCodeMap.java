/*******************************************************************************
 * @(#)DymanicCodeMap.java 2014年2月8日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态密码缓存
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年2月8日 上午9:19:56
 */
public final class DymanicCodeMap {
    /**
     * 缓存
     */
    private static Map<String, DymanicCode> codeMap = new ConcurrentHashMap<String, DymanicCode>();
    /**
     * 缓存实例
     */
    private static DymanicCodeMap dymanicCodeMap;
   /**
    * 
    * 创建一个新的实例 DymanicCodeMap
    */
    private DymanicCodeMap(){
        
    }
    /**
     * 
     * getInstance 获取缓存实例
     * @return 返回缓存实例
     */
    public static DymanicCodeMap getInstance(){
        if(null ==dymanicCodeMap){
            dymanicCodeMap = new DymanicCodeMap();
        }
        return dymanicCodeMap;
    }
    /**
     * 
     * put 将动态密码放入缓存
     * @param key 主键
     * @param code 动态密码
     */
    public void put(String key,DymanicCode code){
        codeMap.put(key, code);
    }
    /**
     * 
     * get 获取动态密码
     * @param key 主键
     * @return 获取动态密码
     */
    public DymanicCode get(String key){
        return codeMap.get(key);
    }
    /**
     * 
     * containsKey 判断是否存在
     * @param key 主键
     * @return 是否存在
     */
    public boolean containsKey(String key){
        return codeMap.containsKey(key);
    }
}

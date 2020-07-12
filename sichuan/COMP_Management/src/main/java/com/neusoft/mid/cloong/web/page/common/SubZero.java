package com.neusoft.mid.cloong.web.page.common;

/**
 * 使用java正则表达式去掉多余的.与0
 * @author liunan
 *
 */
public class SubZero
{
    public static String subZero(String s){  
        if(s.indexOf(".") > 0){  
            s = s.replaceAll("0+?$", "");//去掉多余的0  
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }  
        return s;  
    }
}

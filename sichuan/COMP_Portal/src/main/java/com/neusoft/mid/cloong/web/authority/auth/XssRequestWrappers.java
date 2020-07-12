package com.neusoft.mid.cloong.web.authority.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class XssRequestWrappers extends HttpServletRequestWrapper {
    //将request对象中的参数修改后，放在这个集合里，随后项目取的所有Parameter都是从这个集合中取数
    private Map<String , String[]> params = new HashMap<String, String[]>();

    @SuppressWarnings("unchecked")
    public XssRequestWrappers(HttpServletRequest request) {
        // 把父类request拿来，继承他未重写方法
        super(request);
        //this.params.putAll(request.getParameterMap());//将父类的parameter传递给当前params

    }

    //重载一个构造方法
    public XssRequestWrappers(HttpServletRequest request , Map<String , Object> extendParams) {
        this(request);
        addParameters(extendParams);//这里将扩展参数写入参数表
    }

    public void addParameters(Map<String, Object> extraParams) {
        for (Map.Entry<String, Object> entry : extraParams.entrySet()) {
            addParameter(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        HashMap<String, String[]> newMap = new HashMap<String, String[]>();
        newMap.putAll(super.getParameterMap());
        for(String key : newMap.keySet()){
            String[] value = newMap.get(key);
            value[0] = delHTMLTag(value[0]);
            newMap.put(key,value);
        }
        return Collections.unmodifiableMap(newMap);
    }
    @Override
    public String getParameter(String name) {//重写getParameter，代表参数从当前类中的map获取
        String[]values = params.get(name);
        if(values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }
    @Override
    public String[] getParameterValues(String name) {//同上
        return params.get(name);
    }
    public void addParameter(String name , Object value) {//增加参数
        if(value != null) {
            if(value instanceof String[]) {
                params.put(name , (String[])value);
            }else if(value instanceof String) {
                params.put(name , new String[] {(String)value});
            }else {
                params.put(name , new String[] {String.valueOf(value)});
            }
        }
    }
    public static String delHTMLTag(String inputString){
        String htmlStr = inputString;
        String textStr ="";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;

        Pattern p_html1;
        java.util.regex.Matcher m_html1;

        try {
//            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
//            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
//            String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
//            String regEx_html1 = "<[^>]+";
//            p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
//            m_script = p_script.matcher(htmlStr);
//            htmlStr = m_script.replaceAll(""); //过滤script标签
//
//            p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
//            m_style = p_style.matcher(htmlStr);
//            htmlStr = m_style.replaceAll(""); //过滤style标签
//
//            p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
//            m_html = p_html.matcher(htmlStr);
//            htmlStr = m_html.replaceAll(""); //过滤html标签
//
//            p_html1 = Pattern.compile(regEx_html1,Pattern.CASE_INSENSITIVE);
//            m_html1 = p_html1.matcher(htmlStr);
//            htmlStr = m_html1.replaceAll(""); //过滤html标签

            textStr = htmlStr;
//
            textStr = cleanXSS(textStr);
        }catch(Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
        return textStr;//返回文本字符串
    }
    private static String cleanXSS(String value) {
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        //先暂时去除 英文括号的拦截
//        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        //value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']","\"\"");
        value = value.replaceAll("script", "");
        value = value.replaceAll("alert", "");

        return value;
    }


}

package com.neusoft.mid.cloong.vault.beans.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CDataAdapter extends XmlAdapter<String, String>
{
    @Override
    public String marshal (String v) throws Exception
    {
        return "<![CDATA[" + v + "]]>";
    }
    @Override
    public String unmarshal (String v) throws Exception
    {
        if ("<![CDATA[]]>".equals (v))
        {
            return "";
        }
        String v1 = null;
        String v2 = null;
        String subStart = "<![CDATA[";
        int a = v.indexOf (subStart);
        if (a >= 0)
        {
            v1 = v.substring (subStart.length (), v.length ());
        }
        else
        {
            return v;
        }
        String subEnd = "]]>";
        int b = v1.indexOf (subEnd);
        if (b >= 0)
        {
            v2 = v1.substring (0, b);
        }
        return v2;
    }

}
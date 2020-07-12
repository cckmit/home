package com.neusoft.mid.cloong;

import java.util.Properties;
import java.util.Set;

/**
 * 配置文件过滤类，防止property文件中由于手误填写了多余的空格字符
 * @author <a href="mailto:dingpy@neusoft.com">DPY </a>
 * @version $Revision 1.1 $ 2010-12-30 下午02:00:57
 */
public class TrimPropertyPlaceholderConfigurer extends
        org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {

    @SuppressWarnings( { "unchecked", "deprecation" })
    protected String parseStringValue(String strVal, Properties props, Set originalPlaceholder) {
        String strTemp = null;
        if (strVal != null) {
            strTemp = strVal.trim();
        }
        return super.parseStringValue(strTemp, props, originalPlaceholder);
    }

}

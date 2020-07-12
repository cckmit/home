/*******************************************************************************
 * @(#)hostPrice.java 2013-1-17
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.portal.product.host;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 主机价格
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version $Revision 1.1 $ 2013-1-17 上午10:46:52
 */
public class HostPriceAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(HostPriceAction.class);

    private String SUCCESS = "success";

    private String ERROR = "error";

    private String RECOMMEND = "1";

    private Map<String, List<HostItem>> hostItemMaps;

    Map<String, List<HostItem>> pmHostItemMaps;

    private List<HostItem> recommends;

    private List<HostItem> pmRecommends;

    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    public String execute() {
        List<HostItem> hostItems;
        List<HostItem> pmHostItems;
        try {
            String date = DateParse.generateDateFormatyyyyMMddHHmmss();
            hostItems = ibatisDAO.getData("getHostItems", date);
            pmHostItems = ibatisDAO.getData("getPMHostItems", date);
        } catch (SQLException e) {
            logger.error("产品数据库查询失败!", e);
            return ERROR;
        } catch (Exception e2) {
            logger.error("产品数据库查询失败!", e2);
            return ERROR;
        }

        // 获取推荐列表
        if (null != hostItems) {
            recommends = getRecommendList(hostItems);
        }

        if (null != hostItems) {
            hostItemMaps = getHostItemList(hostItems);
        }
        // 获取推荐列表
        if (null != pmHostItems) {
            pmRecommends = getRecommendList(pmHostItems);
        }
        if (null != pmHostItems) {
            pmHostItemMaps = getHostItemList(pmHostItems);
        }
        return SUCCESS;
    }

    /**
     * 获取主机推荐列表
     * @param List<HostItem>
     * @return List<HostItem>
     */
    private Map<String, List<HostItem>> getHostItemList(List<HostItem> itemList) {
        Map<String, List<HostItem>> map = new HashMap<String, List<HostItem>>();
        for (HostItem item : itemList) {
            List<HostItem> ls = map.get(item.getCatalogName());
            if (null != ls) {
                ls.add(item);
            } else {
                ls = new ArrayList<HostItem>();
                ls.add(item);
                map.put(item.getCatalogName(), ls);
            }
        }
        return map;
    }

    /**
     * 获取主机推荐列表
     * @param List<HostItem>
     * @return List<HostItem>
     */
    private List<HostItem> getRecommendList(List<HostItem> itemList) {
        List<HostItem> list = new ArrayList<HostItem>();
        for (HostItem item : itemList) {
            if (item.getRecommend().equals(RECOMMEND)) {
                list.add(item);
            }
        }
        return list;
    }

    /**
     * @return the recommends
     */
    public List<HostItem> getRecommends() {
        return recommends;
    }

    /**
     * @return the hostItemMaps
     */
    public Map<String, List<HostItem>> getHostItemMaps() {
        return hostItemMaps;
    }

    /**
     * 获取pmHostItemMaps字段数据
     * @return Returns the pmHostItemMaps.
     */
    public Map<String, List<HostItem>> getPmHostItemMaps() {
        return pmHostItemMaps;
    }

    /**
     * 获取pmRecommends字段数据
     * @return Returns the pmRecommends.
     */
    public List<HostItem> getPmRecommends() {
        return pmRecommends;
    }

}

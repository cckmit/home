/*******************************************************************************
 * @(#)EbsPriceAction.java 2013-2-28
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.portal.product.ebs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 云硬盘价格
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-28 上午10:12:35
 */
public class EbsPriceAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(EbsPriceAction.class);

	/** 推荐状态 **/
    private static final String RECOMMEND = "1";
    /** 界面列表展示数据格式 **/
    private Map<String, List<EbsItem>> ebsItemMaps;

    private List<EbsItem> ebsItems;

    private List<EbsItem> recommends;

    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    public String execute() {
        try {
            ebsItems = ibatisDAO.getData("getEbsItems", DateParse.generateDateFormatyyyyMMddHHmmss());
        } catch (SQLException e) {
            logger.error("产品数据库查询失败!", e);
            return ConstantEnum.ERROR.toString();
        } catch (Exception e2) {
            logger.error("产品数据库查询失败!", e2);
            return ConstantEnum.ERROR.toString();
        }
        // 获取推荐列表
        if (null != ebsItems) {
            recommends = getRecommendList(ebsItems);
        }
        if (null != ebsItems) {
            ebsItemMaps = getEbsItemList(ebsItems);
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 获取硬盘推荐列表
     * @param List<EbsItem>
     * @return List<EbsItem>
     */
    private List<EbsItem> getRecommendList(List<EbsItem> itemList) {
        List<EbsItem> list = new ArrayList<EbsItem>();
        for (EbsItem item : itemList) {
            if (item.getRecommend().equals(RECOMMEND)) {
                list.add(item);
            }
        }
        return list;
    }
    
    /**
     * 
     * getEbsItemList 获取云硬盘展示列表数据
     * @param itemList List<EbsItem>
     * @return Map<String, List<EbsItem>>
     */
    private Map<String, List<EbsItem>> getEbsItemList(List<EbsItem> itemList) {
        Map<String, List<EbsItem>> map = new HashMap<String, List<EbsItem>>();
        for (EbsItem item : itemList) {
            List<EbsItem> ls = map.get(item.getCatalogName());
            if (null != ls) {
                ls.add(item);
            } else {
                ls = new ArrayList<EbsItem>();
                ls.add(item);
                map.put(item.getCatalogName(), ls);
            }
        }
        return map;
    }

    /**
     * @return the ebsItems
     */
    public List<EbsItem> getEbsItems() {
        return ebsItems;
    }

    /**
     * @return the recommends
     */
    public List<EbsItem> getRecommends() {
        return recommends;
    }
	/**
     * @return the ebsItemMaps
     */
    public Map<String, List<EbsItem>> getEbsItemMaps() {
        return ebsItemMaps;
    }
    
}

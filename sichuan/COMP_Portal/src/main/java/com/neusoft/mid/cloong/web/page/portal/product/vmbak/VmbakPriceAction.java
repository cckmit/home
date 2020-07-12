/*******************************************************************************
 * @(#)VmbakPriceAction.java 2014-1-24
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.portal.product.vmbak;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机备份
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version $Revision 1.0 $ 2014-1-24 上午3:35:50
 */
public class VmbakPriceAction extends BaseAction {

    private static final long serialVersionUID = 8556304281426541660L;

    private static LogService logger = LogService.getLogger(VmbakPriceAction.class);

    private final String RECOMMEND = "1";

    private List<VmbakItem> vmbakItems;

    private List<VmbakItem> recommends;

    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    public String execute() {
        try {
            vmbakItems = ibatisDAO.getData("getVmbakItems", "");
        } catch (SQLException e) {
            logger.error("产品数据库查询失败!", e);
            return ConstantEnum.ERROR.toString();
        } catch (Exception e2) {
            logger.error("产品数据库查询失败!", e2);
            return ConstantEnum.ERROR.toString();
        }
        // 获取推荐列表
        if (null != vmbakItems) {
            recommends = getRecommendList(vmbakItems);
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 获取硬盘推荐列表
     * @param List<VmbakItem>
     * @return List<VmbakItem>
     */
    private List<VmbakItem> getRecommendList(List<VmbakItem> itemList) {
        List<VmbakItem> list = new ArrayList<VmbakItem>();
        for (VmbakItem item : itemList) {
            if (item.getRecommend().equals(RECOMMEND)) {
                list.add(item);
            }
        }
        return list;
    }

    /**
     * 获取vmbakItems字段数据
     * @return Returns the vmbakItems.
     */
    public List<VmbakItem> getVmbakItems() {
        return vmbakItems;
    }

    /**
     * 获取recommends字段数据
     * @return Returns the recommends.
     */
    public List<VmbakItem> getRecommends() {
        return recommends;
    }
}

package com.neusoft.mid.cloong.web.page.product.item.vm.service;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.ItemStatus;
import com.neusoft.mid.cloong.common.core.ItemType;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemInfo;

/**
 * 通用服务条目修改Service
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-26 上午10:57:46
 */
public class ItemUpdateService extends BaseService {

    /**
     * 通用 修改条目
     * @param itemInfo 条目实体bean
     * @param itemType 条目类型
     * @param statusType 状态类型
     * @param oldStandardId 原资源规格
     * @throws SQLException
     */
    public String itemUpdate(ItemInfo itemInfo, String itemType, String statusType,
            String oldStandardId, String statusCode) throws SQLException {
        initItem(itemInfo, itemType, statusType);
        ItemInfo lsItemInfo = getItemByItemId(itemInfo);
        if (lsItemInfo == null) {
            return ConstantEnum.DELETE.toString();
        }
        ItemStatus status = lsItemInfo.getStatus();
        if (ItemStatus.DELETED.getCode().equals(status.getCode())) {
            return status.getCode();
            // return "条目已删除，不能修改！";
        }
        if (ItemStatus.PENDING.getCode().equals(status.getCode())) {
            return status.getCode();
            // return "条目待审批，不能修改！";
        }
        if (ItemStatus.PUBLISHPASS.getCode().equals(status.getCode())) {
            return status.getCode();
            // return "条目已发布，不能修改！";
        }
        if (ItemStatus.PUBLISH.getCode().equals(status.getCode())) {
            return status.getCode();
            // return "条目发布待审批，不能修改！";
        }
        if (ItemStatus.PULLED.getCode().equals(status.getCode())) {
            return status.getCode();
            // return "条目已下架，不能修改！";
        }
        // 保存虚拟机条目
        // sql更新语句中有条 STATUS='0' OR STATUS='3' OR STATUS='6' OR STATUS='2' 只有保存、审批不通过、发布审批不通过、审批通过 才可修改
        if("3".equals(statusCode)){
            itemInfo.setStatus(ItemStatus.PENDING);
        }
        ibatisDAO.updateData("itemUpdate", itemInfo);
        // TODO 缺少关联资费信息
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 条目信息初始化设置
     * @param itemInfo 服务条目实例
     * @param itemType 条目类型
     * @param statusType 状态类型
     */
    private void initItem(ItemInfo itemInfo, String itemType, String statusType) {
        // 服务条目类型
        itemInfo.setItemType(getItemTypeEnum(itemType));
        for (ItemStatus status : ItemStatus.values()) {
            if (statusType.equals(status.getCode())) {
                // 条目状态
                itemInfo.setStatus(status);
                break;
            }
        }
        // 当前系统时间
        String time = DateParse.generateDateFormatyyyyMMddHHmmss();
        // 更新时间
        itemInfo.setUpdateTime(time);
        if ("".equals(itemInfo.getSellStartTime())) {
            itemInfo.setSellStartTime(time);
        }
    }

    /**
     * 获取条目类型
     * @param itemType
     * @return 存在返回 ItemType 枚举类型.否则返回null
     */
    public ItemType getItemTypeEnum(String itemType) {
        for (ItemType status : ItemType.values()) {
            if (itemType.equals(status.getCode())) {
                return status;
            }
        }
        return null;
    }

    /**
     * 服务条目id查询服务条目
     * @param itemInfo
     * @return ItemInfo
     * @throws SQLException
     */
    private ItemInfo getItemByItemId(ItemInfo itemInfo) throws SQLException {
        return (ItemInfo) ibatisDAO.getSingleRecord("getItemInfoByItemId", itemInfo);
    }
}

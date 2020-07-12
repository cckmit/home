package com.neusoft.mid.cloong.rpproxy.interfaces.ip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

/**
 * 创建虚拟硬盘接口响应
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午01:52:28
 */
public class RPPIPQueryResp extends RPPBaseResp implements Serializable {

    private static final long serialVersionUID = 1635945078071367626L;

    /**
     * 故障描述
     */
    private String faultstring;

    /**
     * 所有的IP信息
     */
    private List<IPSegmentInfo> ipSegmentInfoList = new ArrayList<IPSegmentInfo>();

    /**
     * 获取faultstring字段数据
     * @return Returns the faultstring.
     */
    public String getFaultstring() {
        return faultstring;
    }

    /**
     * 设置faultstring字段数据
     * @param faultstring The faultstring to set.
     */
    public void setFaultstring(String faultstring) {
        this.faultstring = faultstring;
    }

    /**
     * 获取ipSegmentInfoList字段数据
     * @return Returns the ipSegmentInfoList.
     */
    public List<IPSegmentInfo> getIpSegmentInfoList() {
        return ipSegmentInfoList;
    }

    /**
     * 设置ipSegmentInfoList字段数据
     * @param ipSegmentInfoList The ipSegmentInfoList to set.
     */
    public void setIpSegmentInfoList(List<IPSegmentInfo> ipSegmentInfoList) {
        this.ipSegmentInfoList = ipSegmentInfoList;
    }

    public static class IPSegmentInfo implements Serializable {
        /**
         * IP子网，申请私网时有值.格式为192.168.1.1/xx
         */
        private String ipSubNet;

        /**
         * IP段唯一标识符申请私网时有值
         */
        private String ipSegmentURI;

        /**
         * IP类型
         */
        private IPProType ipProType;

        /**
         * IP用途类型
         */
        private IPType ipType;

        /**
         * IP申请状态
         */
        private IPApplyState applyState;

        /**
         * 获取ipSubNet字段数据
         * @return Returns the ipSubNet.
         */
        public String getIpSubNet() {
            return ipSubNet;
        }

        /**
         * 设置ipSubNet字段数据
         * @param ipSubNet The ipSubNet to set.
         */
        public void setIpSubNet(String ipSubNet) {
            this.ipSubNet = ipSubNet;
        }

        /**
         * 获取ipSegmentURI字段数据
         * @return Returns the ipSegmentURI.
         */
        public String getIpSegmentURI() {
            return ipSegmentURI;
        }

        /**
         * 设置ipSegmentURI字段数据
         * @param ipSegmentURI The ipSegmentURI to set.
         */
        public void setIpSegmentURI(String ipSegmentURI) {
            this.ipSegmentURI = ipSegmentURI;
        }

        /**
         * 获取ipProType字段数据
         * @return Returns the ipProType.
         */
        public IPProType getIpProType() {
            return ipProType;
        }

        /**
         * 设置ipProType字段数据
         * @param ipProType The ipProType to set.
         */
        public void setIpProType(IPProType ipProType) {
            this.ipProType = ipProType;
        }

        /**
         * 获取ipType字段数据
         * @return Returns the ipType.
         */
        public IPType getIpType() {
            return ipType;
        }

        /**
         * 设置ipType字段数据
         * @param ipType The ipType to set.
         */
        public void setIpType(IPType ipType) {
            this.ipType = ipType;
        }

        /**
         * 获取applyState字段数据
         * @return Returns the applyState.
         */
        public IPApplyState getApplyState() {
            return applyState;
        }

        /**
         * 设置applyState字段数据
         * @param applyState The applyState to set.
         */
        public void setApplyState(IPApplyState applyState) {
            this.applyState = applyState;
        }

    }
}

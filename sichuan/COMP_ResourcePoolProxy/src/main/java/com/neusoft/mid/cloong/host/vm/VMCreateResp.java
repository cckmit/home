package com.neusoft.mid.cloong.host.vm;

import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.info.RespBodyInfo;

/**创建虚拟机接口响应
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午01:52:28
 */
public class VMCreateResp  extends RespBodyInfo {

    private String caseId;
    
    private List<Map<String,String>> vmInfo;

    public List<Map<String,String>> getVmInfo() {
        return vmInfo;
    }

    public void setVmInfo(List<Map<String,String>> vmInfo) {
        this.vmInfo = vmInfo;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("响应码为：").append(this.getResultCode()).append("\n");
        sb.append("响应描述为：").append(this.getResultMessage()).append("\n");
        sb.append("虚拟机实例ID为：").append(this.caseId).append("\n");
        sb.append("虚拟机信息为：").append("\n");
        for(Map<String,String> vmInfoMap :this.getVmInfo()){
            sb.append(vmInfoMap.toString()).append("\n");
        }
        return sb.toString();
    }
}

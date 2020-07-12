package com.neusoft.mid.cloong.web.page.common;

/**
 * 虚拟机创建返回结果
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-16 下午02:34:35
 */
public class CreateResult {

    /**
     * 结果码
     */
    private String resultFlage;

    /**
     * 结果消息
     */
    private String resultMessage;

    public String getResultFlage() {
        return resultFlage;
    }

    /**
     * 创建一个新的实例 CreateResult
     */
    public CreateResult() {
        super();
    }

    /**
     * 创建一个新的实例 CreateResult
     * @param resultFlage 处理结果标识
     * @param resultMessage 处理结果消息
     */
    public CreateResult(String resultFlage, String resultMessage) {
        super();
        this.resultFlage = resultFlage;
        this.resultMessage = resultMessage;
    }

    public void setResultFlage(String resultFlage) {
        this.resultFlage = resultFlage;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

}

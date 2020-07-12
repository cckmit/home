package com.neusoft.mid.cloong.web.page.product.item.vm.info;

/**
 * 虚拟机创建返回结果
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-16 下午02:34:35
 */
public class CreateResult {

    private String resultFlage;

    private String resultMessage;

    public String getResultFlage() {
        return resultFlage;
    }
    
    

    /**
     * 创建一个新的实例 CreateResult TODO 请在每个@param,@return,@throws行尾补充对应的简要说明
     */
    public CreateResult() {
        super();
    }



    /**
     * 创建一个新的实例 CreateResult
     * @param resultFlage   处理结果标识
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

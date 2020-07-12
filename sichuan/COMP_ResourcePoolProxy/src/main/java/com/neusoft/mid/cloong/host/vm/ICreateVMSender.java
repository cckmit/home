package com.neusoft.mid.cloong.host.vm;

/**发送创建虚拟机请求后查询状态、创建IP、创建带宽的请求
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-26 上午11:18:01
 */
public interface ICreateVMSender  extends Runnable{
    void send();
}

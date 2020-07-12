package com.neusoft.mid.cloong.web.page.user.order.mail;

/**
 * 邮件监听
 * @author <a href="mailto:he.jf@neusoft.com">he junfeng </a>
 * @version $Revision 1.1 $ 2014-2-12 下午01:31:24
 */
public class MailLoaderListener{
	
    /**
     * 定时器
     */
	TimerManager timerManager ;  
	
	/**
	 * 
	 * init TODO 方法 TODO
	 */
	public void init() {
	    
	    timerManager.Manager();
	}

	/**
	 * 
	 * getTimerManager TODO 方法
	 * @return TODO
	 */
	public TimerManager getTimerManager() {
		return timerManager;
	}

	/**
	 * 
	 * setTimerManager TODO 方法
	 * @param timerManager TODO
	 */
	public void setTimerManager(TimerManager timerManager) {
		this.timerManager = timerManager;
	}
	
}

package com.neusoft.mid.cloong.web.page.user.order.mail;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/***
 * 
 * 邮件提醒定时器
 * @author <a href="mailto:he.jf@neusoft.com">he.jf</a>
 * @version $Revision 1.0 $ 2014-2-13 下午03:58:12
 */
 
public class TimerManager {

    /**
     * 时间间隔
     */
    private static final long PERIOD_DEFULT = 24 * 60 * 60 * 1000;

   
    /**
     *  时间间隔
     */
    private String period;

    /**
     * 发送邮件任务
     */
    private SendMail task;


    /**
     * 邮件提醒 时
     */
    private String hourOfDay;// HOUR_OF_DAY

    /**
     *  邮件提醒 分
     */
    private String minute;// MINUTE;

    /**
     * 邮件提醒 秒
     */
    private String second;// SECOND;

    /**
     * 执行定时任务管理方法
     * Manager TODO 方法 TODO
     */
    public void Manager() {
        long interval = PERIOD_DEFULT;
        Calendar calendar = Calendar.getInstance();

        /*** 定制每日2:00执行方法 ***/
        calendar.set(Calendar.HOUR_OF_DAY, 2);
        if (!"".equals(hourOfDay)) {
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourOfDay));
        }
        calendar.set(Calendar.MINUTE, 0);
        if (!"".equals(minute)) {
            calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
        }
        calendar.set(Calendar.SECOND, 0);
        if (!"".equals(second)) {
            calendar.set(Calendar.SECOND, Integer.parseInt(second));
        }
        Date date = calendar.getTime(); // 第一次执行定时任务的时间

        // 如果第一次执行定时任务的时间 小于 当前的时间
        // 此时要在 第一次执行定时任务的时间 加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
        if (date.before(new Date())) {
            date = this.addDay(date, 1);
        }
        
        //转换间隔时间默认24小时
        if(!"".equals(period)){
           interval = Long.parseLong(period)*60*1000;
        }
        
        Timer timer = new Timer();

        // 安排指定的任务在指定的时间开始进行重复的固定延迟执行。
        timer.schedule(task, date, interval);
    }

    /**
     *  增加或减少天数
     * addDay TODO 方法
     * @param date TODO
     * @param num TODO
     * @return TODO
     */
    public Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        System.out.println(startDT);
        return startDT.getTime();
    }
    
    /**
     * 
     * getMinute TODO 方法
     * @return TODO
     */
    public String getMinute() {
        return minute;
    }

    /**
     * 
     * setMinute TODO 方法
     * @param minute TODO
     */
    public void setMinute(String minute) {
        this.minute = minute;
    }

    /**
     * 
     * getSecond TODO 方法
     * @return TODO
     */
    public String getSecond() {
        return second;
    }

    /**
     * 
     * setSecond TODO 方法
     * @param second TODO
     */
    public void setSecond(String second) {
        this.second = second;
    }

    /**
     * 
     * getTask TODO 方法
     * @return TODO
     */
    public SendMail getTask() {
        return task;
    }

    /**
     * 
     * setTask TODO 方法
     * @param task TODO
     */
    public void setTask(SendMail task) {
        this.task = task;
    }

    /**
     * 
     * getHourOfDay TODO 方法
     * @return TODO
     */
    public String getHourOfDay() {
        return hourOfDay;
    }

    /**
     * 
     * setHourOfDay TODO 方法
     * @param hourOfDay TODO
     */
    public void setHourOfDay(String hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    /**
     * 
     * getPeriod TODO 方法
     * @return TODO
     */
    public String getPeriod() {
        return period;
    }

    /**
     * 
     * setPeriod TODO 方法
     * @param period TODO
     */
    public void setPeriod(String period) {
        this.period = period;
    }

}

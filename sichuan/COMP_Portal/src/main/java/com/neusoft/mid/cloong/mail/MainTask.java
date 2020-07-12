package com.neusoft.mid.cloong.mail;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.Constants;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMInstanceInfo;
import com.neusoft.mid.iamp.logger.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import com.neusoft.mid.cloong.mail.SendMail;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainTask extends BaseAction {

    private static LogService logger = LogService.getLogger(MainTask.class);
    //private String mailNoticeFlag ="1";
    private String mailNoticeFlag = Constants.mailNoticeFlag;

    private SendMail sendMailService;

    @SuppressWarnings("unchecked")
    public void executeAlarm() throws IOException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println("do my job"+dateFormat.format(new Date()));
        logger.info("Execute date = " + dateFormat.format(new Date()));

        //Map queryparam = new HashMap();
        //List<AlarmInfo> alarmInfoList = (AlarmInfo) ibatisDAO.getData("getAlarmInfo",null);
        //AlarmInfo alarmInfo = (AlarmInfo) ibatisDAO.getData("getAlarmInfo",null);
        try {
            //List<AlarmInfo> listAll = new ArrayList<AlarmInfo>();
            //同步list
            List<AlarmInfo> alarmInfoList = ibatisDAO.getData("getAlarmInfo", null);
            //三期list
            List<AlarmInfo> alarmInfoListNmsdb = ibatisDAO.getData("getAlarmInfoNmsdb", null);
            //二期远程list
            /*List<AlarmInfo> alarmInfoListYc = ibatisDAO.getData("getAlarmInfoYc", null);
            //遍历对比，有新增需同步回
            if(alarmInfoListYc!=null){
                alarmInfoListNmsdb.addAll(alarmInfoListYc);
            }*/
            if(alarmInfoListNmsdb!=null&&alarmInfoListNmsdb.size()!=0&&alarmInfoList!=null&&alarmInfoList.size()!=0) {
                for (Iterator<AlarmInfo> itA = alarmInfoListNmsdb.iterator(); itA.hasNext(); ) {
                    AlarmInfo temp = itA.next();
                    String object = temp.getObjectID();
                    String type= temp.getAlarmType();
                    String title= temp.getAlarmTitle();
                    // itA.next() 只能在外层循环里面调用1次
                    for (int i = 0; i < alarmInfoList.size(); i++) {
                        if (object.equals(alarmInfoList.get(i).getObjectID())&&type.equals(alarmInfoList.get(i).getAlarmType())&&title.equals(alarmInfoList.get(i).getAlarmTitle()))
                        // 不该在这里多次调用itA.next()的
                        {
                            itA.remove();
                        }
                    }
                }
            }
            if(alarmInfoListNmsdb!=null && alarmInfoListNmsdb.size()!=0) {
                //List<AlarmInfo> vmCreateFails = null;
                List<BatchVO> insertBatchVO = new ArrayList<BatchVO>();
                for (AlarmInfo alarmInsert : alarmInfoListNmsdb) {
                    insertBatchVO.add(new BatchVO("ADD", "insertAlarm", alarmInsert));
                }
                try {
                    ibatisDAO.updateBatchData(insertBatchVO);
                } catch (SQLException e) {
                    logger.info("Alarm === insert fail!");
                    e.printStackTrace();
                }
            }

            List<String> vmIdList = new ArrayList<String>();
            String vmId ="";
            for (AlarmInfo alarmStatus : alarmInfoList) {
                if("".equals(alarmStatus.getStatus())||alarmStatus.getStatus()==null){
                    vmId = alarmStatus.getObjectID();
                    vmIdList.add(vmId);
                    String appId = "";
                    appId = ((VMInstanceInfo) ibatisDAO.getSingleRecord("queryVMAppId", vmId))
                            .getAppId();

                    if(checkAppId(appId)){
                        //临时，需private
                        if ("1".equals(mailNoticeFlag)) {
                            //SendMail sendMailService = new SendMail();

                            Properties prop = new Properties();
                            InputStream in = new MainTask().getClass().getResourceAsStream("/comm_conf/other/Properties.properties");
                            //F:\workspace_idea522\cloong\COMP_Parent_SiChuan_34Phase\COMP_Portal\src\main\resources\comm_conf\other\Properties.properties
                            try {
                                prop.load(in);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //String appVm = "";
                            String appIdE = prop.getProperty(appId);
                            //String referers[] = appIdE.split("\\|");
                            //for (String referer : referers) {
                            //System.out.println(new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss").format(new Date()));
                            try {
                                sendMailService.intMail("新告警通知!"+"<"+alarmStatus.getAlarmTitle()+">", appIdE, null);
                                sendMailService.setMessage("您有一条新的告警!"+"<br>"+"<br>"+"内容:"+"<br>"+"&emsp;"+"&emsp;"+alarmStatus.getAlarmContent()+"<br>"+"<br>"+"<br>"+"<br>"+"<br>"+"<br>"+"<br>"+"<br>"+"<br>"+"<br>"+"<br>"+"<br>"+"<br>"+"<br>"+"&emsp;"+"&emsp;"+"四川政企云平台"+"<br>"+"&emsp;"+"&emsp;"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                                //sendMail.setMessage("hello<br>your order id is " + req.getProductOrderNumber() + "faif ea fa！<br>");
                                sendMailService.send();
                                //}
                                int updateResult = 0;
                                try {
                                    updateResult = ibatisDAO.updateData("updateAlarmStatus", vmId);
                                } catch (Exception e) {
                                    logger.info("update alarm status fail！！！");
                                    e.printStackTrace();
                                }
                            }catch (Exception e){
                                logger.info("send mail fail！！！");
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        /*SendMail sendMail = new SendMail();
        if ("1".equals(mailNoticeFlag)) {
            *//*Map<String, Object> session = ActionContext.getContext().getSession();
            String key = SessionKeys.userInfo.toString();
            UserBean user = (UserBean) session.get(key);*//*

            //String appids = user.getAppIdStr();
            //user.getEmail()
            //String appId = bossOrderService.selectCorporationBossList(req.getProductOrderNumber()).get(0).getAppId();
            //CompUserUnionAppT user = userAppService.selectUserByAppId(appId);
            sendMail.intMail("新告警通知!", "li.hongxin@neusoft.com", null);
            sendMail.setMessage("LHX" + "," + "您有一条新的告警!");
            //sendMail.setMessage("hello<br>your order id is " + req.getProductOrderNumber() + "faif ea fa！<br>");
            sendMail.send();
        }*/
    }
    /*public void checkLine()
    {
        //任务执行体
        System.out.println("执行任务1");
    }

    public void  checkClient()
    {
        //任务执行体
        System.out.println("执行任务2");
    }*/
    private boolean checkAppId(String appId) {
        Properties prop = new Properties();
        /*String p1=HostCleanFilter.class.getClassLoader().getResource("").getPath();
        System.out.println("HostCleanFilter.class.getClassLoader().getResource--"+p1);*/
        InputStream in = new MainTask().getClass().getResourceAsStream("/comm_conf/other/Properties.properties");
        //F:\workspace_idea522\cloong\COMP_Parent_SiChuan_34Phase\COMP_Portal\src\main\resources\comm_conf\other\Properties.properties
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //String httpHostlhx = prop.getProperty("httpHostlhx");
        //String appVm = "";
        String appIdE = prop.getProperty(appId);
        boolean flag = false;
        if(appIdE!=null){
            flag = true;
        }else {
            flag = flag;
        }
        /*String referers[] = appIdE.split("\\|");
        for (String referer : referers) {
            if (!appId.contains(referer)) {
                flag = flag;
            } else {
                flag = true;
                break;
            }
        }*/
        return flag;
    }

    public SendMail getSendMailService() {
        return sendMailService;
    }

    public void setSendMailService(SendMail sendMailService) {
        this.sendMailService = sendMailService;
    }
}
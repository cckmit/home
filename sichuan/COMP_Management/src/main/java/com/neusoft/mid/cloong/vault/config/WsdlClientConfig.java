package com.neusoft.mid.cloong.vault.config;

import com.neusoft.mid.cloong.vault.wsdl.services.BussinessSupportService;
import com.neusoft.mid.iamp.logger.LogService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.*;


public class WsdlClientConfig {

	private LogService log = LogService.getLogger(this.getClass());

	private static WsdlClientConfig wsdlClientConfig = new WsdlClientConfig();

	private BussinessSupportService bussinessSupportService;

	/*@Value("${wsdl.url}")
    private String wsdlUrl="";*/


	private WsdlClientConfig() {
		wsdlClientConfig = this;
    }

    public static WsdlClientConfig getInstance() {
        return wsdlClientConfig;
    }

	//@Bean(name="bussinessSupportService")
    //@PostConstruct//(MVC注解项目启动加载！)
    public void bussinessSupportServiceInit() {

      try {
    	  connect();
      } catch (Exception ex) {
    	  log.error("bussinessSupportServiceInit---error",ex);
    	  /*ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
    	  ExecutorService taskExe = new ThreadPoolExecutor(10,20,200L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(),namedThreadFactory);*/
		  ExecutorService executorService = Executors.newFixedThreadPool(1);
    	  executorService.execute(this.reconnectTask());
      }
	}

     private void connect() throws Exception {
    	 URL url = null;

		 Properties prop = new Properties();
		 InputStream in = new WsdlClientConfig().getClass().getResourceAsStream("/comm_conf/other/Properties.properties");
		 //F:\workspace_idea522\cloong\COMP_Parent_SiChuan_34Phase\COMP_Portal\src\main\resources\comm_conf\other\Properties.properties
		 try {
			 prop.load(in);
		 } catch (IOException e) {
			 log.info("InputStream 解析失败" + e);
			 e.printStackTrace();
		 }
		 //String httpHostlhx = prop.getProperty("httpHostlhx");
		 String wsdlUrl = prop.getProperty("wsdl.url");
     	 //String wsdlUrl = ResourceBundle.getBundle("application-config", Locale.getDefault()).getString("wsdl.url");
     	 log.info("-------wsdl连接开始 -----" + wsdlUrl);
     	 url = new URL(wsdlUrl);
         bussinessSupportService = new BussinessSupportService(url);
         log.info("-------wsdl连接成功 -----");
    }

    public Runnable reconnectTask() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
            	while (true) {
            		try {
                		Thread.sleep(15000);
                		log.info("-------wsdl连接异常,重连开始 -----");
                		connect();
                		log.info("-------wsdl重连成功 -----");
                		break;
    				} catch (Exception e) {
                		log.info("-------wsdl重连异常,稍后继续连接 -----");
    				}
            	}

            }
        };
        return task;
    }

	public BussinessSupportService getBussinessSupportService() {
		return bussinessSupportService;
	}

	public void setBussinessSupportService(BussinessSupportService bussinessSupportService) {
		this.bussinessSupportService = bussinessSupportService;
	}


}

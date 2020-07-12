package com.neusoft.mid.cloong;

import java.util.List;

import org.apache.log4j.Logger;

import com.neusoft.mid.grains.commons.route.core.IService;

/**
 * 内部服务容器，在Spring启动时启动所有服务，Spring关闭时关闭所有服务
 * @author <a href="mailto:dingpy@neusoft.com">DPY </a>
 * @version $Revision 1.1 $ 2011-7-20 下午06:30:21
 */

public class CompositeService implements IService {
    private static Logger logger = Logger.getLogger(CompositeService.class);

    private List<IService> services;

    public List<IService> getServices() {
        return services;
    }

    public void setServices(List<IService> services) {
        this.services = services;
    }

    public boolean ini() {
        logger.info("开始初始化所有的服务......");
        for (IService service : services) {
            if (!service.ini()) {
                logger.error("初始化服务“" + service + "”失败，系统停止。");
                return false;
            }
        }
        return true;
    }

    public void pause() {
        logger.info("CompositeService pause....");
        for (IService service : services) {
            service.pause();
        }
    }

    public boolean start() {
        logger.info("开始启动所有服务......");
        for (IService service : services) {
            if (!service.start()) {
                logger.error("启动服务“" + service + "”失败。");
                return false;
            }
        }
        return true;
    }

    public boolean stop() {
        logger.info("停止所有服务......");
        for (IService service : services) {
            try {
                if (!service.stop()) {
                    logger.error("stop service " + service
                            + " failed. continue to stop other service.");
                }
            } catch (Exception e) {
                logger.error("停止服务“" + service + "”失败，继续停止其他服务。", e);
            }
        }
        return true;
    }
}

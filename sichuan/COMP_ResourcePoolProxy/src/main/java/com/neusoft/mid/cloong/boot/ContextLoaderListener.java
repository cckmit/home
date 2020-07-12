package com.neusoft.mid.cloong.boot;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.neusoft.mid.grains.commons.route.core.IService;

/**
 * Bootstrap listener to start up Spring's root {@link WebApplicationContext}. Simply delegates to
 * {@link ContextLoader}.
 * <p>
 * This listener should be registered after {@link org.springframework.web.util.Log4jConfigListener}
 * in <code>web.xml</code>, if the latter is used.
 * <p>
 * For Servlet 2.2 containers and Servlet 2.3 ones that do not initalize listeners before servlets,
 * use {@link ContextLoaderServlet}. See the latter's Javadoc for details.
 * @author Juergen Hoeller
 * @since 17.02.2003
 * @see ContextLoaderServlet
 * @see org.springframework.web.util.Log4jConfigListener
 */
public class ContextLoaderListener implements ServletContextListener {

    private static Logger logger = Logger.getLogger(ContextLoaderListener.class);

    /**
     *The first invoked bean in spring beans.
     */
    private static final String BEAN_OF_STARTER = "serviceEntrance";

    private ContextLoader contextLoader;

    private WebApplicationContext wac;

    /**
     * Initialize the root web application context.
     */
    public void contextInitialized(ServletContextEvent event) {
        logger.info("加载系统，启动spring容器！");
        this.contextLoader = createContextLoader();
        wac = contextLoader.initWebApplicationContext(event.getServletContext());
        logger.info("spring容器启动成功");
        IService starter = (IService) wac.getBean(BEAN_OF_STARTER);
        try {
            logger.info("初始化所有服务......");
            if (!starter.ini()) {
                logger.error("初始化所有服务失败!");
                throw new RuntimeException("初始化所有服务失败!");
            }
            logger.info("启动所有服务......");
            starter.start();
        } catch (Exception e) {
            logger.fatal("启动所有服务失败." + e.getMessage(), e);
            try {
                starter.stop();
            } catch (Exception ee) {
                logger.fatal("所有服务失败. " + ee.getMessage(), ee);
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * Create the ContextLoader to use. Can be overridden in subclasses.
     * @return the new ContextLoader
     */
    protected ContextLoader createContextLoader() {
        return new ContextLoader();
    }

    /**
     * Return the ContextLoader used by this listener.
     * @return the current ContextLoader
     */
    public ContextLoader getContextLoader() {
        return this.contextLoader;
    }

    /**
     * Close the root web application context.
     */
    public void contextDestroyed(ServletContextEvent event) {
        logger.info("卸载系统，关闭spring容器！");
        if ((this.contextLoader != null) && (null != wac)) {
            IService starter = (IService) wac.getBean(BEAN_OF_STARTER);
            starter.stop();
            this.contextLoader.closeWebApplicationContext(event.getServletContext());
        }
    }

}

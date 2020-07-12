 /**
  * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
  * @version 1.0.0  2016年10月12日   下午4:13:37
  */
package com.neusoft.mid.cloong.web.page.charges.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.identity.bean.ChargesInfo;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

 /**
  * 查询资费详细信息.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 1.0.0  2016年10月12日   下午4:13:37
 */
public class ChargesDetailAction extends BaseAction {

	/**
	 * 序列.
	 */
	private static final long serialVersionUID = -5261085098711179030L;
	
	/**
	 * 日志.
	 */
	private static final LogService logger = LogService.getLogger(ChargesDetailAction.class);
	
	/**
	 * Id.
	 */
	private String id;
	
	
	public void detail(){
		logger.info(getText("charges.detail.start"));
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		ChargesInfo charges = new ChargesInfo();
		charges.setId(id);
		PrintWriter out =null;
		try {
			 out = response.getWriter();
			 charges = (ChargesInfo)ibatisDAO.getSingleRecord("findChargesById", charges);
			 JSONObject obj = JSONObject.fromObject(charges);
			 out.write(obj.toString());
		} catch (Exception e) {
			logger.info(getText("charges.detail.error"),e);
			out.write("{[result:"+getText("charges.detail.error")+"]}");
		}finally{
			out.flush();
			out.close();
		}
		logger.info(getText("charges.detail.end"));
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


}

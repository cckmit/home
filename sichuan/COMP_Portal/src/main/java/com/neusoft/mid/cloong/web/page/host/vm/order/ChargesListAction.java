package com.neusoft.mid.cloong.web.page.host.vm.order;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.identity.bean.ChargesInfo;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

 /**
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 1.0.0    2016年10月14日   下午4:43:13
 */
public class ChargesListAction extends PageAction {

	/**
	  *序列.
	  */
	private static final long serialVersionUID = -6194843129006380011L;
	
	/**
	 * 日志.
	 */
	private static final LogService logger = LogService.getLogger(ChargesListAction.class);
	
	private List<ChargesInfo> chargesList = new ArrayList<ChargesInfo>();
	
	private ChargesInfo  charges = new ChargesInfo();
	
	@SuppressWarnings("unchecked")
	public String execute(){
		logger.info(getText("vm.charges.start"));
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			chargesList = getPage("queryCountCharges", "queryAllCharges",charges);
			
			JSONObject jo = new JSONObject();
			JSONArray json = JSONArray.fromObject(chargesList);
			
			jo.put("chargesList", json);
			jo.put("pageBar", getPageBar());
			
			out = response.getWriter();
			out.print(jo.toString());
		} catch (Exception e) {
			logger.info("获取资费列表出错", e);
			return ConstantEnum.ERROR.toString();
		}finally{
			out.flush();
			out.close();
		}
		
		logger.info(getText("vm.charges.end"));
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * @return the chargesList
	 */
	public List<ChargesInfo> getChargesList() {
		return chargesList;
	}

	/**
	 * @param chargesList the chargesList to set
	 */
	public void setChargesList(List<ChargesInfo> chargesList) {
		this.chargesList = chargesList;
	}

	/**
	 * @return the charges
	 */
	public ChargesInfo getCharges() {
		return charges;
	}

	/**
	 * @param charges the charges to set
	 */
	public void setCharges(ChargesInfo charges) {
		this.charges = charges;
	}
	
}

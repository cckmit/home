package com.neusoft.mid.cloong.httpclient.support;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.http.HttpStatus;

import com.neusoft.mid.cloong.H3C.exception.H3CException;
import com.neusoft.mid.cloong.H3C.utils.JsonTool;
import com.neusoft.mid.cloong.H3C.vlan.H3cTokenReq;
import com.neusoft.mid.cloong.H3C.vlan.H3cTokenReq.SdnTokenReqBean;
import com.neusoft.mid.cloong.H3C.vlan.H3cTokenResp;
import com.neusoft.mid.cloong.H3C.vlan.H3cVlanReq;
import com.neusoft.mid.cloong.H3C.vlan.H3cVlanReq.H3cVlanReqBean;
import com.neusoft.mid.cloong.H3C.vlan.H3cVlanReq.H3cVlanReqBean.H3cVlanReqRange;
import com.neusoft.mid.cloong.H3C.vlan.H3cVlanResp;
import com.neusoft.mid.cloong.httpclient.impl.HttpClientH3CImpl;
import com.neusoft.mid.cloong.httpclient.info.HttpRequestConfig;
import com.neusoft.mid.cloong.httpclient.info.Response;
import com.neusoft.mid.cloong.web.page.user.order.Vlan4SDNInstanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

public class H3CHttpClientService {
	
	
	/***
	 * 日志
	 */
	private static LogService log = LogService.getLogger(H3CHttpClientService.class);
	
	/***
	 * HttpClient connectionRequestTimeout
	 */
	private int connectionRequestTimeout;
	
	/***
	 * HttpClient connectTimeout
	 */
	private int connectTimeout;
	
	/***
	 * HttpClient socketTimeout
	 */
	private int socketTimeout;
	
	/***
	 * domain
	 */
	private String domain;
	
	/***
	 * h3cUesrName
	 */
	private String h3cUesrName;
	
	/**
	 * h3cUesrPassword
	 */
	private String h3cUesrPassword;
	
	/**
	 * h3cSdnUrl
	 */
	private String h3cSdnUrl;
	
	/***
	 * H3cHttpClientImpl
	 */
	private HttpClientH3CImpl  h3cHttpClientImpl;
	
	/**
	 * H3C_TOKEN_URL
	 */
	private static final String H3C_TOKEN_URL = "/sdn/v2.0/auth";
	
	/***
	 * 
	 * getH3CToken 获取H3C的token
	 * @return 
	 * @throws H3CException 
	 */
	private  String getH3CToken() throws H3CException{
		String h3cTokenUrl = h3cSdnUrl+H3C_TOKEN_URL;
		log.info("H3C Token地址："+h3cTokenUrl+" H3C用户名" +h3cUesrName + "H3C密码："+ h3cUesrPassword);
		if(StringUtils.isEmpty(h3cTokenUrl) || StringUtils.isEmpty(h3cUesrName) || StringUtils.isEmpty(h3cUesrPassword) ){
			H3CException H3CException =  new H3CException();
			H3CException.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			H3CException.setMessage("获取H3cToken发生异常，系统参数异常： "+"H3C Token地址："+h3cTokenUrl+" H3C用户名" +h3cUesrName + "H3C密码："+ h3cUesrPassword);
			H3CException.setTitle("获取H3cToken发生异常");
			throw H3CException;
		}
		H3cTokenReq h3cTokenReq =new H3cTokenReq();
		SdnTokenReqBean sdnTokenReqBean =new H3cTokenReq().new SdnTokenReqBean();
		sdnTokenReqBean.setUser(h3cUesrName);
		sdnTokenReqBean.setPassword(h3cUesrPassword);
		sdnTokenReqBean.setDomain(domain);
		h3cTokenReq.setLogin(sdnTokenReqBean);
		
		String h3cTokenReqString = "";
		Response httpClientResponse = null;
		String h3cToken ="";
		try {
			h3cTokenReqString = JsonTool.encodeAsString(h3cTokenReq);
			log.info("请求对象body体Json内容"+h3cTokenReqString);
			httpClientResponse = this.getHttpClientForToken().post(h3cTokenReqString, h3cTokenUrl);
			if(!HttpStatus.CREATED.toString().equals(String.valueOf(httpClientResponse.getStatusCode()))){
				H3CException H3CException =  new H3CException();
				H3CException.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				H3CException.setMessage("获取H3cToken发生异常，SDN 返回状态码异常，状态码为： "+httpClientResponse.getStatusCode());
				H3CException.setTitle("获取H3cToken发生异常");
				throw H3CException;
			}
			if(StringUtils.isEmpty(httpClientResponse.getBody())){
				H3CException H3CException =  new H3CException();
				H3CException.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				H3CException.setMessage("获取H3cToken发生异常，SDN 返回BODY体异常，BODY体为空 ");
				H3CException.setTitle("获取H3cToken发生异常");
				throw H3CException;
			}
			log.info("httpClientResponse token is : " + httpClientResponse.getBody());
			H3cTokenResp h3cTokenResp = (H3cTokenResp) JsonTool.decode(httpClientResponse.getBody(), H3cTokenResp.class);  
			h3cToken = h3cTokenResp.getRecord().getToken();
			return h3cToken;
			
		} catch (JsonProcessingException e) {
			log.error("[获取H3cToken] 发生异常，Json 转换异常 ", e);
			H3CException H3CException =  new H3CException();
			H3CException.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			H3CException.setMessage("获取H3cToken发生异常，Json 转换异常");
			H3CException.setTitle("获取H3cToken发生异常");
			throw H3CException;
		} catch (IOException e) {
			log.error("[获取H3cToken] 发生异常，HttpClient 请求异常 ", e);
			H3CException H3CException =  new H3CException();
			H3CException.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			H3CException.setMessage("获取H3cToken发生异常，HttpClient 请求异常");
			H3CException.setTitle("获取H3cToken发生异常");
			throw H3CException;
		}
		
	}
	
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		String token = "{\"record\":{\"token\":\"603eb687-3b82-4663-97df-6963259d2390\",\"userName\":\"zqsdn\",\"domainName\":\"\"}}";
		H3cTokenResp h3cTokenResp = (H3cTokenResp) JsonTool.decode(token, H3cTokenResp.class);  
		System.out.println(h3cTokenResp.getRecord().getToken());
	}
	
	
	/***
	 * 
	 * deleteH3CAllToken  删除H3cToken,暂时可以不使用
	 * @return
	 * @throws 
	 */
	private boolean deleteH3CAllToken() throws H3CException{
		Response httpClientResponse = null;
		String h3cTokenUrl = h3cSdnUrl+H3C_TOKEN_URL;
		try {
			httpClientResponse = this.getHttpClient().delete(h3cTokenUrl);
			if(!HttpStatus.NO_CONTENT.toString().equals(String.valueOf(httpClientResponse.getStatusCode()))){
				H3CException H3CException =  new H3CException();
				H3CException.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				H3CException.setMessage("删除H3cToken发生异常，SDN 返回状态码异常，状态码为： "+httpClientResponse.getStatusCode());
				H3CException.setTitle("删除H3cToken发生异常");
				throw H3CException;
			}
		} catch (IOException e) {
			log.error("[删除H3cToken] 发生异常，HttpClient 请求异常 ", e);
			H3CException H3CException =  new H3CException();
			H3CException.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			H3CException.setMessage("删除H3cToken发生异常，HttpClient 请求异常");
			H3CException.setTitle("删除H3cToken发生异常");
			throw H3CException;
		}
		
		return true;
		
	}
	
	/***
	 * 
	 * getHttpClientForToken  获取httpclient for token
	 * @return
	 * @throws H3CException 
	 */
	private HttpClientH3CImpl getHttpClientForToken() throws H3CException{
		
		HttpRequestConfig httpRequestConfig = new HttpRequestConfig();
		httpRequestConfig.setConnectionRequestTimeout(connectionRequestTimeout);
		httpRequestConfig.setConnectTimeout(connectTimeout);
		httpRequestConfig.setSocketTimeout(socketTimeout);
		h3cHttpClientImpl.setConfig(httpRequestConfig);
		
		Map<String, String> head = new HashMap<String, String>();
		head.put("Accept", "application/json");
		head.put("Content-Type", "application/json");
		h3cHttpClientImpl.setHead(head);
		
		return h3cHttpClientImpl;
		
	}

	/**
	 * 
	 * getHttpClient 获取httpclient
	 * @return 
	 * @throws H3CException 
	 */
	private HttpClientH3CImpl getHttpClient() throws H3CException{
		
		String h3CToken = this.getH3CToken();
		HttpRequestConfig httpRequestConfig = new HttpRequestConfig();
		httpRequestConfig.setConnectionRequestTimeout(connectionRequestTimeout);
		httpRequestConfig.setConnectTimeout(connectTimeout);
		httpRequestConfig.setSocketTimeout(socketTimeout);
		h3cHttpClientImpl.setConfig(httpRequestConfig);
		
		Map<String, String> head = new HashMap<String, String>();
		head.put("Accept", "application/json");
		head.put("Content-Type", "application/json");
		head.put("X-Auth-Token", h3CToken);
		h3cHttpClientImpl.setHead(head);
		
		return h3cHttpClientImpl;
		
	}

	public H3cVlanResp createH3cVlan(Vlan4SDNInstanceInfo vlan4SDNInstanceInfo) throws H3CException, IOException {
		H3cVlanReq vlanReq = new H3cVlanReq();
		H3cVlanReqBean gateway_vlan_range = vlanReq.new H3cVlanReqBean();
		gateway_vlan_range.setName(vlan4SDNInstanceInfo.getVlanName());
		H3cVlanReqRange range = gateway_vlan_range.new H3cVlanReqRange();
		range.setStart(vlan4SDNInstanceInfo.getStartId());
		range.setEnd(vlan4SDNInstanceInfo.getEndId());
		gateway_vlan_range.setRange(range);
		vlanReq.setGateway_vlan_range(gateway_vlan_range);
		
		Response h3cCreateVlanResp = this.getHttpClient().post(JsonTool.encodeAsString(vlanReq),this.getH3cSdnUrl()+"/nem/v1.0/gateway_vlan_ranges");
		if (!HttpStatus.CREATED.toString().equals(String.valueOf(h3cCreateVlanResp.getStatusCode()))) {
			H3CException h3CException = new H3CException();
			h3CException.setCode(h3cCreateVlanResp.getStatusCode());
			if (HttpStatus.INTERNAL_SERVER_ERROR.toString()
					.equals(String.valueOf(h3cCreateVlanResp.getStatusCode()))) { // 如果response返回的code是500
				h3CException.setMessage(
						"添加vlan发生异常，SDN 返回状态码异常: " + "Error-Code: " + h3cCreateVlanResp.getHead().get("code")
								+ ", Error-Message: " + h3cCreateVlanResp.getHead().get("message"));
			} else {
				h3CException.setMessage("添加vlan发生异常：SDN 返回状态码异常, 状态码是 : " + h3cCreateVlanResp.getStatusCode());
			}
			h3CException.setTitle("添加vlan发生异常");
			throw h3CException;

		}
		if (StringUtils.isEmpty(h3cCreateVlanResp.getBody())) {
			H3CException innerException = new H3CException();
			innerException.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			innerException.setMessage("添加vlan发生异常，SDN 返回BODY体异常，BODY体为空 ");
			innerException.setTitle("添加vlan发生异常");
			throw innerException;
		}
		if (log.isDebugEnable()) {
			log.debug("从H3C Controller返回的添加vlan应答为: " + h3cCreateVlanResp.getBody());
		}		
		H3cVlanResp h3cVlanResp  = (H3cVlanResp) JsonTool.decode(h3cCreateVlanResp.getBody(), H3cVlanResp.class);
		deleteH3CAllToken();
		return h3cVlanResp;
	}
	
	public H3cVlanResp updateH3cVlan(Vlan4SDNInstanceInfo vlan4SDNInstanceInfo) throws H3CException, IOException {
		H3cVlanReq vlanReq = new H3cVlanReq();
		H3cVlanReqBean gateway_vlan_range = vlanReq.new H3cVlanReqBean();
		gateway_vlan_range.setName(vlan4SDNInstanceInfo.getVlanName());
		H3cVlanReqRange range = gateway_vlan_range.new H3cVlanReqRange();
		range.setStart(vlan4SDNInstanceInfo.getStartId());
		range.setEnd(vlan4SDNInstanceInfo.getEndId());
		gateway_vlan_range.setRange(range);
		vlanReq.setGateway_vlan_range(gateway_vlan_range);
		
		Response h3cCreateVlanResp = this.getHttpClient().put(JsonTool.encodeAsString(vlanReq),this.getH3cSdnUrl()+"/nem/v1.0/gateway_vlan_ranges/" + vlan4SDNInstanceInfo.getRangeId());
		if (!HttpStatus.OK.toString().equals(String.valueOf(h3cCreateVlanResp.getStatusCode()))) {
			H3CException h3CException = new H3CException();
			h3CException.setCode(h3cCreateVlanResp.getStatusCode());
			if (HttpStatus.INTERNAL_SERVER_ERROR.toString()
					.equals(String.valueOf(h3cCreateVlanResp.getStatusCode()))) { // 如果response返回的code是500
				h3CException.setMessage(
						"更新vlan发生异常，SDN 返回状态码异常: " + "Error-Code: " + h3cCreateVlanResp.getHead().get("code")
								+ ", Error-Message: " + h3cCreateVlanResp.getHead().get("message"));
			} else {
				h3CException.setMessage("更新vlan发生异常：SDN 返回状态码异常, 状态码是 : " + h3cCreateVlanResp.getStatusCode());
			}
			h3CException.setTitle("更新vlan发生异常");
			throw h3CException;

		}
		if (StringUtils.isEmpty(h3cCreateVlanResp.getBody())) {
			H3CException innerException = new H3CException();
			innerException.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			innerException.setMessage("更新vlan发生异常，SDN 返回BODY体异常，BODY体为空 ");
			innerException.setTitle("更新vlan发生异常");
			throw innerException;
		}
		if (log.isDebugEnable()) {
			log.debug("从H3C Controller返回的更新vlan应答为: " + h3cCreateVlanResp.getBody());
		}		
		H3cVlanResp h3cVlanResp  = (H3cVlanResp) JsonTool.decode(h3cCreateVlanResp.getBody(), H3cVlanResp.class);
		deleteH3CAllToken();
		return h3cVlanResp;
	}
	
	public void deleteH3cVlan(Vlan4SDNInstanceInfo vlan4SDNInstanceInfo) throws H3CException, IOException {
		Response h3cCreateVlanResp = this.getHttpClient().delete(this.getH3cSdnUrl()+"/nem/v1.0/gateway_vlan_ranges/" + vlan4SDNInstanceInfo.getRangeId());
		if (!HttpStatus.NO_CONTENT.toString().equals(String.valueOf(h3cCreateVlanResp.getStatusCode()))) {
			H3CException h3CException = new H3CException();
			h3CException.setCode(h3cCreateVlanResp.getStatusCode());
			if (HttpStatus.INTERNAL_SERVER_ERROR.toString()
					.equals(String.valueOf(h3cCreateVlanResp.getStatusCode()))) { // 如果response返回的code是500
				h3CException.setMessage(
						"删除vlan发生异常，SDN 返回状态码异常: " + "Error-Code: " + h3cCreateVlanResp.getHead().get("code")
								+ ", Error-Message: " + h3cCreateVlanResp.getHead().get("message"));
			} else {
				h3CException.setMessage("删除vlan发生异常：SDN 返回状态码异常, 状态码是 : " + h3cCreateVlanResp.getStatusCode());
			}
			h3CException.setTitle("删除vlan发生异常");
			throw h3CException;

		}
		deleteH3CAllToken();
	}
	/**
	 * 获取h3cUesrName字段数据
	 * @return Returns the h3cUesrName.
	 */
	public String getH3cUesrName() {
		return h3cUesrName;
	}

	/**
	 * 设置h3cUesrName字段数据
	 * @param h3cUesrName The h3cUesrName to set.
	 */
	public void setH3cUesrName(String h3cUesrName) {
		this.h3cUesrName = h3cUesrName;
	}

	/**
	 * 获取h3cUesrPassword字段数据
	 * @return Returns the h3cUesrPassword.
	 */
	public String getH3cUesrPassword() {
		return h3cUesrPassword;
	}

	/**
	 * 设置h3cUesrPassword字段数据
	 * @param h3cUesrPassword The h3cUesrPassword to set.
	 */
	public void setH3cUesrPassword(String h3cUesrPassword) {
		this.h3cUesrPassword = h3cUesrPassword;
	}

	/**
	 * 获取connectionRequestTimeout字段数据
	 * @return Returns the connectionRequestTimeout.
	 */
	public int getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}

	/**
	 * 设置connectionRequestTimeout字段数据
	 * @param connectionRequestTimeout The connectionRequestTimeout to set.
	 */
	public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}

	/**
	 * 获取connectTimeout字段数据
	 * @return Returns the connectTimeout.
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * 设置connectTimeout字段数据
	 * @param connectTimeout The connectTimeout to set.
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * 获取socketTimeout字段数据
	 * @return Returns the socketTimeout.
	 */
	public int getSocketTimeout() {
		return socketTimeout;
	}

	/**
	 * 设置socketTimeout字段数据
	 * @param socketTimeout The socketTimeout to set.
	 */
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	/**
	 * 获取h3cSdnUrl字段数据
	 * @return Returns the h3cSdnUrl.
	 */
	public String getH3cSdnUrl() {
		return h3cSdnUrl;
	}

	/**
	 * 设置h3cSdnUrl字段数据
	 * @param h3cSdnUrl The h3cSdnUrl to set.
	 */
	public void setH3cSdnUrl(String h3cSdnUrl) {
		this.h3cSdnUrl = h3cSdnUrl;
	}

	/**
	 * 获取h3cHttpClientImpl字段数据
	 * @return Returns the h3cHttpClientImpl.
	 */
	public HttpClientH3CImpl getH3cHttpClientImpl() {
		return h3cHttpClientImpl;
	}

	/**
	 * 设置h3cHttpClientImpl字段数据
	 * @param h3cHttpClientImpl The h3cHttpClientImpl to set.
	 */
	public void setH3cHttpClientImpl(HttpClientH3CImpl h3cHttpClientImpl) {
		this.h3cHttpClientImpl = h3cHttpClientImpl;
	}

	/**
	 * 获取domain字段数据
	 * @return Returns the domain.
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * 设置domain字段数据
	 * @param domain The domain to set.
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

}

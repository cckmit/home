package com.neusoft.mid.cloong.web.page.host.vm.order;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OsInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.PmInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.RespoolInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.RespoolPartInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 申请虚拟机页面加载资源池和资源池分区页面信息
 * 
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-1-16 下午02:30:35
 */
public class VMOnloadResPoolAction extends BaseAction {

	private static final long serialVersionUID = -6356197450412064816L;

	private static LogService logger = LogService
			.getLogger(VMOnloadResPoolAction.class);

	/**
	 * 条目ID
	 */
	private String itemId;

	/**
	 * 资源池ID
	 */
	private String respoolId;

	/**
	 * 资源池分区ID
	 */
	private String respoolPartId;
	/**
	 * 镜像ID
	 */
	private String osId;

	/**
	 * 资源池
	 */
	private List<RespoolInfo> respools;

	/**
	 * 选择的资源池
	 */
	private RespoolInfo respool;

	/**
	 * 资源池分区
	 */
	private List<RespoolPartInfo> respoolParts;

	/**
	 * 选择的资源池分区
	 */
	private RespoolPartInfo respoolPart;

	private List<OsInfo> oss;
	/**
	 * 选择的镜像
	 */
	private OsInfo os;

	private List<PmInfo> pms;

	private PmInfo pm;

	/**
	 * 返回结果路径,error,failure
	 */
	private String resultRoute;

	private String cpuNumMaxSize = "64";

	private String ramMaxSize = "51200";

	private String diskMaxSize = "1024";

	public String execute() {

		if (logger.isDebugEnable()) {
			logger.debug("itemId = " + itemId);
			logger.debug("respoolId = " + respoolId);
			logger.debug("respoolPartId = " + respoolPartId);
			logger.debug("osId = " + osId);
		}
		String tempItemId = null;

		if (null == respoolId || respoolId.equals("")) {
			tempItemId = itemId.trim();
		}

		try {
			// 根据itemId查资源池信息

			respools = queryRespools(tempItemId);

			if (respools.size() == 0) {
				respool = new RespoolInfo();
				respoolId = "";
			} else if (null == respoolId || respoolId.equals("")) {
				respool = respools.get(0);
				respoolId = respool.getRespoolId();
			} else {
				for (RespoolInfo tempinfo : respools) {
					if (tempinfo.getRespoolId().equals(respoolId)) {
						respool = tempinfo;
					}
				}
			}

			respoolParts = queryRespoolParts(tempItemId, respoolId);

			if (respoolParts.size() == 0) {
				respoolPart = new RespoolPartInfo();
				respoolPartId = "";
			} else if (null == respoolPartId || respoolPartId.equals("")) {
				respoolPart = respoolParts.get(0);
				respoolPartId = respoolPart.getRespoolPartId();
			} else {
				for (RespoolPartInfo tempinfo : respoolParts) {
					if (tempinfo.getRespoolPartId().equals(respoolPartId)) {
						respoolPart = tempinfo;
					}
				}
			}

			oss = queryOss(respool.getRespoolId(),
					respoolPart.getRespoolPartId());
			if (oss.size() == 0) {
				os = new OsInfo();
				osId = "";
			} else if (null == osId || osId.equals("")) {
				os = oss.get(0);
				osId = os.getOsId();
			} else {
				for (OsInfo tempinfo : oss) {
					if (tempinfo.getOsId().equals(osId)) {
						os = tempinfo;
					}
				}
			}
			pms = queryPms(respoolId, respoolPartId);

			if (null == respoolId || respoolId.equals("")) {
				logger.debug("资源池分区有空值，自定义创建虚拟机阀值采用默认配置");
			} else {
				RespoolPartInfo respoolPartInfo = (RespoolPartInfo) ibatisDAO
						.getSingleRecord("queryRespoolPartsForVmApply",
                                respoolId);
				if (respoolPartInfo == null) {
					logger.debug("资源池分区有空值，自定义创建虚拟机阀值采用默认配置");
				} else {
					if (!StringUtils.isEmpty(respoolPartInfo.getCpuNumTotal())) {
						cpuNumMaxSize = respoolPartInfo.getCpuNumTotal();
					}
					if (!StringUtils.isEmpty(respoolPartInfo.getRamNumTotal())) {
						ramMaxSize = respoolPartInfo.getRamNumTotal();
					}
					if (!StringUtils.isEmpty(respoolPartInfo
							.getDiskSizeNumTotal())) {
						diskMaxSize = respoolPartInfo.getDiskSizeNumTotal();
					}
				}
			}

		} catch (SQLException e) {
			logger.error(VMStatusCode.ONLOADQUERYRESPOOL_EXCEPTION_CODE,
					getText("vmonload.respool.fail"), e);
			resultRoute = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e2) {
			logger.error(VMStatusCode.ONLOADQUERYRESPOOL_EXCEPTION_CODE,
					getText("vmonload.respool.fail"), e2);
			resultRoute = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}

		return ConstantEnum.SUCCESS.toString();

	}

	/**
	 * 查询可用资源池
	 * 
	 * @param itemId
	 *            条目Id
	 * @return 资源池信息
	 */
	private List<RespoolInfo> queryRespools(String itemId) throws SQLException {
		Map queryparam = new HashMap();
		queryparam.put("itemId", itemId);
		// 查询所有可用资源池
		return (List<RespoolInfo>) ibatisDAO.getData("queryRespools",
				queryparam);

	}

	/**
	 * 查询可用资源池分区
	 * 
	 * @param itemId
	 * @param respoolId
	 * @return
	 */
	private List<RespoolPartInfo> queryRespoolParts(String itemId,
			String respoolId) throws SQLException {
		// 查询所有可用资源池分区
		Map queryparam = new HashMap();
		queryparam.put("itemId", itemId);
		queryparam.put("respoolId", respoolId);
		return (List<RespoolPartInfo>) ibatisDAO.getData("queryRespoolParts",
				queryparam);
	}

	private List<OsInfo> queryOss(String respoolId, String respoolPartId)
			throws SQLException {
		RespoolPartInfo rp = new RespoolPartInfo();
		rp.setRespoolId(respoolId);
		rp.setRespoolPartId(respoolPartId);
		rp.setResourceType("0");
		return (List<OsInfo>) ibatisDAO.getData("queryOss", rp);
	}

	/**
	 * 对应物理机list
	 * 
	 * @param respoolId
	 * @param respoolPartId
	 * @return
	 * @throws SQLException
	 */
	private List<PmInfo> queryPms(String respoolId, String respoolPartId) throws SQLException {
		Map queryparam = new HashMap();
		queryparam.put("respoolId", respoolId);
		queryparam.put("respoolPartId", respoolPartId);
		return (List<PmInfo>) ibatisDAO.getData("queryPms", queryparam);
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public List<RespoolInfo> getRespools() {
		return respools;
	}

	public void setRespools(List<RespoolInfo> respools) {
		this.respools = respools;
	}

	public RespoolInfo getRespool() {
		return respool;
	}

	public void setRespool(RespoolInfo respool) {
		this.respool = respool;
	}

	public List<RespoolPartInfo> getRespoolParts() {
		return respoolParts;
	}

	public void setRespoolParts(List<RespoolPartInfo> respoolParts) {
		this.respoolParts = respoolParts;
	}

	public RespoolPartInfo getRespoolPart() {
		return respoolPart;
	}

	public void setRespoolPart(RespoolPartInfo respoolPart) {
		this.respoolPart = respoolPart;
	}

	public String getResultRoute() {
		return resultRoute;
	}

	public void setResultRoute(String resultRoute) {
		this.resultRoute = resultRoute;
	}

	public List<OsInfo> getOss() {
		return oss;
	}

	public void setOss(List<OsInfo> oss) {
		this.oss = oss;
	}

	public List<PmInfo> getPms() {
		return pms;
	}

	public void setPms(List<PmInfo> pms) {
		this.pms = pms;
	}

	public PmInfo getPm() {
		return pm;
	}

	public void setPm(PmInfo pm) {
		this.pm = pm;
	}

	public String getRespoolId() {
		return respoolId;
	}

	public void setRespoolId(String respoolId) {
		this.respoolId = respoolId;
	}

	public String getRespoolPartId() {
		return respoolPartId;
	}

	public void setRespoolPartId(String respoolPartId) {
		this.respoolPartId = respoolPartId;
	}

	public String getOsId() {
		return osId;
	}

	public void setOsId(String osId) {
		this.osId = osId;
	}

	public OsInfo getOs() {
		return os;
	}

	public void setOs(OsInfo os) {
		this.os = os;
	}

	/**
	 * 获取cpuNumMaxSize字段数据
	 * 
	 * @return Returns the cpuNumMaxSize.
	 */
	public String getCpuNumMaxSize() {
		return cpuNumMaxSize;
	}

	/**
	 * 设置cpuNumMaxSize字段数据
	 * 
	 * @param cpuNumMaxSize
	 *            The cpuNumMaxSize to set.
	 */
	public void setCpuNumMaxSize(String cpuNumMaxSize) {
		this.cpuNumMaxSize = cpuNumMaxSize;
	}

	/**
	 * 获取ramMaxSize字段数据
	 * 
	 * @return Returns the ramMaxSize.
	 */
	public String getRamMaxSize() {
		return ramMaxSize;
	}

	/**
	 * 设置ramMaxSize字段数据
	 * 
	 * @param ramMaxSize
	 *            The ramMaxSize to set.
	 */
	public void setRamMaxSize(String ramMaxSize) {
		this.ramMaxSize = ramMaxSize;
	}

	/**
	 * 获取diskMaxSize字段数据
	 * 
	 * @return Returns the diskMaxSize.
	 */
	public String getDiskMaxSize() {
		return diskMaxSize;
	}

	/**
	 * 设置diskMaxSize字段数据
	 * 
	 * @param diskMaxSize
	 *            The diskMaxSize to set.
	 */
	public void setDiskMaxSize(String diskMaxSize) {
		this.diskMaxSize = diskMaxSize;
	}

}

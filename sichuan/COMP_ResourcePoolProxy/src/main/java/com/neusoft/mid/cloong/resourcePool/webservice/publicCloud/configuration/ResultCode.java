package com.neusoft.mid.cloong.resourcePool.webservice.publicCloud.configuration;


enum ResultCode
{
	SUCCESS("00000000" , "成功"),
	ERROR_HEADER_TRANSACTIONID_INVALIED("20000000","WebService头TRANSACTIONID字段校验失败"),
    ERROR_HEADER_IDCACCESSID_NOEXIST("20000010","资源池上报的ID在运营平台中不存在"),
    ERROR_HEADER_IDCACCESSID_IP_ILLEGAL("20000011","资源池IP校验失败"),
    ERROR_HEADER_ZONEID_NOEXIST("20000020","资源池上报的分区ID在运营平台中不存在"),
    ERROR_BODY_PM_INSTANCE_NOTEXIST("51000001","不存在物理机实例"),
    ERROR_BODY_PM_EQPID_NONE("51000002","物理机编码为空"),
    ERROR_BODY_VM_INSTANCEID_NONE("52000002","虚拟机实例ID为空"),
    ERROR_BODY_DATABASE_EXCEPTION("51000002","数据库操作异常"),
    ERROR_BODY_VM_INSTANCE_NOTEXIST("52000001","不存在虚拟机实例"),
    ERROR_BODY_EBS_INSTANCE_NOTEXIST("53000001","不存在卷实例"),
    ERROR_BODY_EBS_INSTANCE_NONE("53000002","卷实例ID为空"),
	ERROR_BODY_DATEFORMATERROR("51000003","日期格式转换异常"),
	ERROR_BODY_MC_INSTANCE_NOTEXIST("54000001","不存在小型机实例"),
	ERROR_BODY_MC_INSTANCE_NONE("54000002","小型机实例ID为空"),
	ERROR_BODY_MCPART_INSTANCE_NOTEXIST("55000001","不存在小型机分区实例"),
	ERROR_BODY_MCPAR_INSTANCE_NONE("55000002","小型机分区实例ID为空"),
	ERROR_BODY_RAID_INSTANCE_NOTEXIST("56000001","不存在存储阵列实例"),
	ERROR_BODY_RAID_INSTANCEID_NONE("56000002","存储阵列实例ID为空"),
	ERROR_BODY_SW_INSTANCE_NOTEXIST("57000001","不存在交换机实例"),
	ERROR_BODY_SW_INSTANCEID_NONE("57000002","交换机ID为空"),
	ERROR_BODY_SWIF_INSTANCE_NOTEXIST("58000001","不存在交换机端口实例"),
	ERROR_BODY_SWIF_INSTANCEID_NONE("58000002","交换机端口ID为空"),
	ERROR_BODY_RT_INSTANCE_NOTEXIST("59000001","不存在 路由器实例"),
	ERROR_BODY_RT_INSTANCEID_NONE("59000002","路由器ID为空"),
	ERROR_BODY_RTIF_INSTANCE_NOTEXIST("59000001","不存在路由器端口实例"),
	ERROR_BODY_RTIF_INSTANCEID_NONE("59000002","路由器端口ID为空"),
	ERROR_BODY_FW_INSTANCE_NOTEXIST("59000001","不存在 防火墙实例"),
	ERROR_BODY_FW_INSTANCEID_NONE("59000002","防火墙接口ID为空");
	
	/**
	 * 状态代码
	 */
	private String code;
	/**
	 * 状态描述
	 */
	private String desc;

	/**
	 * 状态
	 * 
	 * @param code
	 *            状态代码
	 * @param desc
	 *            状态描述
	 */
	ResultCode(String code , String desc)
	{
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 获取条目状态
	 * 
	 * @param code
	 *            状态字符串
	 * @return 存在返回 RoleStatus 枚举类型.否则返回null
	 */
	public static ResultCode obtainItemStatusEunm(String code)
	{
		for (ResultCode status : ResultCode.values())
		{
			if(code.equals(status.getCode()))
			{
				return status;
			}
		}
		return null;
	}

	public String getCode()
	{
		return code;
	}

	/**
	 * 字符串格式代码
	 * 
	 * @return 返回字符串格式代码
	 */
	public String toString()
	{
		return code;
	}

	public String getDesc()
	{
		return desc;
	}
}

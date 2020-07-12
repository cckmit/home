package com.neusoft.mid.cloong.web.page.product.standard.ebs.info;

import com.neusoft.mid.cloong.web.BaseInfo;

/**
 * 虚拟硬盘资源规格实例
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-21 下午11:00:59
 */
public class EBSStandardInfo extends BaseInfo {

    private String standardName;

    private String standardId;

    private long discSize;

    private String description;

    private String useStatus;
    
    private String resourceType;

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public long getDiscSize() {
        return discSize;
    }

    public void setDiscSize(long discSize) {
        this.discSize = discSize;
    }

	/**
	 * @return the resourceType
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * @param resourceType the resourceType to set
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
}

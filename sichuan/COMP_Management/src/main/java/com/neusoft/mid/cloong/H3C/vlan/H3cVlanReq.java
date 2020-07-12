/**
 * 
 */
package com.neusoft.mid.cloong.H3C.vlan;

/**
 * @author li-lei
 *
 */
public class H3cVlanReq {

	private H3cVlanReqBean gateway_vlan_range;

	public H3cVlanReqBean getGateway_vlan_range() {
		return gateway_vlan_range;
	}

	public void setGateway_vlan_range(H3cVlanReqBean gateway_vlan_range) {
		this.gateway_vlan_range = gateway_vlan_range;
	}
	
	public class H3cVlanReqBean {
		private String name;
		
		private H3cVlanReqRange range;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public H3cVlanReqRange getRange() {
			return range;
		}

		public void setRange(H3cVlanReqRange range) {
			this.range = range;
		}
		
		public class H3cVlanReqRange {
			private String start;
			
			private String end;

			public String getStart() {
				return start;
			}

			public void setStart(String start) {
				this.start = start;
			}

			public String getEnd() {
				return end;
			}

			public void setEnd(String end) {
				this.end = end;
			}
			
		}
	}
}

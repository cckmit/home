/**
 * 
 */
package com.neusoft.mid.cloong.H3C.vlan;

/**
 * @author li-lei
 *
 */
public class H3cVlanResp {

	private H3cVlanRespBean gateway_vlan_range;

	public H3cVlanRespBean getGateway_vlan_range() {
		return gateway_vlan_range;
	}

	public void setGateway_vlan_range(H3cVlanRespBean gateway_vlan_range) {
		this.gateway_vlan_range = gateway_vlan_range;
	}
	
	public static class H3cVlanRespBean {
		
		private String id;
		
		private String name;
		
		private H3cVlanRespRange range;
		
		private boolean allocated;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public H3cVlanRespRange getRange() {
			return range;
		}

		public void setRange(H3cVlanRespRange range) {
			this.range = range;
		}

		public boolean getAllocated() {
			return allocated;
		}

		public void setAllocated(boolean allocated) {
			this.allocated = allocated;
		}
		
		public static class H3cVlanRespRange {
			
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

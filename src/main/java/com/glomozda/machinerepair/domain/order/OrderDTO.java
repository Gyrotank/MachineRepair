package com.glomozda.machinerepair.domain.order;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

public class OrderDTO {
	
	@Min(1)
	private Long clientId;
	
	@Min(1)
	private Long repairTypeId;
	
	@Min(1)
	private Long machineId;
	
	@NotEmpty
	private String startDate;
	
	@Min(1)
	private Long orderStatusId;
	
	@NotEmpty
	private String manager;

	public OrderDTO() {		
	}
	
	public OrderDTO(Long clientId, Long repairTypeId, Long machineId,
			String startDate, Long orderStatusId, String manager) {		
		this.clientId = clientId;
		this.repairTypeId = repairTypeId;
		this.machineId = machineId;
		this.startDate = startDate;
		this.orderStatusId = orderStatusId;
		this.manager = manager;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getRepairTypeId() {
		return repairTypeId;
	}

	public void setRepairTypeId(Long repairTypeId) {
		this.repairTypeId = repairTypeId;
	}

	public Long getMachineId() {
		return machineId;
	}

	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Long getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(Long orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result
				+ ((machineId == null) ? 0 : machineId.hashCode());
		result = prime * result + ((manager == null) ? 0 : manager.hashCode());
		result = prime * result
				+ ((orderStatusId == null) ? 0 : orderStatusId.hashCode());
		result = prime * result
				+ ((repairTypeId == null) ? 0 : repairTypeId.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OrderDTO other = (OrderDTO) obj;
		if (clientId == null) {
			if (other.clientId != null) {
				return false;
			}
		} else if (!clientId.equals(other.clientId)) {
			return false;
		}
		if (machineId == null) {
			if (other.machineId != null) {
				return false;
			}
		} else if (!machineId.equals(other.machineId)) {
			return false;
		}
		if (manager == null) {
			if (other.manager != null) {
				return false;
			}
		} else if (!manager.equals(other.manager)) {
			return false;
		}
		if (orderStatusId == null) {
			if (other.orderStatusId != null) {
				return false;
			}
		} else if (!orderStatusId.equals(other.orderStatusId)) {
			return false;
		}
		if (repairTypeId == null) {
			if (other.repairTypeId != null) {
				return false;
			}
		} else if (!repairTypeId.equals(other.repairTypeId)) {
			return false;
		}
		if (startDate == null) {
			if (other.startDate != null) {
				return false;
			}
		} else if (!startDate.equals(other.startDate)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderDTO {clientId=");
		builder.append(clientId);
		builder.append(", repairTypeId=");
		builder.append(repairTypeId);
		builder.append(", machineId=");
		builder.append(machineId);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", orderStatusId=");
		builder.append(orderStatusId);
		builder.append(", manager=");
		builder.append(manager);
		builder.append("}");
		return builder.toString();
	}
	
	

}

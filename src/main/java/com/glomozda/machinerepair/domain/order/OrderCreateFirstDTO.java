package com.glomozda.machinerepair.domain.order;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

public class OrderCreateFirstDTO {
	
	@Min(1)
	private Long machineServiceableId;
	
	@NotEmpty
	private String machineSerialNumber;
	
	@NotEmpty
	private String machineYear;
	
	@Min(1)
	private Long repairTypeId;
	
	public OrderCreateFirstDTO() {		
	}
	
	public OrderCreateFirstDTO(final Long machineServiceableId, final String machineSerialNumber, 
			final String machineYear, final Long repairTypeId) {
		this.machineServiceableId = machineServiceableId;
		this.machineSerialNumber = machineSerialNumber;
		this.machineYear = machineYear;
		this.repairTypeId = repairTypeId;
	}

	public Long getMachineServiceableId() {
		return machineServiceableId;
	}

	public void setMachineServiceableId(Long machineServiceableId) {
		this.machineServiceableId = machineServiceableId;
	}

	public String getMachineSerialNumber() {
		return machineSerialNumber;
	}

	public void setMachineSerialNumber(String machineSerialNumber) {
		this.machineSerialNumber = machineSerialNumber;
	}

	public String getMachineYear() {
		return machineYear;
	}

	public void setMachineYear(String machineYear) {
		this.machineYear = machineYear;
	}

	public Long getRepairTypeId() {
		return repairTypeId;
	}

	public void setRepairTypeId(Long repairTypeId) {
		this.repairTypeId = repairTypeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((machineSerialNumber == null) ? 0 : machineSerialNumber
						.hashCode());
		result = prime
				* result
				+ ((machineServiceableId == null) ? 0 : machineServiceableId
						.hashCode());
		result = prime * result
				+ ((machineYear == null) ? 0 : machineYear.hashCode());
		result = prime * result
				+ ((repairTypeId == null) ? 0 : repairTypeId.hashCode());
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
		OrderCreateFirstDTO other = (OrderCreateFirstDTO) obj;
		if (machineSerialNumber == null) {
			if (other.machineSerialNumber != null) {
				return false;
			}
		} else if (!machineSerialNumber.equals(other.machineSerialNumber)) {
			return false;
		}
		if (machineServiceableId == null) {
			if (other.machineServiceableId != null) {
				return false;
			}
		} else if (!machineServiceableId.equals(other.machineServiceableId)) {
			return false;
		}
		if (machineYear == null) {
			if (other.machineYear != null) {
				return false;
			}
		} else if (!machineYear.equals(other.machineYear)) {
			return false;
		}
		if (repairTypeId == null) {
			if (other.repairTypeId != null) {
				return false;
			}
		} else if (!repairTypeId.equals(other.repairTypeId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderCreateNewDTO [machineServiceableId=");
		builder.append(machineServiceableId);
		builder.append(", machineSerialNumber=");
		builder.append(machineSerialNumber);
		builder.append(", machineYear=");
		builder.append(machineYear);
		builder.append(", repairTypeId=");
		builder.append(repairTypeId);
		builder.append("]");
		return builder.toString();
	}	
}

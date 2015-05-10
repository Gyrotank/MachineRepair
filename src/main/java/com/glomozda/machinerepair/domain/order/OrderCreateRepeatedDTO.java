package com.glomozda.machinerepair.domain.order;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

public class OrderCreateRepeatedDTO {
	
	@NotEmpty
	private String machineSerialNumber;
	
	@Min(1)
	private Long repairTypeId;
	
	public OrderCreateRepeatedDTO() {		
	}
	
	public OrderCreateRepeatedDTO(final String machineSerialNumber, final Long repairTypeId) {
		this.machineSerialNumber = machineSerialNumber;
		this.repairTypeId = repairTypeId;
	}

	public String getMachineSerialNumber() {
		return machineSerialNumber;
	}

	public void setMachineSerialNumber(String machineSerialNumber) {
		this.machineSerialNumber = machineSerialNumber;
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
		OrderCreateRepeatedDTO other = (OrderCreateRepeatedDTO) obj;
		if (machineSerialNumber == null) {
			if (other.machineSerialNumber != null) {
				return false;
			}
		} else if (!machineSerialNumber.equals(other.machineSerialNumber)) {
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
		builder.append("OrderCreateRepeatedDTO [machineSerialNumber=");
		builder.append(machineSerialNumber);
		builder.append(", repairTypeId=");
		builder.append(repairTypeId);
		builder.append("]");
		return builder.toString();
	}	
}
